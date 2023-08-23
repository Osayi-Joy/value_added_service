package com.digicore.billent.backoffice.service.modules.authentication.services;

import com.digicore.billent.data.lib.modules.common.settings.service.SettingService;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.notification.lib.request.NotificationRequestType;
import com.digicore.notification.lib.request.NotificationServiceRequest;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.services.LoginService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.digicore.billent.data.lib.modules.common.notification.NotificationSubject.LOGIN_SUCCESSFUL_SUBJECT_KEY;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jun-27(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserAuthenticationService {
  private final LoginService<LoginResponse, LoginRequestDTO> userAuthService;
  private final NotificationDispatcher notificationDispatcher;
  private final SettingService settingService;

  public LoginResponse authenticateBackOfficeUser(LoginRequestDTO loginRequestDTO) {
    LoginResponse loginResponse = userAuthService.authenticate(loginRequestDTO);
    notificationDispatcher.dispatchEmail(
        NotificationServiceRequest.builder()
            .recipients(List.of(loginResponse.getAdditionalInformation().get("email").toString()))
            .notificationSubject(settingService.retrieveValue(LOGIN_SUCCESSFUL_SUBJECT_KEY))
            .firstName((String) loginResponse.getAdditionalInformation().get("firstName"))
            .notificationRequestType(NotificationRequestType.SEND_LOGIN_SUCCESS_EMAIL)
            .build());
    return loginResponse;
  }
}
