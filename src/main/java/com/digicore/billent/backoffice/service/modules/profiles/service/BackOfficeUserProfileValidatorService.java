package com.digicore.billent.backoffice.service.modules.profiles.service;
/**
 * @author Joy Osayi
 * @createdOn Aug-15(Tue)-2023
 */
public interface BackOfficeUserProfileValidatorService {
  Object deleteBackofficeProfile(Object request, Object... args);

  Object disableBackofficeProfile(Object request, Object... args);

  Object enableBackofficeProfile(Object request, Object... args);
}
