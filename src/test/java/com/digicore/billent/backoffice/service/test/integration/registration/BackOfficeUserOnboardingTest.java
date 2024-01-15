package com.digicore.billent.backoffice.service.test.integration.registration;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-17(Mon)-2023
 */

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ONBOARDING_API_V1;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.InviteBodyDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.billent.data.lib.modules.common.registration.dto.UserRegistrationDTO;
import com.digicore.billent.data.lib.modules.common.wallet.dto.CreateWalletResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordFirstBaseRequestDTO;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordSecondBaseRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class BackOfficeUserOnboardingTest {
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

  @Test
  void onboardNewBackOfficeUser() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    UserRegistrationDTO backOfficeProfile = testHelper.createBackOfficeProfile();
    backOfficeProfile.setPassword("Sup3rM@n1234567");
    
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("user-invitation"))
                    .content(
                        ClientUtil.getGsonMapper().toJson(backOfficeProfile))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();
    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
    assertTrue(response.isSuccess());
  }

  @Test
  void When_OnboardNewBackOfficeUser_ExpectStatus400() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    UserRegistrationDTO userRegistrationDTO = testHelper.createBackOfficeProfile();
    userRegistrationDTO.setAssignedRole("INVALID_ROLE");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("user-invitation"))
                    .content(ClientUtil.getGsonMapper().toJson(userRegistrationDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().is4xxClientError())
            .andReturn();
    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
    assertFalse(response.isSuccess());
  }

  @Test
  void resendInvitation() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    InviteBodyDTO inviteBodyDTO = new InviteBodyDTO();
    inviteBodyDTO.setAssignedRole(testHelper.createBackOfficeProfile().getAssignedRole());
    inviteBodyDTO.setEmail(testHelper.createBackOfficeProfile().getEmail());
    inviteBodyDTO.setFirstName(testHelper.createBackOfficeProfile().getFirstName());
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(
                        ONBOARDING_API_V1.concat("resending-of-user-invitation"))
                    .content(ClientUtil.getGsonMapper().toJson(inviteBodyDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();
    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
    assertTrue(response.isSuccess());
  }

  @Test
  void updateDefaultPassword() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc);
    ResetPasswordSecondBaseRequestDTO passwordFirstBaseRequestDTO =
        new ResetPasswordSecondBaseRequestDTO();
    passwordFirstBaseRequestDTO.setEmail("test@unittest.com");
    passwordFirstBaseRequestDTO.setOtp("1111");
    passwordFirstBaseRequestDTO.setNewPassword("tester@12ece432");
    UserAuthProfileDTO backOfficeUserAuthProfile = new UserAuthProfileDTO();
    backOfficeUserAuthProfile.setUsername(passwordFirstBaseRequestDTO.getEmail());
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("password-update"))
                    .content(ClientUtil.getGsonMapper().toJson(passwordFirstBaseRequestDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveValidAccessToken()))
            .andExpect(status().is4xxClientError())
            .andReturn();
    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
    assertFalse(response.isSuccess());
  }
}
