package com.digicore.billent.backoffice.service.modules.onboarding.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.onboarding.services.BackOfficeUserOnboardingService;
import com.digicore.registhentication.common.dto.request.ThirdBaseRequestDTO;
import com.digicore.request.processor.annotations.TokenValid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.*;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-04(Tue)-2023
 */
@RestController
@RequestMapping(ONBOARDING_API_V1)
@Slf4j
@RequiredArgsConstructor
public class BackOfficeUserOnboardingController {
    private final BackOfficeUserOnboardingService backOfficeUserOnboardingService;
    @TokenValid()
    @PostMapping("invite-user")
    public ResponseEntity<Object> inviteUser(@Valid @RequestBody ThirdBaseRequestDTO backOfficeUserDTO)  {
    return ControllerResponse.buildSuccessResponse(
        backOfficeUserOnboardingService.onboardNewBackOfficeUser(backOfficeUserDTO),
        "invitation sent to ".concat(backOfficeUserDTO.getEmail()).concat(" successfully"));
    }
}
