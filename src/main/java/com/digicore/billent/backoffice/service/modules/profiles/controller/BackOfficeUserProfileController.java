package com.digicore.billent.backoffice.service.modules.profiles.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.profiles.service.BackOfficeUserProfileOperations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PROFILE_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.PAGE_SIZE_DEFAULT_VALUE;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */
@RestController
@RequestMapping(PROFILE_API_V1)
@Tag(name = PROFILE_CONTROLLER_TITLE, description = PROFILE_CONTROLLER_DESCRIPTION)
@RequiredArgsConstructor
public class BackOfficeUserProfileController {
    private final BackOfficeUserProfileOperations backOfficeUserProfileOperations;

    @GetMapping("get-all")
    @PreAuthorize("hasAuthority('view-backoffice-users')")
    @Operation(
            summary = PROFILE_CONTROLLER_GET_ALL_USERS_TITLE,
            description = PROFILE_CONTROLLER_GET_ALL_USERS_DESCRIPTION)
    public ResponseEntity<Object> getAllBackOfficeProfiles(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
            int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
            int pageSize)
    {
        return ControllerResponse.buildSuccessResponse(
                backOfficeUserProfileOperations.fetchAllBackOfficeUserProfiles(pageNumber, pageSize), "Back Office profiles  retrieved successfully");
    }
}
