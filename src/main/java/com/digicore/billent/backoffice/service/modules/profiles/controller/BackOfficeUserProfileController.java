package com.digicore.billent.backoffice.service.modules.profiles.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.PROFILE_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.PAGE_SIZE_DEFAULT_VALUE;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.profiles.service.BackOfficeUserProfileOperations;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.registration.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
          @RequestParam(value = "key") String key,
          @RequestParam(value = "value") String value) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setKey(key);
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
}
