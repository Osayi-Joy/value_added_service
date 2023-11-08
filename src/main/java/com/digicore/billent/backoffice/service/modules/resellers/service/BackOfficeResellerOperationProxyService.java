package com.digicore.billent.backoffice.service.modules.resellers.service;

import com.digicore.billent.data.lib.modules.common.authentication.dto.UserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.service.AuthProfileService;
import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.settings.service.SettingService;
import com.digicore.registhentication.exceptions.ExceptionHandler;
import com.digicore.registhentication.registration.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.digicore.billent.data.lib.modules.exception.messages.ResellerProfileErrorMessage.*;
import static com.digicore.billent.data.lib.modules.exception.messages.ResellerProfileErrorMessage.RESELLER_USER_ALREADY_INACTIVE_MESSAGE_CODE;
import static com.digicore.billent.data.lib.modules.exception.messages.ResellerUserProfileErrorMessage.RESELLER_USER_PROFILE_ALREADY_ACTIVE_CODE_KEY;
import static com.digicore.billent.data.lib.modules.exception.messages.ResellerUserProfileErrorMessage.RESELLER_USER_PROFILE_ALREADY_ACTIVE_MESSAGE_KEY;
import static com.digicore.billent.data.lib.modules.exception.messages.ResellerUserProfileErrorMessage.RESELLER_USER_PROFILE_ALREADY_INACTIVE_CODE_KEY;
import static com.digicore.billent.data.lib.modules.exception.messages.ResellerUserProfileErrorMessage.RESELLER_USER_PROFILE_ALREADY_INACTIVE_MESSAGE;
import static com.digicore.billent.data.lib.modules.exception.messages.ResellerUserProfileErrorMessage.RESELLER_USER_PROFILE_ALREADY_INACTIVE_MESSAGE_KEY;

@Service
@AllArgsConstructor
public class BackOfficeResellerOperationProxyService {
    private final BackOfficeResellerOperationValidatorService validatorService;
    private final AuthProfileService<UserAuthProfileDTO> resellerUserAuthProfileServiceImpl;

    private final ExceptionHandler<String, String, HttpStatus, String> exceptionHandler;
    private final SettingService settingService;

  public Object disableResellerUser(String email) {
    UserAuthProfileDTO userAuthProfileDTO =
        resellerUserAuthProfileServiceImpl.retrieveResellerUserAuthProfile(email);
    if (userAuthProfileDTO.getStatus().equals(Status.INACTIVE)) {
      throw exceptionHandler.processBadRequestException(
          settingService.retrieveValue(RESELLER_USER_PROFILE_ALREADY_INACTIVE_MESSAGE_KEY),
          settingService.retrieveValue(RESELLER_USER_PROFILE_ALREADY_INACTIVE_CODE_KEY));
    }
    return validatorService.disableResellerUser(userAuthProfileDTO.getUserProfile());
  }
    public Object enableResellerUser(String email) {
        UserAuthProfileDTO userAuthProfileDTO = resellerUserAuthProfileServiceImpl
                .retrieveResellerUserAuthProfile(email);
        if (userAuthProfileDTO.getStatus().equals(Status.ACTIVE)) {
            throw exceptionHandler.processBadRequestException(
                    settingService.retrieveValue(RESELLER_USER_PROFILE_ALREADY_ACTIVE_MESSAGE_KEY),
                    settingService.retrieveValue(RESELLER_USER_PROFILE_ALREADY_ACTIVE_CODE_KEY));
        }
        return validatorService.enableResellerUser(userAuthProfileDTO.getUserProfile());
    }
}
