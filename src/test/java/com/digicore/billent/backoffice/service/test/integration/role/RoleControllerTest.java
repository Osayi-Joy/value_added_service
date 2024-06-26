package com.digicore.billent.backoffice.service.test.integration.role;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTOWithTeamMembers;
import com.digicore.billent.data.lib.modules.common.wallet.dto.CreateWalletResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class RoleControllerTest {
  //    mvn test -Dspring.profiles.active=test -Dtest="RoleControllerTest"

  @Autowired private MockMvc mockMvc;

  @Autowired private PropertyConfig propertyConfig;

  @MockBean
  private WalletService walletService;

  @BeforeEach
  void  checkup() throws Exception {
    new H2TestConfiguration(propertyConfig);
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRole();
    CreateWalletResponseData createWalletResponseData = new CreateWalletResponseData();
    createWalletResponseData.setCurrency("NGN");
    createWalletResponseData.setWalletName("Wallet Name");
    createWalletResponseData.setSystemWalletId("865753dcy1");
    createWalletResponseData.setCustomerId("89756rft781");
    createWalletResponseData.setCustomerName("Oluwatobi Ogunwuyi");
    when(walletService.createWallet(any())).thenReturn(createWalletResponseData);
  }

  private static PaginatedResponseDTO<RoleDTOWithTeamMembers> getPaginatedResponseDTO(
      MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
    ApiResponseJson<PaginatedResponseDTO<RoleDTOWithTeamMembers>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                result.getResponse().getContentAsString().trim(),
                new TypeToken<
                    ApiResponseJson<PaginatedResponseDTO<RoleDTOWithTeamMembers>>>() {}.getType());
    assertTrue(response.isSuccess());

    return response.getData();
  }



  @Test
  void testGetAllRolesPaginated() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    int pageNumber = 0;
    int pageSize = 10;

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(ROLES_API_V1 + "get-all".concat("?paginated=true"))
                    .param("pageNumber", String.valueOf(pageNumber))
                    .param("pageSize", String.valueOf(pageSize))
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    PaginatedResponseDTO<RoleDTOWithTeamMembers> paginatedResponseDTO =
        getPaginatedResponseDTO(mvcResult);

    assertNotNull(paginatedResponseDTO.getContent());
    assertTrue(paginatedResponseDTO.getIsFirstPage());
    assertTrue(paginatedResponseDTO.getIsLastPage());
    assertNotNull(paginatedResponseDTO.getContent());
//    assertEquals(1, paginatedResponseDTO.getContent().get(2).getTotalTeamMemberCount());
    assertTrue(paginatedResponseDTO.getContent().get(1).getPermissions().size() > 0);
  }

  @Test
  void testGetRole() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(ROLES_API_V1.concat("get-").concat(CHECKER_ROLE_NAME).concat("-details"))
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<RoleDTOWithTeamMembers> response =
        ClientUtil.getObjectMapper()
            .readValue(
                mvcResult.getResponse().getContentAsString().trim(), new TypeReference<>() {});
    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    RoleDTOWithTeamMembers responseData = response.getData();
    assertEquals(1, responseData.getTotalTeamMemberCount());
    assertEquals(CHECKER_ROLE_NAME, responseData.getName());
    assertTrue(responseData.getPermissions().size() > 0);
  }

  @Test
  void testGetAllPermissions() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    MvcResult mvcResult =
        mockMvc
            .perform(
                get(ROLES_API_V1 + "get-system-permissions")
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<Set<PermissionDTO>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                mvcResult.getResponse().getContentAsString().trim(),
                new TypeToken<ApiResponseJson<Set<PermissionDTO>>>() {}.getType());

    assertTrue(response.isSuccess());
    assertTrue(response.getData().size() > 0);
    assertNotNull(response.getData());
  }

  @Test
  void testCreateRole() throws Exception {
    RoleCreationDTO roleCreationDTO = new RoleCreationDTO();
    roleCreationDTO.setName("Tester");
    roleCreationDTO.setDescription("tester tester");
    roleCreationDTO.setPermissions(Set.of("create-roles", "view-roles"));

    TestHelper testHelper = new TestHelper(mockMvc);
    MvcResult mvcResult =
        mockMvc
            .perform(
                post(ROLES_API_V1 + "creation")
                    .content(ClientUtil.getGsonMapper().toJson(roleCreationDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<RoleDTO> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                mvcResult.getResponse().getContentAsString().trim(),
                new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());

    assertTrue(response.isSuccess());
  }

  @Test
  void testGetAllRoles() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    MvcResult mvcResult =
        mockMvc
            .perform(
                get(ROLES_API_V1 + "get-all")
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<Set<PermissionDTO>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                mvcResult.getResponse().getContentAsString().trim(),
                new TypeToken<ApiResponseJson<Set<PermissionDTO>>>() {}.getType());

    assertTrue(response.isSuccess());
    assertTrue(response.getData().size() > 0);
    assertNotNull(response.getData());
  }

  @Test
  void testDeleteRole() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    MvcResult mvcResult =
        mockMvc
            .perform(
                delete(ROLES_API_V1 + "remove-SYSTEM_CHECKER")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<RoleDTO> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                mvcResult.getResponse().getContentAsString().trim(),
                new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());

    assertTrue(response.isSuccess());
  }

  @Test
  void testUpdateRole() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRoleCustom("TesterUpdateRole");
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setName("TesterUpdateRole");
    roleDTO.setDescription("tester tester");

    MvcResult mvcResult =
        mockMvc
            .perform(
                patch(ROLES_API_V1 + "edit")
                    .content(ClientUtil.getGsonMapper().toJson(roleDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<RoleDTO> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                mvcResult.getResponse().getContentAsString().trim(),
                new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());

    assertTrue(response.isSuccess());
  }

  @Test
  void testDisableRole() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRoleCustom("TesterUpdateRole");
    MvcResult mvcResult =
            mockMvc
                    .perform(
                            patch(ROLES_API_V1 + "disable-TesterUpdateRole")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", testHelper.retrieveValidAccessToken()))
                    .andExpect(status().isOk())
                    .andReturn();

    ApiResponseJson<RoleDTO> response =
            ClientUtil.getGsonMapper()
                    .fromJson(
                            mvcResult.getResponse().getContentAsString().trim(),
                            new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());

    assertTrue(response.isSuccess());
  }

  @Test
  void testEnableRole() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRoleCustom("TestDisableRole");
    testHelper.disableRole("TestDisableRole");
    MvcResult mvcResult =
            mockMvc
                    .perform(
                            patch(ROLES_API_V1 + "enable-TestDisableRole")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", testHelper.retrieveValidAccessToken()))
                    .andExpect(status().isOk())
                    .andReturn();

    ApiResponseJson<RoleDTO> response =
            ClientUtil.getGsonMapper()
                    .fromJson(
                            mvcResult.getResponse().getContentAsString().trim(),
                            new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());

    assertTrue(response.isSuccess());
  }

  @Test
  void testAlreadyEnabledRole_ThrowsException() throws Exception{
    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestRoleCustom("TestDisableRole");
    MvcResult mvcResult =
            mockMvc
                    .perform(
                            patch(ROLES_API_V1 + "enable-TestDisableRole")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", testHelper.retrieveValidAccessToken()))
                    .andExpect(status().is4xxClientError())
                    .andReturn();

  }
}
