package com.digicore.billent.backoffice.service.test.integration.profile;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PROFILE_API_V1;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.wallet.dto.CreateWalletResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class BackOfficeProfileControllerTest {
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

  private static PaginatedResponseDTO<UserProfileDTO> getPaginatedResponseDTO(MvcResult result)
      throws UnsupportedEncodingException, JsonProcessingException {
    ApiResponseJson<PaginatedResponseDTO<UserProfileDTO>> response =
        ClientUtil.getGsonMapper()
            .fromJson(
                result.getResponse().getContentAsString().trim(),
                new TypeToken<
                    ApiResponseJson<PaginatedResponseDTO<UserProfileDTO>>>() {}.getType());
    assertTrue(response.isSuccess());

    return response.getData();
  }



  @Test
  void testGetAllBackOfficeUserProfiles() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    int pageNumber = 0;
    int pageSize = 10;

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(PROFILE_API_V1 + "get-all")
                    .param("pageNumber", String.valueOf(pageNumber))
                    .param("pageSize", String.valueOf(pageSize))
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
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
  void testGetBackOfficeUserProfile() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(PROFILE_API_V1 + "get-".concat(MAKER_EMAIL).concat("-details"))
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<UserProfileDTO> response =
        ClientUtil.getObjectMapper()
            .readValue(
                mvcResult.getResponse().getContentAsString().trim(), new TypeReference<>() {});
    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    UserProfileDTO responseData = response.getData();
    assertEquals(MAKER_EMAIL, responseData.getEmail());
  }

  @Test
  void testSearchBackOfficeUserProfiles() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    int pageNumber = 0;
    int pageSize = 10;

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(PROFILE_API_V1 + "search")
                    .param(PAGE_NUMBER, String.valueOf(pageNumber))
                    .param(PAGE_SIZE, String.valueOf(pageSize))
                    .param(KEY, "email")
                    .param(VALUE, "system")
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
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
    TestHelper testHelper = new TestHelper(mockMvc);
    int pageNumber = 0;
    int pageSize = 10;

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(PROFILE_API_V1 + "filter")
                    .param(PAGE_NUMBER, String.valueOf(pageNumber))
                    .param(PAGE_SIZE, String.valueOf(pageSize))
                    .param(STATUS, "PENDING_INVITE_ACCEPTANCE")
                    .param(START_DATE, "2021-01-01")
                    .param(END_DATE, "9021-01-01")
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
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
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult =
        mockMvc
            .perform(
                delete(PROFILE_API_V1.concat("remove-".concat(CHECKER_EMAIL)))
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
  void testDisableUserProfile_ProfileExists() throws Exception {

    TestHelper testHelper = new TestHelper(mockMvc);
    testHelper.createTestUser("disableuser@test.com");

    MvcResult mvcResult =
        mockMvc
            .perform(
                patch(PROFILE_API_V1.concat("disable-").concat("disableuser@test.com"))
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
  void testUpdateUserProfile_ProfileExists() throws Exception {
    UserProfileDTO userProfileDTO = new UserProfileDTO();
    userProfileDTO.setEmail(MAKER_EMAIL);
    userProfileDTO.setFirstName("John");
    userProfileDTO.setLastName("Doe");
    userProfileDTO.setAssignedRole("TesterRole");
    userProfileDTO.setPhoneNumber("2349061962179");
    userProfileDTO.setUsername(MAKER_EMAIL);

    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult mvcResult =
        mockMvc
            .perform(
                patch(PROFILE_API_V1.concat("edit"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ClientUtil.getGsonMapper().toJson(userProfileDTO))
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(mvcResult.getResponse().getContentAsString(), ApiResponseJson.class);
    assertTrue(response.isSuccess());
  }

  //todo joy to rework this
//  @Test
//  void changePasswordTest() throws Exception {
//    TestHelper testHelper = new TestHelper(mockMvc);
//    UpdatePasswordRequestDTO requestDTO = new UpdatePasswordRequestDTO();
//    requestDTO.setOldPassword(testPassword);
//    requestDTO.setNewPassword("joyosayi@1234567");
//
//    MvcResult result =
//            mockMvc
//                    .perform(
//                            MockMvcRequestBuilders.patch(PROFILE_API_V1.concat("change-password"))
//                                    .content(ClientUtil.getGsonMapper().toJson(requestDTO))
//                                    .contentType(MediaType.APPLICATION_JSON)
//                    .header("Authorization", testHelper.retrieveValidAccessToken()))
//                    .andExpect(status().isOk())
//                    .andReturn();
//    ApiResponseJson<?> response =
//            ClientUtil.getGsonMapper()
//                    .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
//    assertTrue(response.isSuccess());
//  }

  @Test
  void viewProfileDetailsTest() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);

    MvcResult result =
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.get(PROFILE_API_V1.concat("get-self"))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", testHelper.retrieveValidAccessToken()))
                    .andExpect(status().isOk())
                    .andReturn();
    ApiResponseJson<?> response =
            ClientUtil.getGsonMapper()
                    .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
    assertTrue(response.isSuccess());
  }
}
