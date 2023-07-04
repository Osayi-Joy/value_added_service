package com.digicore.billent.backoffice.service.modules.authentication.controller;

import com.digicore.billent.backoffice.service.modules.authentication.services.BackOfficeUserAuthenticationService;
import com.digicore.billent.data.lib.modules.common.util.ControllerResponseUtil;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUTHENTICATION_API_V1;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-03(Mon)-2023
 */
@RestController
@RequestMapping(AUTHENTICATION_API_V1)
@Slf4j
@RequiredArgsConstructor
public class BackOfficeUserAuthenticationController {
    private final BackOfficeUserAuthenticationService authenticateBackOfficeUser;
    @PostMapping("login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ControllerResponseUtil.buildSuccessResponse(authenticateBackOfficeUser.authenticateBackOfficeUser(loginRequestDTO),"Authentication Successful");
    }
}
