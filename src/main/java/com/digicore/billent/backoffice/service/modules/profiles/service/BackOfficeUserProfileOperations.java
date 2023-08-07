package com.digicore.billent.backoffice.service.modules.profiles.service;

import com.digicore.billent.data.lib.modules.backoffice.profile.service.BackOfficeUserProfileService;
import com.digicore.billent.data.lib.modules.common.authentication.dtos.UserProfileDTO;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */

@Service
@RequiredArgsConstructor
public class BackOfficeUserProfileOperations {
    private final BackOfficeUserProfileService<UserProfileDTO> backOfficeUserProfileService;

    public PaginatedResponseDTO<UserProfileDTO> fetchAllBackOfficeUserProfiles(int page,int size){
        return backOfficeUserProfileService.retrieveAllBackOfficeUserProfiles(page,size);
    }
}
