package com.digicore.billent.backoffice.service.modules.authentication.services;

import com.digicore.billent.data.lib.modules.backoffice.authentication.service.implementation.BackOfficeUserAuthServiceImpl;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.billent.data.lib.modules.common.settings.service.SettingService;
import com.digicore.notification.lib.request.NotificationRequestType;
import com.digicore.notification.lib.request.NotificationServiceRequest;
import com.digicore.otp.enums.OtpType;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.otp.service.OtpService;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordFirstBaseRequestDTO;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordSecondBaseRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.services.LoginService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digicore.registhentication.authentication.services.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.digicore.billent.data.lib.modules.common.notification.NotificationSubject.LOGIN_SUCCESSFUL_SUBJECT_KEY;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jun-27(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserAuthenticationService {
  private final LoginService<LoginResponse, LoginRequestDTO> backOfficeUserAuthServiceImpl;
  private final AuthProfileService<UserAuthProfileDTO> backOfficeUserAuthProfileServiceImpl;
  private final NotificationDispatcher notificationDispatcher;
  private final SettingService settingService;

  private final PasswordResetService passwordResetService;

  private final OtpService otpService;

  @Value("${password-reset-subject: Password Reset}")
  private String passwordResetSubject;

  public LoginResponse authenticateBackOfficeUser(LoginRequestDTO loginRequestDTO) {
    LoginResponse loginResponse = backOfficeUserAuthServiceImpl.authenticate(loginRequestDTO);
    notificationDispatcher.dispatchEmail(
        NotificationServiceRequest.builder()
            .recipients(List.of(loginResponse.getAdditionalInformation().get("email").toString()))
            .notificationSubject(settingService.retrieveValue(LOGIN_SUCCESSFUL_SUBJECT_KEY))
            .firstName((String) loginResponse.getAdditionalInformation().get("firstName"))
            .notificationRequestType(NotificationRequestType.SEND_LOGIN_SUCCESS_EMAIL)
            .build());
    return loginResponse;
  }

  public void requestPasswordReset(String email){
    UserAuthProfileDTO userAuthProfileDTO = backOfficeUserAuthProfileServiceImpl.retrieveAuthProfile(email);
    otpService.send(
            NotificationServiceRequest.builder()
                    .recipients(List.of(email))
                    .notificationSubject(passwordResetSubject)
                    .firstName(userAuthProfileDTO.getUserProfile().getFirstName())
                    .notificationRequestType(NotificationRequestType.SEND_ACCOUNT_RECOVERY_EMAIL)
                    .build(), OtpType.PASSWORD_UPDATE);

  }

  @Transactional
  public void validateEmailVerificationAndSendSmsOtp(ResetPasswordFirstBaseRequestDTO resetPasswordDto){
    UserAuthProfileDTO userAuthProfileDTO = backOfficeUserAuthProfileServiceImpl.retrieveAuthProfile(resetPasswordDto.getEmail());
    otpService.effect(
        resetPasswordDto.getEmail(), OtpType.PASSWORD_UPDATE, resetPasswordDto.getOtp());
    otpService.send(
            NotificationServiceRequest.builder()
                    .recipients(List.of(resetPasswordDto.getEmail().concat(userAuthProfileDTO.getUserProfile().getPhoneNumber())))
                    .firstName(userAuthProfileDTO.getUserProfile().getFirstName())
                    .phoneNumbers(List.of(userAuthProfileDTO.getUserProfile().getPhoneNumber()))
                    .notificationRequestType(NotificationRequestType.SEND_SMS)
                    .channel("SMS").build(), OtpType.PASSWORD_UPDATE);
  }

  public Map<String, Object> validateSmsVerification(ResetPasswordFirstBaseRequestDTO resetPasswordDto){
    UserAuthProfileDTO userAuthProfileDTO = backOfficeUserAuthProfileServiceImpl.retrieveAuthProfile(resetPasswordDto.getEmail());
    otpService.effect(
        resetPasswordDto.getEmail().concat(userAuthProfileDTO.getUserProfile().getPhoneNumber()), OtpType.PASSWORD_UPDATE, resetPasswordDto.getOtp());
    Map<String, Object> response = new HashMap<>();
    response.put("resetKey", otpService.store(resetPasswordDto.getEmail().concat(userAuthProfileDTO.getUserProfile().getPhoneNumber()), OtpType.PASSWORD_UPDATE_RECOVERY_KEY));
    return response;

    }
    @Transactional
    public void resetPassword(ResetPasswordSecondBaseRequestDTO resetPasswordDto){
      UserAuthProfileDTO userAuthProfileDTO = backOfficeUserAuthProfileServiceImpl.retrieveAuthProfile(resetPasswordDto.getEmail());
      otpService.effect(resetPasswordDto.getEmail().concat(userAuthProfileDTO.getUserProfile().getPhoneNumber()),OtpType.PASSWORD_UPDATE_RECOVERY_KEY,resetPasswordDto.getOtp());
      passwordResetService.updateAccountPasswordWithoutVerification(resetPasswordDto.getEmail(),resetPasswordDto.getNewPassword());
      notificationDispatcher.dispatchEmail(
              NotificationServiceRequest.builder()
                      .recipients(List.of(resetPasswordDto.getEmail()))
                      .notificationSubject(passwordResetSubject)
                      .firstName(userAuthProfileDTO.getUserProfile().getFirstName())
                      .notificationRequestType(NotificationRequestType.SEND_PASSWORD_UPDATE_EMAIL)
                      .build());

    }
}
