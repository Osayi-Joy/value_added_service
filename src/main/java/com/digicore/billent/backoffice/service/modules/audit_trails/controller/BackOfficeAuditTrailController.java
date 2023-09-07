package com.digicore.billent.backoffice.service.modules.audit_trails.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.registhentication.authentication.dtos.request.ResetPasswordSecondBaseRequestDTO;
import com.digicore.request.processor.annotations.LogActivity;
import com.digicore.request.processor.annotations.TokenValid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUDIT_TRAIL_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.ONBOARDING_CONTROLLER_RESET_DEFAULT_PASSWORD_DESCRIPTION;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-07(Thu)-2023
 */
@RestController
@RequestMapping(AUDIT_TRAIL_API_V1)
@Tag(name = AUDIT_TRAIL_CONTROLLER_TITLE, description = AUDIT_TRAIL_CONTROLLER_DESCRIPTION)
@RequiredArgsConstructor
public class BackOfficeAuditTrailController {


    @TokenValid()
    @PostMapping("password-update")
    @Operation(
            summary = AUDIT_TRAIL_CONTROLLER_FETCH_SELF_TITLE,
            description = AUDIT_TRAIL_CONTROLLER_FETCH_SELF_DESCRIPTION)
    public ResponseEntity<Object> fetchSelfAuditTrail(
            @Valid @RequestBody ResetPasswordSecondBaseRequestDTO resetPasswordFirstBaseRequestDTO,
            Principal principal) {
        backOfficeUserOnboardingService.updateDefaultPassword(
                resetPasswordFirstBaseRequestDTO, principal);
        return ControllerResponse.buildSuccessResponse();
    }


    @TokenValid()
    @PostMapping("password-update")
    @Operation(
            summary = ONBOARDING_CONTROLLER_RESET_DEFAULT_PASSWORD_TITLE,
            description = ONBOARDING_CONTROLLER_RESET_DEFAULT_PASSWORD_DESCRIPTION)
    public ResponseEntity<Object> updateDefaultPassword(
            @Valid @RequestBody ResetPasswordSecondBaseRequestDTO resetPasswordFirstBaseRequestDTO,
            Principal principal) {
        backOfficeUserOnboardingService.updateDefaultPassword(
                resetPasswordFirstBaseRequestDTO, principal);
        return ControllerResponse.buildSuccessResponse();
    }
}
