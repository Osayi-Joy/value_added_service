package com.digicore.billent.backoffice.service.modules.onboarding.services;

import com.digicore.registhentication.common.dto.request.ThirdBaseRequestDTO;
import com.digicore.registhentication.common.dto.response.ProfileDTO;
import com.digicore.registhentication.registration.services.RegistrationService;
import com.digicore.request.processor.annotations.MakerChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-04(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserOnboardingService {
    private final RegistrationService<ProfileDTO, ThirdBaseRequestDTO> registrationService;
    @MakerChecker(checkerPermission = "approve-invite-backoffice-user", makerPermission = "invite-backoffice-user",
            requestClassName = "com.digicore.registhentication.common.dto.request.ThirdBaseRequestDTO")
    public Object onboardNewBackOfficeUser(Object requestDTO, Object... args){
        return registrationService.createProfile((ThirdBaseRequestDTO) requestDTO);

    }
}
