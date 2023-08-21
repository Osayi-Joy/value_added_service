package com.digicore.billent.backoffice.service.modules.profiles.service.impl;

import com.digicore.billent.backoffice.service.modules.profiles.service.BackOfficeUserProfileValidatorService;
import com.digicore.billent.data.lib.modules.backoffice.profile.model.BackOfficeUserProfile;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.profile.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Joy Osayi
 * @createdOn Aug-15(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserProfileProxyService {
  private final BackOfficeUserProfileValidatorService backOfficeUserProfileValidatorService;
  private final UserProfileService<UserProfileDTO, BackOfficeUserProfile>
      backOfficeUserProfileServiceImpl;

  public Object deleteBackofficeProfile(String email) {
    backOfficeUserProfileServiceImpl.profileExistenceCheckByEmail(email);
    UserProfileDTO userProfileDTO = new UserProfileDTO();
    userProfileDTO.setEmail(email);
    return backOfficeUserProfileValidatorService.deleteBackofficeProfile(userProfileDTO);
  }

  public Object updateBackofficeProfile(UserProfileDTO userProfileDTO) {
    backOfficeUserProfileServiceImpl.profileExistenceCheckByEmail(userProfileDTO.getEmail());
    return backOfficeUserProfileValidatorService.updateBackofficeProfile(userProfileDTO);
  }

  public Object disableBackofficeProfile(String email) {
    backOfficeUserProfileServiceImpl.profileExistenceCheckByEmail(email);
    UserProfileDTO userProfileDTO = new UserProfileDTO();
    userProfileDTO.setEmail(email);
    return backOfficeUserProfileValidatorService.disableBackofficeProfile(userProfileDTO);
  }

  public Object enableBackofficeProfile(String email) {
    backOfficeUserProfileServiceImpl.profileExistenceCheckByEmail(email);
    UserProfileDTO userProfileDTO = new UserProfileDTO();
    userProfileDTO.setEmail(email);
    return backOfficeUserProfileValidatorService.enableBackofficeProfile(userProfileDTO);
  }
}