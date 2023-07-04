package com.digicore.billent.backoffice.service.modules.authentication.services;

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

    public LoginResponse authenticateBackOfficeUser(LoginRequestDTO loginRequestDTO){
       return userAuthService.authenticate(loginRequestDTO);
    }
}
