package com.digicore.billent.backoffice.service.modules.profiles.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PROFILE_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;


import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.profiles.service.impl.BackOfficeUserProfileOperations;
import com.digicore.billent.backoffice.service.modules.profiles.service.impl.BackOfficeUserProfileProxyService;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.registration.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */
@RestController
@RequestMapping(PROFILE_API_V1)
@Tag(name = PROFILE_CONTROLLER_TITLE, description = PROFILE_CONTROLLER_DESCRIPTION)
@RequiredArgsConstructor
public class BackOfficeUserProfileController {
  private static final String PROFILE_RETRIEVED_MESSAGE = "Back Office profiles  retrieved successfully";
  private final BackOfficeUserProfileOperations backOfficeUserProfileOperations;
  private final BackOfficeUserProfileProxyService backOfficeUserProfileProxyService;

  @GetMapping("get-all")
  @PreAuthorize("hasAuthority('view-backoffice-users')")
  @Operation(
          summary = PROFILE_CONTROLLER_GET_ALL_USERS_TITLE,
          description = PROFILE_CONTROLLER_GET_ALL_USERS_DESCRIPTION)
  public ResponseEntity<Object> getAllBackOfficeProfiles(
          @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
          @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize) {
    return ControllerResponse.buildSuccessResponse(
            backOfficeUserProfileOperations.fetchAllBackOfficeUserProfiles(pageNumber, pageSize),
            PROFILE_RETRIEVED_MESSAGE);
  }

  @GetMapping("search")
  @PreAuthorize("hasAuthority('view-backoffice-users')")
  @Operation(
          summary = PROFILE_CONTROLLER_SEARCH_USERS_TITLE,
          description = PROFILE_CONTROLLER_SEARCH_USERS_DESCRIPTION)
  public ResponseEntity<Object> search(
          @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
          @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
         // @RequestParam(value = KEY) String key,
          @RequestParam(value = VALUE) String value) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setKey("");
    billentSearchRequest.setValue(value);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);

    return ControllerResponse.buildSuccessResponse(
            backOfficeUserProfileOperations.filterOrSearch(billentSearchRequest),
            PROFILE_RETRIEVED_MESSAGE);
  }

  @GetMapping("filter")
  @PreAuthorize("hasAuthority('view-backoffice-users')")
  @Operation(
          summary = PROFILE_CONTROLLER_FILTER_USERS_TITLE,
          description = PROFILE_CONTROLLER_FILTER_USERS_DESCRIPTION)
  public ResponseEntity<Object> filter(
          @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
          @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
          @RequestParam(value = STATUS) Status status,
          @RequestParam(value = START_DATE) String startDate,
          @RequestParam(value = END_DATE) String endDate) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setStatus(status);
    billentSearchRequest.setStartDate(startDate);
    billentSearchRequest.setEndDate(endDate);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    billentSearchRequest.setForFilter(true);

    return ControllerResponse.buildSuccessResponse(
            backOfficeUserProfileOperations.filterOrSearch(billentSearchRequest),
            PROFILE_RETRIEVED_MESSAGE);
  }

  @DeleteMapping("remove-{email}")
  @PreAuthorize("hasAuthority('delete-backoffice-profile')")
  @Operation(
          summary = PROFILE_CONTROLLER_DELETE_USER_PROFILE_TITLE,
          description = PROFILE_CONTROLLER_DELETE_USER_PROFILE_DESCRIPTION)
  public ResponseEntity<Object> deleteBackofficeProfile(
          @PathVariable String email) {
    return ControllerResponse.buildSuccessResponse(backOfficeUserProfileProxyService.deleteBackofficeProfile(email),"User Profile deleted successfully");
  }

  @PatchMapping("edit")
  @PreAuthorize("hasAuthority('edit-backoffice-user-details')")
  @Operation(
          summary = PROFILE_CONTROLLER_UPDATE_USER_PROFILE_TITLE,
          description = PROFILE_CONTROLLER_UPDATE_USER_PROFILE_DESCRIPTION)
  public ResponseEntity<Object> updateBackofficeProfile(
          @Valid @RequestBody UserProfileDTO userProfileDTO) {
    return ControllerResponse.buildSuccessResponse(backOfficeUserProfileProxyService.updateBackofficeProfile(userProfileDTO),"User Profile updated successfully");
  }
}
