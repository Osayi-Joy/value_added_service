package com.digicore.billent.backoffice.service.test.integration.authentication;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUTHENTICATION_API_V1;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.MAKER_EMAIL;
import static com.digicore.billent.data.lib.modules.common.constants.SystemConstants.SYSTEM_DEFAULT_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.backoffice.service.test.integration.common.H2TestConfiguration;
import com.digicore.billent.backoffice.service.test.integration.common.TestHelper;
import com.digicore.common.util.ClientUtil;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.otp.service.OtpService;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordFirstBaseRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.enums.AuthenticationType;
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

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-17(Mon)-2023
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
 class BackOfficeAuthenticationTest {
    @Autowired
 private MockMvc mockMvc;
    @Autowired
    private PropertyConfig propertyConfig;

    @MockBean
    private OtpService otpService;
    @MockBean
    private NotificationDispatcher notificationDispatcher;

    @BeforeEach
      void  checkup() throws Exception {
        new H2TestConfiguration(propertyConfig);
        TestHelper testHelper = new TestHelper(mockMvc);
        testHelper.createTestRole();
    }

   @Test
   void getAccessToken() throws Exception {

       LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
       loginRequestDTO.setAuthenticationType(AuthenticationType.PASSWORD);
       loginRequestDTO.setPassword(SYSTEM_DEFAULT_PASSWORD);
       loginRequestDTO.setEmail(MAKER_EMAIL);
       loginRequestDTO.setUsername(MAKER_EMAIL);

       MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTHENTICATION_API_V1.concat("login"))
                       .content(ClientUtil.getGsonMapper().toJson(loginRequestDTO)).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();

       ApiResponseJson<LoginResponse> response = ClientUtil.getGsonMapper().fromJson(result.getResponse().getContentAsString(),ApiResponseJson.class);
       assertTrue(response.isSuccess());
      // return  response.getData().getAccessToken();

   }

   @Test
    void requestPasswordResetTest() throws Exception {
       MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTHENTICATION_API_V1.concat("password-forgotten?email=".concat(MAKER_EMAIL)))).andExpect(status().isOk()).andReturn();

       ApiResponseJson<Object> response = ClientUtil.getGsonMapper().fromJson(result.getResponse().getContentAsString(),ApiResponseJson.class);
       assertTrue(response.isSuccess());
   }

  @Test
  void validateEmailOtpWhenResettingPasswordTest() throws Exception {
        ResetPasswordFirstBaseRequestDTO resetPasswordDto = new ResetPasswordFirstBaseRequestDTO();
       resetPasswordDto.setEmail("test@unittest.com");
       resetPasswordDto.setOtp("1111");

       MvcResult result =
               mockMvc
                       .perform(
                               MockMvcRequestBuilders.post(AUTHENTICATION_API_V1.concat("verify-email-otp"))
                                       .content(ClientUtil.getGsonMapper().toJson(resetPasswordDto))
                                       .contentType(MediaType.APPLICATION_JSON))
                                       .andExpect(status().is4xxClientError())
                                       .andReturn();
       ApiResponseJson<?> response =
               ClientUtil.getGsonMapper()
                       .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
       assertFalse(response.isSuccess());
   }

    @Test
    void validateSmsOtpWhenResettingPasswordTest() throws Exception {
        ResetPasswordFirstBaseRequestDTO resetPasswordDto = new ResetPasswordFirstBaseRequestDTO();
        resetPasswordDto.setEmail("test@unittest.com");
        resetPasswordDto.setOtp("1111");

        MvcResult result =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.post(AUTHENTICATION_API_V1.concat("verify-email-otp"))
                                        .content(ClientUtil.getGsonMapper().toJson(resetPasswordDto))
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is4xxClientError())
                        .andReturn();
        ApiResponseJson<?> response =
                ClientUtil.getGsonMapper()
                        .fromJson(result.getResponse().getContentAsString(), ApiResponseJson.class);
        assertFalse(response.isSuccess());
    }

}
