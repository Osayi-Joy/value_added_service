package com.digicore.billent.backoffice.service.test.integration.registration;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-17(Mon)-2023
 */

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.ONBOARDING_API_V1;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.InviteBodyDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.model.Role;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.common.util.ClientUtil;
import com.digicore.otp.service.NotificationDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class BackOfficeUserOnboardingTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private RoleService<RoleDTO, Role> roleService;
  @Autowired private NotificationDispatcher notificationDispatcher;

  @Autowired private  BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;

  @Test
  void onboardNewBackOfficeUser() throws Exception {
    log.trace("about to test onboard");
    TestHelper testHelper = new TestHelper(mockMvc, roleService, backOfficeUserAuthService);
    testHelper.updateMakerSelfPermissionByAddingNeededPermission("invite-backoffice-user");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("user-invitation"))
                    .content(
                        ClientUtil.getGsonMapper().toJson(testHelper.createBackOfficeProfile()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", testHelper.retrieveMakerAccessToken()))
            .andExpect(status().isOk())
            .andReturn();
    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
    assertTrue(response.isSuccess());
    testHelper.approvalRequest(1L);
  }

  @Test
  void resendInvitation() throws Exception {
    log.trace("about to test resend");
    TestHelper testHelper = new TestHelper(mockMvc, roleService, backOfficeUserAuthService);
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
                                    .header("Authorization", testHelper.retrieveMakerAccessToken()))
                    .andExpect(status().isOk())
                    .andReturn();
    ApiResponseJson<?> response =
            ClientUtil.getGsonMapper()
                    .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
    assertTrue(response.isSuccess());
  }
}
