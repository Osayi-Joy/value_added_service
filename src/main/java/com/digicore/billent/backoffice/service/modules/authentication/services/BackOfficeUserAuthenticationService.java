package com.digicore.billent.backoffice.service.modules.authentication.services;

import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.BackOfficeUserAuthProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.authentication.model.BackOfficeUserAuthProfile;
import com.digicore.billent.data.lib.modules.backoffice.authentication.service.BackOfficeUserAuthService;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.response.LoginResponse;
import com.digicore.registhentication.authentication.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jun-27(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserAuthenticationService {
    private final LoginService<LoginResponse, LoginRequestDTO> userAuthService;
    private final BackOfficeUserAuthService<BackOfficeUserAuthProfile> authService;

    public LoginResponse authenticateBackOfficeUser(LoginRequestDTO loginRequestDTO){
       return userAuthService.authenticate(loginRequestDTO);
    }

}
