package com.digicore.billent.backoffice.service.test.integration.common;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.APPROVAL_API_V1;
import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUTHENTICATION_API_V1;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.common.util.ClientUtil;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.enums.AuthenticationType;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-17(Mon)-2023
 */
@Component
@Slf4j
public class TestHelper {
  /*
    This class is contains already defined methods that would prove useful for integration test
  */
  private final MockMvc mockMvc;

  private final BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService;

  public TestHelper(
      MockMvc mockMvc,
      BackOfficeUserAuthService<BackOfficeUserAuthProfileDTO> backOfficeUserAuthService) {
    this.mockMvc = mockMvc;
    this.backOfficeUserAuthService = backOfficeUserAuthService;
  }

  @NotNull
  private static String getAccessToken(MvcResult result) throws UnsupportedEncodingException {
    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(result.getResponse().getContentAsString().trim(), ApiResponseJson.class);
    assertTrue(response.isSuccess());

    String loginResponseInString = ClientUtil.getGsonMapper().toJson(response.getData());

    LoginResponse loginResponse =
        ClientUtil.getGsonMapper().fromJson(loginResponseInString, LoginResponse.class);
    return "Bearer ".concat(loginResponse.getAccessToken());
  }

  /*
    This method is useful for getting  a valid jwt for integration test
  */
  public String retrieveValidAccessToken() throws Exception {
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
    loginRequestDTO.setAuthenticationType(AuthenticationType.PASSWORD);
    loginRequestDTO.setPassword(SYSTEM_DEFAULT_PASSWORD);
    loginRequestDTO.setEmail(MAKER_EMAIL);
    loginRequestDTO.setUsername(MAKER_EMAIL);

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(AUTHENTICATION_API_V1.concat("login"))
                    .content(ClientUtil.getGsonMapper().toJson(loginRequestDTO))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    return getAccessToken(result);
  }

  /*
    This method is useful for creating user registration object
  */
  public UserRegistrationDTO createBackOfficeProfile() {
    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
    userRegistrationDTO.setEmail("tobiogunwuyi@gmail.com");
    userRegistrationDTO.setPhoneNumber("2347087982874");
    userRegistrationDTO.setFirstName("Oluwatobi");
    userRegistrationDTO.setLastName("Ogunwuyi");
    userRegistrationDTO.setAssignedRole(MAKER_ROLE_NAME);
    userRegistrationDTO.setUsername("tobiogunwuyi@gmail.com");
    return userRegistrationDTO;
  }

  /*
    This method is useful for updating the user permission to the
    needed permission required to call an endpoint
  */
  public void updateMakerSelfPermissionByAddingNeededPermission(String permissionName) {
    PermissionDTO permissionDTO = new PermissionDTO();
    permissionDTO.setName(permissionName);
    BackOfficeUserAuthProfileDTO backOfficeUserAuthProfileDTO = new BackOfficeUserAuthProfileDTO();
    backOfficeUserAuthProfileDTO.setUsername(MAKER_EMAIL);
    backOfficeUserAuthProfileDTO.setPermissions(Collections.singleton(permissionDTO));
    backOfficeUserAuthService.updateAuthProfile(backOfficeUserAuthProfileDTO);
  }

  /*
    This method is useful for approving a request going through maker checker,
    it is important to note that the requestId is incremental by 1.
    So always check the previous test case that made call last to get the last used requestId
    and increase it by 1
  */
  public void approvalRequest(Long requestId) throws Exception {
    updateMakerSelfPermissionByAddingNeededPermission("approve-invite-backoffice-user");
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(APPROVAL_API_V1.concat("treat-request-" + requestId))
                .header("Authorization", retrieveValidAccessToken()))
        .andExpect(status().isOk())
        .andReturn();
  }
}
