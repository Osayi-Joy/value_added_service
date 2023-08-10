package com.digicore.billent.backoffice.service.test.integration.profile;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTOWithTeamMembers;
import com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PROFILE_API_V1;
import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ROLES_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class BackOfficeProfileControllerTest {

 @Autowired
 private MockMvc mockMvc;

 @Autowired private BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;
 @Autowired
 private PropertyConfig propertyConfig;

 @BeforeEach
 void  checkup(){
  new H2TestConfiguration(propertyConfig);
 }
 @Test
 void testGetAllBackOfficeUserProfiles() throws Exception {
  TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
  testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-backoffice-users");
  int pageNumber = 0;
  int pageSize = 10;

  MvcResult mvcResult = mockMvc.perform(get(PROFILE_API_V1 + "get-all")
                  .param("pageNumber", String.valueOf(pageNumber))
                  .param("pageSize", String.valueOf(pageSize))
                  .header("Authorization",testHelper.retrieveValidAccessToken()))
          .andExpect(status().isOk())
          .andReturn();


  PaginatedResponseDTO<UserProfileDTO> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);


  assertNotNull(paginatedResponseDTO.getContent());
  assertTrue(paginatedResponseDTO.getIsFirstPage());
  assertTrue(paginatedResponseDTO.getIsLastPage());
  assertNotNull(paginatedResponseDTO.getContent());
  assertTrue(paginatedResponseDTO.getContent().size() > 0);
 }

 @Test
 void testSearchBackOfficeUserProfiles() throws Exception {
  TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
  testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-backoffice-users");
  int pageNumber = 0;
  int pageSize = 10;

  MvcResult mvcResult = mockMvc.perform(get(PROFILE_API_V1 + "search")
                  .param(PAGE_NUMBER, String.valueOf(pageNumber))
                  .param(PAGE_SIZE, String.valueOf(pageSize))
                  .param(KEY, "email")
                  .param(VALUE, "system")
                  .header("Authorization",testHelper.retrieveValidAccessToken()))
          .andExpect(status().isOk())
          .andReturn();


  PaginatedResponseDTO<UserProfileDTO> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);


  assertNotNull(paginatedResponseDTO.getContent());
  assertTrue(paginatedResponseDTO.getIsFirstPage());
  assertTrue(paginatedResponseDTO.getIsLastPage());
  assertNotNull(paginatedResponseDTO.getContent());
  assertTrue(paginatedResponseDTO.getContent().size() > 0);
 }

 @Test
 void testFilterBackOfficeUserProfiles() throws Exception {
  TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
  testHelper.updateMakerSelfPermissionByAddingNeededPermission("view-backoffice-users");
  int pageNumber = 0;
  int pageSize = 10;

  MvcResult mvcResult = mockMvc.perform(get(PROFILE_API_V1 + "filter")
                  .param(PAGE_NUMBER, String.valueOf(pageNumber))
                  .param(PAGE_SIZE, String.valueOf(pageSize))
                  .param(STATUS, "INACTIVE")
                  .param(START_DATE, "2021-01-01")
                  .param(END_DATE, "9021-01-01")
                  .header("Authorization",testHelper.retrieveValidAccessToken()))
          .andExpect(status().isOk())
          .andReturn();


  PaginatedResponseDTO<UserProfileDTO> paginatedResponseDTO = getPaginatedResponseDTO(mvcResult);


  assertNotNull(paginatedResponseDTO.getContent());
  assertTrue(paginatedResponseDTO.getIsFirstPage());
  assertTrue(paginatedResponseDTO.getIsLastPage());
  assertNotNull(paginatedResponseDTO.getContent());
  assertTrue(paginatedResponseDTO.getContent().size() > 0);
 }

 private static PaginatedResponseDTO<UserProfileDTO> getPaginatedResponseDTO(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
  ApiResponseJson<PaginatedResponseDTO<UserProfileDTO>> response =
          ClientUtil.getGsonMapper()
                  .fromJson(result.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<PaginatedResponseDTO<UserProfileDTO>>>() {}.getType());
  assertTrue(response.isSuccess());



  return response.getData();

 }
}
