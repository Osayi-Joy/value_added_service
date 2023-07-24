package com.digicore.billent.backoffice.service.modules.authentication.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUTHENTICATION_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.authentication.services.BackOfficeUserAuthenticationService;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-03(Mon)-2023
 */
@RestController
@RequestMapping(AUTHENTICATION_API_V1)
@Tag(name = AUTHENTICATION_CONTROLLER_TITLE, description = AUTHENTICATION_CONTROLLER_DESCRIPTION)
@RequiredArgsConstructor
public class BackOfficeUserAuthenticationController {
  private final BackOfficeUserAuthenticationService authenticateBackOfficeUser;

  @PostMapping("login")
  @Operation(
      summary = AUTHENTICATION_CONTROLLER_LOGIN_TITLE,
      description = AUTHENTICATION_CONTROLLER_LOGIN_DESCRIPTION)
  public ResponseEntity<Object> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
    return ControllerResponse.buildSuccessResponse(
        authenticateBackOfficeUser.authenticateBackOfficeUser(loginRequestDTO),
        "Authentication Successful");
  }
}
