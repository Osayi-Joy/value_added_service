package com.digicore.billent.backoffice.service.modules.authentication.services;

import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.model.BackOfficeUserAuthProfile;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.config.properties.PropertyConfig;
import com.digicore.notification.lib.request.NotificationRequestType;
import com.digicore.notification.lib.request.NotificationServiceRequest;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jun-27(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserAuthenticationService {
    private final LoginService<LoginResponse, LoginRequestDTO> userAuthService;
    private final NotificationDispatcher notificationDispatcher;
    private final PropertyConfig propertyConfig;

    public LoginResponse authenticateBackOfficeUser(LoginRequestDTO loginRequestDTO){
        LoginResponse loginResponse = userAuthService.authenticate(loginRequestDTO);
        notificationDispatcher.dispatchEmail(NotificationServiceRequest.builder()
                .recipients(List.of(loginResponse.getAdditionalInformation().get("email").toString()))
                .notificationSubject(propertyConfig.getSuccessLoginSubject())
                .firstName((String) loginResponse.getAdditionalInformation().get("firstName"))
                .notificationRequestType(NotificationRequestType.SEND_LOGIN_SUCCESS_EMAIL)
                .build());
        return loginResponse;
    }

}
