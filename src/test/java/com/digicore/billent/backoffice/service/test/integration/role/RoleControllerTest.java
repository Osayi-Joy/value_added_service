package com.digicore.billent.backoffice.service.test.integration.role;


import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.model.Role;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.approval_repository.ApprovalRequestsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@Profile("test")
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private RoleService<RoleDTO, Role> roleService;
    @Autowired private NotificationDispatcher notificationDispatcher;

    @Autowired private BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;

    @Autowired private ApprovalRequestsRepository approvalRequestsRepository;


    @Autowired
    private PropertyConfig propertyConfig;

    @BeforeEach
    void  checkup(){
        new H2TestConfiguration(propertyConfig);
    }



    @Test
     void testGetAllRoles() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService, approvalRequestsRepository);
        testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-roles");
       int pageNumber = 0;
        int pageSize = 10;

        MvcResult mvcResult = mockMvc.perform(get(ROLES_API_V1 + "get-all")
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", String.valueOf(pageSize))
                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();


        PaginatedResponseDTO<RoleDTO> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);


        assertNotNull(paginatedResponseDTO.getContent());
        assertTrue(paginatedResponseDTO.getIsFirstPage());
        assertTrue(paginatedResponseDTO.getIsLastPage());
        assertNotNull(paginatedResponseDTO.getContent());
    }

    @Test
    void testGetAllPermissions() throws Exception {
        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService, approvalRequestsRepository);
        testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-permissions");
        MvcResult mvcResult = mockMvc.perform(get(ROLES_API_V1 + "get-system-permissions")

                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();


        ApiResponseJson<Set<PermissionDTO>> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<Set<PermissionDTO>>>() {}.getType());



        assertTrue(response.isSuccess());
        assertTrue(response.getData().size() > 0);
        assertNotNull(response.getData());
    }

    private static PaginatedResponseDTO<RoleDTO> getPaginatedResponseDTO(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponseJson<PaginatedResponseDTO<RoleDTO>> response =
                ClientUtil.getGsonMapper()
                        .fromJson(result.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<PaginatedResponseDTO<RoleDTO>>>() {}.getType());
        assertTrue(response.isSuccess());



      return response.getData();

    }
    @Test
    void testCreateRole() throws Exception {
        RoleCreationDTO roleCreationDTO = new RoleCreationDTO();
        roleCreationDTO.setName("Tester");
        roleCreationDTO.setDescription("tester tester");
        roleCreationDTO.setPermissions(Set.of("create-roles","view-roles"));

        TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService, approvalRequestsRepository);
        testHelper.updateMakerSelfPermissionByAddingNeededPermission("create-roles");
        MvcResult mvcResult = mockMvc.perform(post(ROLES_API_V1 + "creation")
                        .content(
                                ClientUtil.getGsonMapper().toJson(roleCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",testHelper.retrieveValidAccessToken()))
                .andExpect(status().isOk())
                .andReturn();


        ApiResponseJson<RoleDTO> response =
                ClientUtil.getGsonMapper()
                        .fromJson(mvcResult.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());

        assertTrue(response.isSuccess());
        testHelper.approvalRequest(1L,"approve-create-roles");

    }
}
