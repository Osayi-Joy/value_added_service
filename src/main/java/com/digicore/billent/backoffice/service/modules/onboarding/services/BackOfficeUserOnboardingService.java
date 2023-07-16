package com.digicore.billent.backoffice.service.modules.onboarding.services;

import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO;
import com.digicore.notification.lib.request.NotificationRequestType;
import com.digicore.notification.lib.request.NotificationServiceRequest;
import com.digicore.otp.service.NotificationDispatcher;
import com.digicore.registhentication.common.dto.response.ProfileDTO;
import com.digicore.registhentication.registration.services.RegistrationService;
import com.digicore.registhentication.util.IDGeneratorUtil;
import com.digicore.request.processor.annotations.MakerChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-04(Tue)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeUserOnboardingService {
    private final RegistrationService<UserProfileDTO, UserRegistrationDTO> registrationService;
    private final NotificationDispatcher notificationDispatcher;
    @MakerChecker(checkerPermission = "approve-invite-backoffice-user", makerPermission = "invite-backoffice-user",
            requestClassName = "com.digicore.billent.data.lib.modules.common.authentication.dtos.UserRegistrationDTO")
    public Object onboardNewBackOfficeUser(Object requestDTO, Object... args){
        UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) requestDTO;
        userRegistrationDTO.setPassword(IDGeneratorUtil.generateTempId());
        UserProfileDTO result = registrationService.createProfile(userRegistrationDTO);
        notificationDispatcher.dispatchEmail(
                NotificationServiceRequest.builder()
                        .notificationSubject("Invitation to the backoffice Biller Platform")
                        .recipients(List.of(result.getEmail()))
                        .dateTime(LocalDateTime.now())
                        .userCode(result.getPassword())
                        .firstName(result.getFirstName())
                        .notificationRequestType(NotificationRequestType.SEND_INVITE_FOR_BACKOFFICE_EMAIL)
                        .build());

        return result;
    }
}
