package com.digicore.billent.backoffice.service.modules.profiles.service;

import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserEditDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.profile.UserProfileService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.common.util.ClientUtil;
import com.digicore.notification.lib.request.NotificationRequestType;
import com.digicore.notification.lib.request.NotificationServiceRequest;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.authentication.dtos.request.UpdatePasswordRequestDTO;
import com.digicore.registhentication.authentication.services.PasswordResetService;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.AuditLogProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */

@Service
@RequiredArgsConstructor
public class BackOfficeUserProfileOperations implements BackOfficeUserProfileValidatorService {
  private final UserProfileService<UserProfileDTO> backOfficeUserProfileServiceImpl;
  private final AuditLogProcessor auditLogProcessor;
  private final AuthProfileService<UserAuthProfileDTO> backOfficeUserAuthProfileServiceImpl;
  private final NotificationDispatcher notificationDispatcher;

  private final PasswordResetService passwordResetService;
  @Value("${password-update-subject: Password Update}")
  private String passwordUpdateSubject;

  public PaginatedResponseDTO<UserProfileDTO> fetchAllBackOfficeUserProfiles(int page, int size) {
    return backOfficeUserProfileServiceImpl.retrieveAllUserProfiles(page, size);
  }

  public UserProfileDTO fetchBackOfficeUserProfile(String email) {
    return backOfficeUserProfileServiceImpl.retrieveUserProfile(email);
  }

  public PaginatedResponseDTO<UserProfileDTO> filterOrSearch(
      BillentSearchRequest billentSearchRequest) {
    return backOfficeUserProfileServiceImpl.filterOrSearch(billentSearchRequest);
  }

  @MakerChecker(
          checkerPermission = "approve-delete-backoffice-profile",
          makerPermission = "delete-backoffice-profile",
          requestClassName = "com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO")
  public Object deleteBackofficeProfile(Object request, Object... args) {
    UserProfileDTO userProfileDTO = (UserProfileDTO) request;
    backOfficeUserProfileServiceImpl.deleteUserProfile(userProfileDTO.getEmail());
    auditLogProcessor.saveAuditWithDescription(AuditLogActivity.APPROVE_DELETE_PROFILE,AuditLogActivity.BACKOFFICE,AuditLogActivity.APPROVE_DELETE_PROFILE_DESCRIPTION.replace("{}",userProfileDTO.getEmail()));
    return Optional.empty();
  }

  @MakerChecker(
          checkerPermission = "approve-disable-backoffice-profile",
          makerPermission = "disable-backoffice-profile",
          requestClassName = "com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO")
  public Object disableBackofficeProfile(Object request, Object... args) {
    UserProfileDTO userProfileDTO = (UserProfileDTO) request;
    backOfficeUserProfileServiceImpl.disableUserProfile(userProfileDTO.getEmail());
    auditLogProcessor.saveAuditWithDescription(AuditLogActivity.APPROVE_DISABLE_PROFILE,AuditLogActivity.BACKOFFICE,AuditLogActivity.APPROVE_DISABLE_PROFILE_DESCRIPTION.replace("{}",userProfileDTO.getEmail()));
    return Optional.empty();
  }
  @MakerChecker(
          checkerPermission = "approve-enable-backoffice-profile",
          makerPermission = "enable-backoffice-profile",
          requestClassName = "com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO")
  public Object enableBackofficeProfile(Object request, Object... args) {
    UserProfileDTO userProfileDTO = (UserProfileDTO) request;
    backOfficeUserProfileServiceImpl.enableUserProfile(userProfileDTO.getEmail());
    auditLogProcessor.saveAuditWithDescription(AuditLogActivity.APPROVE_ENABLE_PROFILE,AuditLogActivity.BACKOFFICE,AuditLogActivity.APPROVE_ENABLE_PROFILE_DESCRIPTION.replace("{}",userProfileDTO.getEmail()));
    return Optional.empty();
  }

  @MakerChecker(
          checkerPermission = "approve-edit-backoffice-user-details",
          makerPermission = "edit-backoffice-user-details",
          requestClassName = "com.digicore.billent.data.lib.modules.common.authentication.dto.UserEditDTO")
  public Object updateBackofficeProfile(Object request, Object... args) {
    UserEditDTO userProfileDTO = (UserEditDTO) request;
    backOfficeUserProfileServiceImpl.editUserProfile(userProfileDTO);
    auditLogProcessor.saveAuditWithDescription(AuditLogActivity.APPROVE_EDIT_PROFILE,AuditLogActivity.BACKOFFICE,AuditLogActivity.APPROVE_EDIT_PROFILE_DESCRIPTION.replace("{}",userProfileDTO.getEmail()));
    return Optional.empty();
  }

  @Transactional
  public void changePassword(UpdatePasswordRequestDTO requestDTO) {
    UserAuthProfileDTO userAuthProfileDTO =
            backOfficeUserAuthProfileServiceImpl.retrieveAuthProfile(ClientUtil.getLoggedInUsername());

    passwordResetService.updateAccountPassword(requestDTO);
    notificationDispatcher.dispatchEmail(
            NotificationServiceRequest.builder()
                    .recipients(List.of(userAuthProfileDTO.getUsername()))
                    .notificationSubject(passwordUpdateSubject)
                    .firstName(userAuthProfileDTO.getUserProfile().getFirstName())
                    .notificationRequestType(NotificationRequestType.SEND_PASSWORD_UPDATE_EMAIL)
                    .build());
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.PASSWORD_UPDATE,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.PASSWORD_UPDATE_DESCRIPTION);
  }
}
