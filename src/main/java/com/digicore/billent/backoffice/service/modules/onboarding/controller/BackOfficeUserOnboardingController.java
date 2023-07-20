package com.digicore.billent.backoffice.service.modules.onboarding.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.*;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.onboarding.services.BackOfficeUserOnboardingProxyService;
import com.digicore.billent.backoffice.service.modules.onboarding.services.BackOfficeUserOnboardingService;
import com.digicore.billent.data.lib.modules.backoffice.authentication.dto.InviteBodyDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
import com.digicore.request.processor.annotations.TokenValid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-04(Tue)-2023
 */
@RestController
@RequestMapping(ONBOARDING_API_V1)
@Tag(name = ONBOARDING_CONTROLLER_TITLE, description = ONBOARDING_CONTROLLER_DESCRIPTION)
@RequiredArgsConstructor
public class BackOfficeUserOnboardingController {
  private final BackOfficeUserOnboardingService backOfficeUserOnboardingService;
  private final BackOfficeUserOnboardingProxyService onboardingProxyService;

  @TokenValid()
  @PostMapping("user-invitation")
  @PreAuthorize("hasAuthority('invite-backoffice-user')")
  @Operation(
      summary = ONBOARDING_CONTROLLER_INVITE_USER_TITLE,
      description = ONBOARDING_CONTROLLER_INVITE_USER_DESCRIPTION)
  public ResponseEntity<Object> inviteUser(
      @Valid @RequestBody UserRegistrationDTO backOfficeUserDTO) {
    return ControllerResponse.buildSuccessResponse(
            onboardingProxyService.onboardNewBackOfficeUser(backOfficeUserDTO),
        "invitation sent to ".concat(backOfficeUserDTO.getEmail()).concat(" successfully"));
  }

  @TokenValid()
  @PostMapping("resending-of-user-invitation")
  @PreAuthorize("hasAuthority('resend-invite-email')")
  @Operation(
      summary = ONBOARDING_CONTROLLER_RE_INVITE_USER_TITLE,
      description = ONBOARDING_CONTROLLER_RE_INVITE_USER_DESCRIPTION)
  public ResponseEntity<Object> resendInvitation(@Valid @RequestBody InviteBodyDTO inviteBodyDTO) {
    backOfficeUserOnboardingService.resendInvitation(inviteBodyDTO);
    return ControllerResponse.buildSuccessResponse();
  }

  @TokenValid()
  @PostMapping("password-update")
  @Operation(
          summary = ONBOARDING_CONTROLLER_RE_INVITE_USER_TITLE,
          description = ONBOARDING_CONTROLLER_RE_INVITE_USER_DESCRIPTION)
  public ResponseEntity<Object> updateDefaultPassword(@Valid @RequestBody InviteBodyDTO inviteBodyDTO) {
    backOfficeUserOnboardingService.resendInvitation(inviteBodyDTO);
    return ControllerResponse.buildSuccessResponse();
  }
}
