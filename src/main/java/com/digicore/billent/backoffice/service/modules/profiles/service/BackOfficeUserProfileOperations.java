package com.digicore.billent.backoffice.service.modules.profiles.service;

import com.digicore.billent.backoffice.service.modules.profiles.service.BackOfficeUserProfileValidatorService;
import com.digicore.billent.data.lib.modules.backoffice.profile.model.BackOfficeUserProfile;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserEditDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.profile.UserProfileService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.AuditLogProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}