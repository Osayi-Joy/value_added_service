package com.digicore.billent.backoffice.service.test.integration.registration;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-17(Mon)-2023
 */

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ONBOARDING_API_V1;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.InviteBodyDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.request.processor.approval_repository.ApprovalRequestsRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@Profile("test")
class BackOfficeUserOnboardingTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private NotificationDispatcher notificationDispatcher;

  @Autowired private  BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;


  @Autowired
  private PropertyConfig propertyConfig;

  @BeforeEach
  void  checkup(){
    new H2TestConfiguration(propertyConfig);
  }


  @Test
  void onboardNewBackOfficeUser() throws Exception {
    TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
    testHelper.updateMakerSelfPermissionByAddingNeededPermission("invite-backoffice-user");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("user-invitation"))
                    .content(
                        ClientUtil.getGsonMapper().toJson(testHelper.createBackOfficeProfile()))
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
    TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
    testHelper.updateMakerSelfPermissionByAddingNeededPermission("invite-backoffice-user");
    UserRegistrationDTO userRegistrationDTO = testHelper.createBackOfficeProfile();
    userRegistrationDTO.setAssignedRole("INVALID_ROLE");
    MvcResult result =
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("user-invitation"))
                                    .content(
                                            ClientUtil.getGsonMapper().toJson(userRegistrationDTO))
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
    TestHelper testHelper = new TestHelper(mockMvc, backOfficeUserAuthService);
    testHelper.updateMakerSelfPermissionByAddingNeededPermission("resend-invite-email");
    InviteBodyDTO inviteBodyDTO = new InviteBodyDTO();
    inviteBodyDTO.setAssignedRole(testHelper.createBackOfficeProfile().getAssignedRole());
    inviteBodyDTO.setEmail(testHelper.createBackOfficeProfile().getEmail());
    inviteBodyDTO.setFirstName(testHelper.createBackOfficeProfile().getFirstName());
    MvcResult result =
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("resending-of-user-invitation"))
                                    .content(
                                            ClientUtil.getGsonMapper().toJson(inviteBodyDTO))
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
