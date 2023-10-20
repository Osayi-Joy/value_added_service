package com.digicore.billent.backoffice.service.modules.onboarding.services;

import static com.digicore.billent.data.lib.modules.common.notification.NotificationSubject.INVITE_BACKOFFICE_USER_SUBJECT_KEY;
import static com.digicore.billent.data.lib.modules.common.notification.NotificationSubject.PASSWORD_RESET_SUCCESSFUL_SUBJECT_KEY;

import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.InviteBodyDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.registration.dto.UserRegistrationDTO;
import com.digicore.billent.data.lib.modules.common.settings.service.SettingService;
import com.digicore.notification.lib.request.NotificationRequestType;
import com.digicore.notification.lib.request.NotificationServiceRequest;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordSecondBaseRequestDTO;
import com.digicore.registhentication.authentication.services.PasswordResetService;
import com.digicore.registhentication.registration.services.RegistrationService;
import com.digicore.registhentication.util.IDGeneratorUtil;
import com.digicore.request.processor.annotations.MakerChecker;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import com.digicore.request.processor.processors.AuditLogProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-04(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserOnboardingService implements BackOfficeUserOnboardingValidatorService {
  private final RegistrationService<UserProfileDTO, UserRegistrationDTO>
      backOfficeUserRegistrationServiceImpl;
  private final PasswordResetService passwordResetServiceImpl;
  private final NotificationDispatcher notificationDispatcher;
  private final SettingService settingService;
  private final AuditLogProcessor auditLogProcessor;

  @MakerChecker(
      checkerPermission = "approve-invite-backoffice-user",
      makerPermission = "invite-backoffice-user",
      requestClassName =
          "com.digicore.billent.data.lib.modules.common.registration.dto.UserRegistrationDTO")
  public Object onboardNewBackOfficeUser(Object requestDTO, Object... args) {
    UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) requestDTO;
    userRegistrationDTO.setPassword(IDGeneratorUtil.generateTempId());
    UserProfileDTO result =
        backOfficeUserRegistrationServiceImpl.createProfile(userRegistrationDTO);
    notificationDispatcher.dispatchEmail(
        NotificationServiceRequest.builder()
            .notificationSubject(settingService.retrieveValue(INVITE_BACKOFFICE_USER_SUBJECT_KEY))
            .recipients(List.of(result.getEmail()))
            .dateTime(LocalDateTime.now())
            .userCode(result.getPassword())
            .userRole(result.getAssignedRole())
            .firstName(result.getFirstName())
            .notificationRequestType(NotificationRequestType.SEND_INVITE_FOR_BACKOFFICE_EMAIL)
            .build());
    auditLogProcessor.saveAuditWithDescription(AuditLogActivity.APPROVE_INVITE_USER,AuditLogActivity.BACKOFFICE,AuditLogActivity.APPROVE_INVITE_USER_DESCRIPTION.replace("{}",userRegistrationDTO.getEmail()));

    return result;
  }

  public void resendInvitation(InviteBodyDTO inviteBodyDTO) {
    String password = IDGeneratorUtil.generateTempId();
    passwordResetServiceImpl.updateAccountPasswordWithoutVerification(
        inviteBodyDTO.getEmail(), password);
    notificationDispatcher.dispatchEmail(
        NotificationServiceRequest.builder()
            .notificationSubject(settingService.retrieveValue(INVITE_BACKOFFICE_USER_SUBJECT_KEY))
            .recipients(List.of(inviteBodyDTO.getEmail()))
            .dateTime(LocalDateTime.now())
            .userCode(password)
            .userRole(inviteBodyDTO.getAssignedRole())
            .firstName(inviteBodyDTO.getFirstName())
            .notificationRequestType(NotificationRequestType.SEND_INVITE_FOR_BACKOFFICE_EMAIL)
            .build());
  }

  public void updateDefaultPassword(
      ResetPasswordSecondBaseRequestDTO resetPasswordFirstBaseRequestDTO, Principal principal) {
    resetPasswordFirstBaseRequestDTO.setEmail(principal.getName());
    passwordResetServiceImpl.updateAccountPassword(resetPasswordFirstBaseRequestDTO);
    notificationDispatcher.dispatchEmail(
        NotificationServiceRequest.builder()
            .notificationSubject(
                settingService.retrieveValue(PASSWORD_RESET_SUCCESSFUL_SUBJECT_KEY))
            .recipients(List.of(resetPasswordFirstBaseRequestDTO.getEmail()))
            .dateTime(LocalDateTime.now())
            .notificationRequestType(NotificationRequestType.SEND_PASSWORD_UPDATE_EMAIL)
            .build());
    auditLogProcessor.saveAuditWithDescription(AuditLogActivity.PASSWORD_UPDATE,AuditLogActivity.BACKOFFICE,AuditLogActivity.PASSWORD_UPDATE_DESCRIPTION);

  }
}
