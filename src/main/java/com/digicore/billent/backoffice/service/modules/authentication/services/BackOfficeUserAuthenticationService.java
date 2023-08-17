package com.digicore.billent.backoffice.service.modules.authentication.services;

import com.digicore.api.helper.response.ApiResponseJson;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.implementation.BackOfficeUserAuthServiceImpl;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.notification.lib.request.NotificationRequestType;
import com.digicore.notification.lib.request.NotificationServiceRequest;
import com.digicore.otp.enums.OtpType;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.otp.service.OtpService;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordFirstBaseRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.services.LoginService;
import java.util.List;

import com.digicore.registhentication.authentication.services.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jun-27(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserAuthenticationService {
  private final LoginService<LoginResponse, LoginRequestDTO> userAuthService;
  private final NotificationDispatcher notificationDispatcher;
  private final PropertyConfig propertyConfig;

  private final PasswordResetService passwordResetService;

  private final BackOfficeUserAuthServiceImpl backOfficeUserAuthService;

  private final OtpService otpService;

  public LoginResponse authenticateBackOfficeUser(LoginRequestDTO loginRequestDTO) {
    LoginResponse loginResponse = userAuthService.authenticate(loginRequestDTO);
    notificationDispatcher.dispatchEmail(
        NotificationServiceRequest.builder()
            .recipients(List.of(loginResponse.getAdditionalInformation().get("email").toString()))
            .notificationSubject(propertyConfig.getSuccessLoginSubject())
            .firstName((String) loginResponse.getAdditionalInformation().get("firstName"))
            .notificationRequestType(NotificationRequestType.SEND_LOGIN_SUCCESS_EMAIL)
            .build());
    return loginResponse;
  }

  public ApiResponseJson<Object> requestPasswordRequest(String email){
    UserAuthProfileDTO userAuthProfileDTO = backOfficeUserAuthService.retrieveAuthProfile(email);
    notificationDispatcher.dispatchEmail(
            NotificationServiceRequest.builder()
                    .recipients(List.of(email))
                    .notificationSubject("PASSWORD_RESET_OTP")
                    .firstName(userAuthProfileDTO.getUserProfile().getFirstName())
                    .notificationRequestType(NotificationRequestType.SEND_PASSWORD_UPDATE_EMAIL)
                    .build());
    return ApiResponseJson.builder().success(true).data(null).build();
  }

  public ApiResponseJson<Object> validateEmailVerificationAndSendSmsOtp(ResetPasswordFirstBaseRequestDTO resetPasswordDto){
    UserAuthProfileDTO userAuthProfileDTO = backOfficeUserAuthService.retrieveAuthProfile(resetPasswordDto.getEmail());
    otpService.effect(
        resetPasswordDto.getEmail(), OtpType.PASSWORD_UPDATE, resetPasswordDto.getResetKey());
    otpService.send(NotificationServiceRequest.builder().firstName(userAuthProfileDTO.getUserProfile().getFirstName()).phoneNumbers(List.of(userAuthProfileDTO.getUserProfile().getPhoneNumber())).notificationRequestType(NotificationRequestType.SEND_SMS).channel("SMS").build(), OtpType.PHONE_NUMBER_VERIFICATION);
    return ApiResponseJson.builder().success(true).build();
  }

  public ApiResponseJson<Object> validateSmsVerification(ResetPasswordFirstBaseRequestDTO resetPasswordDto){
    otpService.effect(
        resetPasswordDto.getEmail(), OtpType.PASSWORD_UPDATE, resetPasswordDto.getResetKey());
    return ApiResponseJson.builder().success(true).build();

    }

    public ApiResponseJson<Object> resetPassword(ResetPasswordFirstBaseRequestDTO resetPasswordDto){
      UserAuthProfileDTO userAuthProfileDTO = backOfficeUserAuthService.retrieveAuthProfile(resetPasswordDto.getEmail());
      checkPasswordCriteria(resetPasswordDto.getNewPassword());
      passwordResetService.updateAccountPasswordWithoutVerification(resetPasswordDto.getEmail(),resetPasswordDto.getNewPassword());
      notificationDispatcher.dispatchEmail(
              NotificationServiceRequest.builder()
                      .recipients(List.of(resetPasswordDto.getEmail()))
                      .notificationSubject("PASSWORD_RESET")
                      .firstName(userAuthProfileDTO.getUserProfile().getFirstName())
                      .notificationRequestType(NotificationRequestType.SEND_PASSWORD_UPDATE_EMAIL)
                      .build());
      return ApiResponseJson.builder().success(true).build();

    }

  private void checkPasswordCriteria(String newPassword) {
      //not the same as 5 previous passwords
  }


}
