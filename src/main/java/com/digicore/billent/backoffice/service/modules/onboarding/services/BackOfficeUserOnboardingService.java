package com.digicore.billent.backoffice.service.modules.onboarding.services;

import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.InviteBodyDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.notification.lib.request.NotificationRequestType;
import com.digicore.notification.lib.request.NotificationServiceRequest;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordFirstBaseRequestDTO;
import com.digicore.registhentication.authentication.services.PasswordResetService;
import com.digicore.registhentication.registration.services.RegistrationService;
import com.digicore.registhentication.util.IDGeneratorUtil;
import com.digicore.request.processor.annotations.MakerChecker;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-04(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserOnboardingService implements BackOfficeUserOnboardingValidatorService{
  private final RegistrationService<UserProfileDTO, UserRegistrationDTO> registrationService;
  private final PasswordResetService passwordResetService;
  private final NotificationDispatcher notificationDispatcher;
  private final PropertyConfig propertyConfig;

  @MakerChecker(
      checkerPermission = "approve-invite-backoffice-user",
      makerPermission = "invite-backoffice-user",
      requestClassName =
          "com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO")
  public Object onboardNewBackOfficeUser(Object requestDTO, Object... args) {
    UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) requestDTO;
    userRegistrationDTO.setPassword(IDGeneratorUtil.generateTempId());
    UserProfileDTO result = registrationService.createProfile(userRegistrationDTO);
    notificationDispatcher.dispatchEmail(
        NotificationServiceRequest.builder()
            .notificationSubject(propertyConfig.getInviteUserSubject())
            .recipients(List.of(result.getEmail()))
            .dateTime(LocalDateTime.now())
            .userCode(result.getPassword())
            .userRole(result.getAssignedRole())
            .firstName(result.getFirstName())
            .notificationRequestType(NotificationRequestType.SEND_INVITE_FOR_BACKOFFICE_EMAIL)
            .build());

    return result;
  }

  public void resendInvitation(InviteBodyDTO inviteBodyDTO) {
    String password = IDGeneratorUtil.generateTempId();
    passwordResetService.updateAccountPasswordWithoutVerification(
        inviteBodyDTO.getEmail(), password);
    notificationDispatcher.dispatchEmail(
        NotificationServiceRequest.builder()
            .notificationSubject(propertyConfig.inviteUserSubject)
            .recipients(List.of(inviteBodyDTO.getEmail()))
            .dateTime(LocalDateTime.now())
            .userCode(password)
            .userRole(inviteBodyDTO.getAssignedRole())
            .firstName(inviteBodyDTO.getFirstName())
            .notificationRequestType(NotificationRequestType.SEND_INVITE_FOR_BACKOFFICE_EMAIL)
            .build());
  }

  public void updateDefaultPassword(ResetPasswordFirstBaseRequestDTO resetPasswordFirstBaseRequestDTO, Principal principal) {
    resetPasswordFirstBaseRequestDTO.setEmail(principal.getName());
    passwordResetService.updateAccountPassword(resetPasswordFirstBaseRequestDTO);
    notificationDispatcher.dispatchEmail(
            NotificationServiceRequest.builder()
                    .notificationSubject(propertyConfig.passwordResetSubject)
                    .recipients(List.of(resetPasswordFirstBaseRequestDTO.getEmail()))
                    .dateTime(LocalDateTime.now())
                    .firstName(resetPasswordFirstBaseRequestDTO.getFirstName())
                    .notificationRequestType(NotificationRequestType.SEND_PASSWORD_UPDATE_EMAIL)
                    .build());
  }
}
