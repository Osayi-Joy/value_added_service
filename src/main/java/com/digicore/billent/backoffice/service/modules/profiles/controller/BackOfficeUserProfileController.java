package com.digicore.billent.backoffice.service.modules.profiles.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PROFILE_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;


import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.profiles.service.BackOfficeUserProfileOperations;
import com.digicore.billent.backoffice.service.modules.profiles.service.BackOfficeUserProfileProxyService;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserEditDTO;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.authentication.dtos.request.UpdatePasswordRequestDTO;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.request.processor.annotations.LogActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-07(Mon)-2023
 */
@RestController
@RequestMapping(PROFILE_API_V1)
@Tag(name = PROFILE_CONTROLLER_TITLE, description = PROFILE_CONTROLLER_DESCRIPTION)
@Validated
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

  @GetMapping("get-{email}-details")
  @PreAuthorize("hasAuthority('view-backoffice-user-details')")
  @Operation(
          summary = PROFILE_CONTROLLER_GET_USER_TITLE,
          description = PROFILE_CONTROLLER_GET_USER_DESCRIPTION)
  public ResponseEntity<Object> getBackOfficeProfile(@PathVariable String email) {
    return ControllerResponse.buildSuccessResponse(
            backOfficeUserProfileOperations.fetchBackOfficeUserProfile(email),
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
          @RequestParam(value = VALUE) @NotBlank(message = "value should not be blank") String value) {
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
          @RequestParam(value = STATUS) @NotNull(message = "status can not be null") Status status,
          @RequestParam(value = START_DATE, required = false) String startDate,
          @RequestParam(value = END_DATE, required = false) String endDate) {
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

  @LogActivity(
          activity = AuditLogActivity.DELETE_PROFILE,
          auditType = AuditLogActivity.BACKOFFICE,
          auditDescription = AuditLogActivity.DELETE_PROFILE_DESCRIPTION)
  @DeleteMapping("remove-{email}")
  @PreAuthorize("hasAuthority('delete-backoffice-profile')")
  @Operation(
          summary = PROFILE_CONTROLLER_DELETE_USER_PROFILE_TITLE,
          description = PROFILE_CONTROLLER_DELETE_USER_PROFILE_DESCRIPTION)
  public ResponseEntity<Object> deleteBackofficeProfile(
          @PathVariable String email) {
    return ControllerResponse.buildSuccessResponse(backOfficeUserProfileProxyService.deleteBackofficeProfile(email),"User Profile deleted successfully");
  }

  @LogActivity(
          activity = AuditLogActivity.DISABLE_PROFILE,
          auditType = AuditLogActivity.BACKOFFICE,
          auditDescription = AuditLogActivity.DISABLE_PROFILE_DESCRIPTION)
  @PatchMapping("disable-{email}")
          @PreAuthorize("hasAuthority('disable-backoffice-profile')")
          @Operation(
          summary = PROFILE_CONTROLLER_DISABLE_USER_PROFILE_TITLE,
          description = PROFILE_CONTROLLER_DISABLE_USER_PROFILE_DESCRIPTION)
  public ResponseEntity<Object> disableBackofficeProfile(
          @PathVariable String email) {
    return ControllerResponse.buildSuccessResponse(backOfficeUserProfileProxyService.disableBackofficeProfile(email),"User Profile disabled successfully");
  }

  @LogActivity(
          activity = AuditLogActivity.ENABLE_PROFILE,
          auditType = AuditLogActivity.BACKOFFICE,
          auditDescription = AuditLogActivity.ENABLE_PROFILE_DESCRIPTION)
  @PatchMapping("enable-{email}")
  @PreAuthorize("hasAuthority('enable-backoffice-profile')")
  @Operation(
          summary = PROFILE_CONTROLLER_ENABLE_USER_PROFILE_TITLE,
          description = PROFILE_CONTROLLER_ENABLE_USER_PROFILE_DESCRIPTION)
  public ResponseEntity<Object> enableBackofficeProfile(
          @PathVariable String email) {
    return ControllerResponse.buildSuccessResponse(backOfficeUserProfileProxyService.enableBackofficeProfile(email),"User Profile enabled successfully");
  }

  @LogActivity(
          activity = AuditLogActivity.EDIT_PROFILE,
          auditType = AuditLogActivity.BACKOFFICE,
          auditDescription = AuditLogActivity.EDIT_PROFILE_DESCRIPTION)
  @PatchMapping("edit")
  @PreAuthorize("hasAuthority('edit-backoffice-user-details')")
  @Operation(
          summary = PROFILE_CONTROLLER_UPDATE_USER_PROFILE_TITLE,
          description = PROFILE_CONTROLLER_UPDATE_USER_PROFILE_DESCRIPTION)
  public ResponseEntity<Object> updateBackofficeProfile(
          @Valid @RequestBody UserEditDTO userProfileDTO) {
    return ControllerResponse.buildSuccessResponse(backOfficeUserProfileProxyService.updateBackofficeProfile(userProfileDTO),"User Profile updated successfully");
  }

  @PatchMapping("change-password")
  @Operation(
          summary = AUTHENTICATION_CONTROLLER_CHANGE_MY_PASSWORD_TITLE,
          description = AUTHENTICATION_CONTROLLER_CHANGE_MY_PASSWORD_DESCRIPTION)
  public ResponseEntity<Object> changePassword(
          @Valid @RequestBody UpdatePasswordRequestDTO requestDTO) {
    backOfficeUserProfileOperations.changePassword(requestDTO);
    return ControllerResponse.buildSuccessResponse("Password updated Successful");
  }

  @GetMapping("get-self")
  @PreAuthorize("hasAuthority('view-self-user-details')")
  @Operation(
          summary = PROFILE_CONTROLLER_FETCH_SELF_USER_DETAILS_TITLE,
          description = PROFILE_CONTROLLER_FETCH_SELF_USER_DETAILS_DESCRIPTION)
  public ResponseEntity<Object> viewProfileDetails() {
    return ControllerResponse.buildSuccessResponse(
            backOfficeUserProfileOperations.retrieveProfileDetails(),
            PROFILE_RETRIEVED_MESSAGE);
  }
  @GetMapping("export-to-csv")
  @PreAuthorize("hasAuthority('export-backoffice-users')")
  @Operation(
          summary = PROFILE_CONTROLLER_EXPORT_PROFILES_IN_CSV_TITLE,
          description = PROFILE_CONTROLLER_EXPORT_PROFILES_IN_CSV_DESCRIPTION)
  public void downloadListOfProfilesInCSV(
          @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
          @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
          @RequestParam(value = START_DATE, required = false) String startDate,
          @RequestParam(value = END_DATE, required = false) String endDate,
          @RequestParam(value = STATUS) Status status,
          @RequestParam(value = DOWNLOAD_FORMAT, defaultValue = DOWNLOAD_FORMAT_DEFAULT_VALUE, required = false) String downloadFormat,
          HttpServletResponse response) {
    backOfficeUserProfileOperations.downloadAllProfilesInCSV(response, status, startDate, endDate, downloadFormat, pageNumber, pageSize);
  }


}
