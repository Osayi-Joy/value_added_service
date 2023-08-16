package com.digicore.billent.backoffice.service.test.integration.profile;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PROFILE_API_V1;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.profile.model.BackOfficeUserProfile;
import com.digicore.billent.data.lib.modules.backoffice.profile.repository.BackOfficeUserProfileRepository;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class BackOfficeProfileControllerTest {
// mvn test -Dspring.profiles.active=backOffice,test -Dtest="BackOfficeProfileControllerTest"
 @Autowired
 private MockMvc mockMvc;

 @Autowired private AuthProfileService<UserAuthProfileDTO> backOfficeUserAuthServiceImpl;
 @Autowired
 private PropertyConfig propertyConfig;
 @Autowired
 private BackOfficeUserProfileRepository backOfficeUserProfileRepository;

 private static PaginatedResponseDTO<UserProfileDTO> getPaginatedResponseDTO(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
  ApiResponseJson<PaginatedResponseDTO<UserProfileDTO>> response =
          ClientUtil.getGsonMapper()
                  .fromJson(result.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<PaginatedResponseDTO<UserProfileDTO>>>() {}.getType());
  assertTrue(response.isSuccess());



  return response.getData();

 }

 @BeforeEach
 void  checkup(){
  new H2TestConfiguration(propertyConfig);
 }

 @Test
 void testGetAllBackOfficeUserProfiles() throws Exception {
  TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
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
  TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
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
  TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
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



 @Test
 void testDeleteUserProfile_ProfileExists() throws Exception {
  BackOfficeUserProfile userProfile = new BackOfficeUserProfile();
  userProfile.setEmail("test@example.com");
  userProfile.setProfileId("123");
  userProfile.setFirstName("JOY");
  userProfile.setLastName("OSAYI");
  String email = "test@example.com";
  backOfficeUserProfileRepository.save(userProfile);

  TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
  testHelper.updateMakerSelfPermissionByAddingNeededPermission("delete-backoffice-profile");

  MvcResult mvcResult = mockMvc.perform(delete(PROFILE_API_V1.concat("delete-"+email))
                  .param(EMAIL, email)
                  .contentType(MediaType.APPLICATION_JSON)
                  .header("Authorization", testHelper.retrieveValidAccessToken()))
          .andExpect(status().isOk())
          .andReturn();

  ApiResponseJson<?> response =
          ClientUtil.getGsonMapper()
                  .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
  assertTrue(response.isSuccess());
 }
 @Test
 void testDeleteUserProfile_ProfileDoesNotExists() throws Exception {
  String email = "test@example.com";

  TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthServiceImpl);
  testHelper.updateMakerSelfPermissionByAddingNeededPermission("delete-backoffice-profile");

  MvcResult mvcResult = mockMvc.perform(delete(PROFILE_API_V1.concat("delete-"+email))
                  .contentType(MediaType.APPLICATION_JSON)
                  .header("Authorization", testHelper.retrieveValidAccessToken()))
          .andExpect(status().isBadRequest())
          .andReturn();

  ApiResponseJson<UserProfileDTO> response =
          ClientUtil.getGsonMapper()
                  .fromJson(mvcResult.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<UserProfileDTO>>() {}.getType());

  assertFalse(response.isSuccess());
 }
}
