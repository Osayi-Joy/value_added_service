package com.digicore.billent.backoffice.service.modules.profiles.service.impl;

import com.digicore.billent.backoffice.service.modules.profiles.service.BackOfficeUserProfileValidatorService;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.profile.UserProfileService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.annotations.MakerChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */

@Service
@RequiredArgsConstructor
public class BackOfficeUserProfileOperations implements BackOfficeUserProfileValidatorService {
  private final UserProfileService<UserProfileDTO> backOfficeUserProfileServiceImpl;

  public PaginatedResponseDTO<UserProfileDTO> fetchAllBackOfficeUserProfiles(int page, int size) {
    return backOfficeUserProfileServiceImpl.retrieveAllUserProfiles(page, size);
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
    return backOfficeUserProfileServiceImpl.deleteUserProfile(userProfileDTO.getEmail());
  }
}
