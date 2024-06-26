package com.digicore.billent.backoffice.service.test.integration.common;


import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.*;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserEditDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.billent.data.lib.modules.common.authorization.dto.PermissionDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleCreationDTO;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.registration.dto.UserRegistrationDTO;
import com.digicore.billent.data.lib.modules.common.wallet.dto.CreateWalletResponseData;
import com.digicore.common.util.ClientUtil;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.enums.AuthenticationType;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Set;


import com.digicore.registhentication.registration.enums.Status;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
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
@Profile("test")
public class TestHelper {
  /*
    This class is contains already defined methods that would prove useful for integration test
  */
  private final MockMvc mockMvc;


  public TestHelper(
          MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }


  private static String getAccessToken(MvcResult result) throws UnsupportedEncodingException {
    LoginResponse loginResponse = getLoginResponse(result);
    return "Bearer ".concat(loginResponse.getAccessToken());
  }

  private static LoginResponse getLoginResponse(MvcResult result) throws UnsupportedEncodingException {
    ApiResponseJson<?> response = getApiResponseJson(result);

    String loginResponseInString = ClientUtil.getGsonMapper().toJson(response.getData());

    return ClientUtil.getGsonMapper().fromJson(loginResponseInString, LoginResponse.class);
  }



  private static ApiResponseJson<?> getApiResponseJson(MvcResult result) throws UnsupportedEncodingException {
    ApiResponseJson<?> response =
        ClientUtil.getGsonMapper()
            .fromJson(result.getResponse().getContentAsString().trim(), ApiResponseJson.class);
    assertTrue(response.isSuccess());
    return response;
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

  public LoginResponse authenticateWithNewUserCredentials(String username, String password) throws Exception {
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
    loginRequestDTO.setAuthenticationType(AuthenticationType.PASSWORD);
    loginRequestDTO.setPassword(password);
    loginRequestDTO.setEmail(username);
    loginRequestDTO.setUsername(username);

    MvcResult result =
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.post(AUTHENTICATION_API_V1.concat("login"))
                                    .content(ClientUtil.getGsonMapper().toJson(loginRequestDTO))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

    return getLoginResponse(result);
  }

  /*
    This method is useful for creating user registration object
  */
  public UserRegistrationDTO createBackOfficeProfile() {
    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
    userRegistrationDTO.setEmail("tobiogunwuyi@gmail.com");
    setCommonFieldsInUserRegistrationBody(userRegistrationDTO);
    userRegistrationDTO.setUsername("tobiogunwuyi@gmail.com");
    return userRegistrationDTO;
  }

  private static void setCommonFieldsInUserRegistrationBody(UserRegistrationDTO userRegistrationDTO) {
    userRegistrationDTO.setPhoneNumber("2347087982874");
    userRegistrationDTO.setFirstName("Oluwatobi");
    userRegistrationDTO.setLastName("Ogunwuyi");
    userRegistrationDTO.setAssignedRole("TesterRole");
  }

  public UserRegistrationDTO createBackOfficeProfile(String username) {
    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
    userRegistrationDTO.setEmail(username);
    setCommonFieldsInUserRegistrationBody(userRegistrationDTO);
    userRegistrationDTO.setUsername(username);
    return userRegistrationDTO;
  }

  /*
    This method is useful for updating the user permission to the
    needed permission required to call an endpoint
  */
//  public void updateMakerSelfPermissionByAddingNeededPermission(String permissionName) {
//    PermissionDTO permissionDTO = new PermissionDTO();
//    permissionDTO.setName(permissionName);
//    UserAuthProfileDTO backOfficeUserAuthProfileDTO = new UserAuthProfileDTO();
//    backOfficeUserAuthProfileDTO.setUsername(MAKER_EMAIL);
//    backOfficeUserAuthProfileDTO.setPermissions(Collections.singleton(permissionDTO));
//    backOfficeUserAuthProfileDTO.setStatus(Status.ACTIVE);
//    backOfficeUserAuthProfileDTO.setAssignedRole(MAKER_ROLE_NAME);
//    backOfficeUserAuthServiceImpl.updateAuthProfile(backOfficeUserAuthProfileDTO);
//  }

  public void createTestUser(String username) throws Exception {
    UserRegistrationDTO userRegistrationDTO = createBackOfficeProfile(username);
    userRegistrationDTO.setAssignedRole("TesterRole");
    MvcResult result =
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.post(ONBOARDING_API_V1.concat("user-invitation"))
                                    .content(ClientUtil.getGsonMapper().toJson(userRegistrationDTO))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", retrieveValidAccessToken()))
                    .andExpect(status().isOk())
                    .andReturn();
  }
  public void createTestRoleCustom(String roleName) throws Exception {
    RoleCreationDTO roleCreationDTO = new RoleCreationDTO();
    roleCreationDTO.setName(roleName);
    roleCreationDTO.setDescription("tester tester");
    roleCreationDTO.setPermissions(Set.of("create-roles","edit-role","view-backoffice-users","view-roles","view-role-details","view-backoffice-user-details","view-billers", "view-self-user-details",
            "delete-backoffice-profile","disable-backoffice-profile","edit-backoffice-user-details","invite-backoffice-user","resend-invite-email","view-permissions","delete-role"));


   MvcResult mvcResult =  mockMvc.perform(post(ROLES_API_V1 + "creation")
                    .content(
                            ClientUtil.getGsonMapper().toJson(roleCreationDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization",retrieveValidAccessToken()))
//            .andExpect(status().isOk())
            .andReturn();
//
//    ApiResponseJson<RoleDTO> response =
//            ClientUtil.getGsonMapper()
//                    .fromJson(mvcResult.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());
//
//    assertTrue(response.isSuccess());
    updateUserRole();

  }
  public void createTestRole() throws Exception {
    RoleCreationDTO roleCreationDTO = new RoleCreationDTO();
    roleCreationDTO.setName("TesterRole");
    roleCreationDTO.setDescription("tester tester");
    roleCreationDTO.setPermissions(Set.of("create-roles","edit-role","view-backoffice-users","view-roles","view-role-details","view-backoffice-user-details","view-billers","edit-billers","enable-biller","export-biller-products","enable-biller-product","export-resellers", "view-dashboard", "view-self-user-details",
            "delete-backoffice-profile","disable-backoffice-profile","edit-backoffice-user-details","invite-backoffice-user","resend-invite-email","view-permissions","delete-role","disable-biller","view-biller-products","disable-biller-product","view-resellers", "view-all-audit-trails","view-all-wallet-balances",
            "view-all-wallets","credit-wallet","view-all-wallet-transactions","export-wallets","disable-role","enable-role","disable-reseller-user","enable-reseller-user"));


   MvcResult mvcResult =  mockMvc.perform(post(ROLES_API_V1 + "creation")
                    .content(
                            ClientUtil.getGsonMapper().toJson(roleCreationDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization",retrieveValidAccessToken()))
//            .andExpect(status().isOk())
            .andReturn();
//
//    ApiResponseJson<RoleDTO> response =
//            ClientUtil.getGsonMapper()
//                    .fromJson(mvcResult.getResponse().getContentAsString().trim(), new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());
//
//    assertTrue(response.isSuccess());
    updateUserRole();

  }

  public void updateUserRole() throws Exception {
    UserEditDTO userProfileDTO = new UserEditDTO();
    userProfileDTO.setEmail(MAKER_EMAIL);
    userProfileDTO.setFirstName("John");
    userProfileDTO.setLastName("Doe");
    userProfileDTO.setAssignedRole("TesterRole");
//    userProfileDTO.setPermissions(Set.of("create-roles","edit-role","view-backoffice-users","view-roles","view-role-details","view-backoffice-user-details","view-billers","edit-billers","enable-biller","disable-biller","export-biller-products","export-resellers",
//            "delete-backoffice-profile","disable-backoffice-profile","edit-backoffice-user-details","invite-backoffice-user","resend-invite-email","view-permissions","delete-role","view-biller-products","disable-biller-product","enable-biller-product","view-resellers"));
    userProfileDTO.setPhoneNumber("2349061962179");
    userProfileDTO.setUsername(MAKER_EMAIL);


            mockMvc
                    .perform(
                            patch(PROFILE_API_V1.concat("edit"))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(ClientUtil.getGsonMapper().toJson(userProfileDTO))
                                    .header("Authorization", retrieveValidAccessToken()))
                    .andExpect(status().isOk())
                    .andReturn();

  }

  public void updateTestRole(String permission) throws Exception {
    RoleCreationDTO roleDTO = new RoleCreationDTO();
    roleDTO.setName("TesterRole");
    roleDTO.setDescription("tester tester");
    roleDTO.setPermissions(Set.of("edit-role",permission));

    mockMvc.perform(patch(ROLES_API_V1 + "edit")
                    .content(
                            ClientUtil.getGsonMapper().toJson(roleDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization",retrieveValidAccessToken()))
            .andExpect(status().isOk())
            .andReturn();

  }

  public CreateWalletResponseData initializeWalletResponse(String username) {
      CreateWalletResponseData createWalletResponseData = new CreateWalletResponseData();
      createWalletResponseData.setCurrency("NGN");
      createWalletResponseData.setWalletName("Wallet Name");
      createWalletResponseData.setSystemWalletId("WA_TESTID");
      createWalletResponseData.setCustomerId(username);
      createWalletResponseData.setCustomerName("Oluwatobi Ogunwuyi");
      return createWalletResponseData;
  }

  /*
    This method is useful for approving a request going through maker checker,
    it is important to note that the requestId is incremental by 1.
    So always check the previous test case that made call last to get the last used requestId
    and increase it by 1
  */



//  public void approvalRequest(Long requestId,String requiredPermission) throws Exception {
//    updateMakerSelfPermissionByAddingNeededPermission(requiredPermission);
//    mockMvc
//            .perform(
//                    MockMvcRequestBuilders.post(APPROVAL_API_V1.concat("treat-request-" + requestId))
//                            .header("Authorization", retrieveValidAccessToken()))
//            .andExpect(status().isOk())
//            .andReturn();
//  }

  public void disableRole(String roleName) throws Exception{

    MvcResult mvcResult =
            mockMvc
                    .perform(
                            patch(ROLES_API_V1 + "disable-"+roleName)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", retrieveValidAccessToken()))
                    .andExpect(status().isOk())
                    .andReturn();

    ApiResponseJson<RoleDTO> response =
            ClientUtil.getGsonMapper()
                    .fromJson(
                            mvcResult.getResponse().getContentAsString().trim(),
                            new TypeToken<ApiResponseJson<RoleDTO>>() {}.getType());

    assertTrue(response.isSuccess());
  }
}
