package com.digicore.billent.backoffice.service.modules.authentication.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUTHENTICATION_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.authentication.services.BackOfficeUserAuthenticationService;
import com.digicore.registhentication.authentication.dtos.request.LoginRequestDTO;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordFirstBaseRequestDTO;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordSecondBaseRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  @PostMapping("password-forgotten")
  @Operation(
          summary = AUTHENTICATION_CONTROLLER_REQUEST_PASSWORD_RESET_TITLE,
          description = AUTHENTICATION_CONTROLLER_REQUEST_PASSWORD_RESET_DESCRIPTION)
  public ResponseEntity<Object> requestPasswordRequest(@RequestParam String email) {
    authenticateBackOfficeUser.requestPasswordReset(email);
    return ControllerResponse.buildSuccessResponse("Password ResetRequest Successful");
  }
  @PostMapping("verify-email-otp")
  @Operation(
          summary = AUTHENTICATION_CONTROLLER_VERIFY_EMAIL_OTP_TITLE,
          description = AUTHENTICATION_CONTROLLER_VERIFY_EMAIL_OTP_DESCRIPTION)
  public ResponseEntity<Object> validateEmailVerification(@Valid @RequestBody ResetPasswordFirstBaseRequestDTO resetPasswordDto) {
    authenticateBackOfficeUser.validateEmailVerificationAndSendSmsOtp(resetPasswordDto);
    return ControllerResponse.buildSuccessResponse("Email Verification Successful");
  }

  @PostMapping("verify-sms-otp")
  @Operation(
          summary = AUTHENTICATION_CONTROLLER_VERIFY_SMS_OTP_TITLE,
          description = AUTHENTICATION_CONTROLLER_VERIFY_SMS_OTP_DESCRIPTION)
  public ResponseEntity<Object> validateSmsVerification(@Valid @RequestBody ResetPasswordFirstBaseRequestDTO resetPasswordDto) {
    return ControllerResponse.buildSuccessResponse(authenticateBackOfficeUser.validateSmsVerification(resetPasswordDto),"Phone Verification Successful");
  }
  @PostMapping("reset-password")
  @Operation(
          summary = AUTHENTICATION_CONTROLLER_UPDATE_PASSWORD_TITLE,
          description = AUTHENTICATION_CONTROLLER_UPDATE_PASSWORD_DESCRIPTION)
  public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordSecondBaseRequestDTO resetPasswordDto) {
    authenticateBackOfficeUser.resetPassword(resetPasswordDto);
    return ControllerResponse.buildSuccessResponse("Password Reset Successful");
  }

}
