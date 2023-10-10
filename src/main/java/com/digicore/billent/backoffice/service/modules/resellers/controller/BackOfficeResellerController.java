package com.digicore.billent.backoffice.service.modules.resellers.controller;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-03(Sun)-2023
 */

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.RESELLERS_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.resellers.service.BackOfficeResellerOperation;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.registration.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(RESELLERS_API_V1)
@Tag(name = RESELLER_CONTROLLER_TITLE, description = RESELLER_CONTROLLER_DESCRIPTION)
public class BackOfficeResellerController {

  private final BackOfficeResellerOperation backOfficeResellerOperation;

  @GetMapping("get-all")
  @PreAuthorize("hasAuthority('view-resellers')")
  @Operation(
      summary = RESELLER_CONTROLLER_GET_ALL_RESELLER_TITLE,
      description = RESELLER_CONTROLLER_GET_ALL_RESELLER_DESCRIPTION)
  public ResponseEntity<Object> viewAllResellers(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize) {
    return ControllerResponse.buildSuccessResponse(
        backOfficeResellerOperation.getAllResellers(pageNumber, pageSize),
        "Retrieved all resellers successfully");
  }

  @GetMapping("get-all-users")
  @PreAuthorize("hasAuthority('view-reseller-user-details')")
  @Operation(
          summary = RESELLER_CONTROLLER_GET_ALL_RESELLER_TITLE,
          description = RESELLER_CONTROLLER_GET_ALL_RESELLER_DESCRIPTION)
  public ResponseEntity<Object> viewAllResellerUsers(
          @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
          @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
          @RequestParam(value = "resellerId") String resellerId) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setKey(resellerId);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    return ControllerResponse.buildSuccessResponse(
            backOfficeResellerOperation.getAllResellerUsers(billentSearchRequest),
            "Retrieved all reseller users successfully");
  }

  @GetMapping("get-{resellerId}-details")
  @PreAuthorize("hasAuthority('view-reseller-user-details')")
  @Operation(
      summary = RESELLER_PROFILE_CONTROLLER_GET_RESELLER_PROFILE_DETAIL_TITLE,
      description = RESELLER_PROFILE_CONTROLLER_GET_RESELLER_PROFILE_DETAIL_DESCRIPTION)
  public ResponseEntity<Object> getResellerProfile(@PathVariable String resellerId) {
    return ControllerResponse.buildSuccessResponse(
        backOfficeResellerOperation.fetchResellerProfile(resellerId),
        "Retrieved reseller profile successfully");
  }

  @GetMapping("get-{resellerId}-wallet-balance")
  @PreAuthorize("hasAuthority('view-reseller-wallet-balance')")
  @Operation(
      summary = RESELLER_PROFILE_CONTROLLER_GET_RESELLER_WALLET_BALANCE_TITLE,
      description = RESELLER_PROFILE_CONTROLLER_GET_RESELLER_WALLET_BALANCE_DESCRIPTION)
  public ResponseEntity<Object> getResellerWalletBalance(@PathVariable String resellerId) {
    return ControllerResponse.buildSuccessResponse(
        backOfficeResellerOperation.fetchResellerWalletBalance(resellerId),
        "Retrieved reseller wallet balance successfully");
  }

  @GetMapping("filter")
  @PreAuthorize("hasAuthority('view-resellers')")
  @Operation(
      summary = RESELLER_CONTROLLER_FETCH_RESELLER_BY_STATUS_TITLE,
      description = RESELLER_CONTROLLER_FETCH_RESELLER_BY_STATUS_DESCRIPTION)
  public ResponseEntity<Object> filterResellersByStatus(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = START_DATE, required = false) String startDate,
      @RequestParam(value = END_DATE, required = false) String endDate,
      @RequestParam(value = RESELLER_STATUS, required = false) Status resellerStatus) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setStartDate(startDate);
    billentSearchRequest.setEndDate(endDate);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    billentSearchRequest.setStatus(resellerStatus);
    billentSearchRequest.setDownloadFormat("CSV");
    return ControllerResponse.buildSuccessResponse(
        backOfficeResellerOperation.fetchResellersByStatusOrDateCreated(billentSearchRequest),
        "Retrieved all billers by status successfully");
  }

  @GetMapping("search")
  @PreAuthorize("hasAuthority('view-resellers')")
  @Operation(
      summary = RESELLER_CONTROLLER_FETCH_RESELLER_BY_SEARCH_TITLE,
      description = RESELLER_CONTROLLER_FETCH_RESELLER_BY_SEARCH_DESCRIPTION)
  public ResponseEntity<Object> searchReseller(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = VALUE) String value) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setValue(value);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    return ControllerResponse.buildSuccessResponse(
        backOfficeResellerOperation.searchReseller(billentSearchRequest),
        "Retrieved all resellers by status successfully");
  }

  @GetMapping("export-to-csv")
  @PreAuthorize("hasAuthority('export-resellers')")
  @Operation(
      summary = RESELLER_CONTROLLER_EXPORT_RESELLER_IN_CSV_TITLE,
      description = RESELLER_CONTROLLER_EXPORT_RESELLER_IN_CSV_DESCRIPTION)
  public void downloadResellersInCSV(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = START_DATE, required = false) String startDate,
      @RequestParam(value = END_DATE, required = false) String endDate,
      @RequestParam(value = RESELLER_STATUS, required = false) Status resellerStatus,
      HttpServletResponse response) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setStartDate(startDate);
    billentSearchRequest.setEndDate(endDate);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    billentSearchRequest.setStatus(resellerStatus);
    billentSearchRequest.setDownloadFormat("CSV");
    backOfficeResellerOperation.downloadAllResellersInCSV(response, billentSearchRequest);
  }

  @GetMapping("filter-users")
  @PreAuthorize("hasAuthority('view-reseller-user-details')")
  @Operation(
      summary = RESELLER_CONTROLLER_FETCH_RESELLER_USER_BY_STATUS_TITLE,
      description = RESELLER_CONTROLLER_FETCH_RESELLER_USER_BY_STATUS_DESCRIPTION)
  public ResponseEntity<Object> filterResellerUserByStatus(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = START_DATE, required = false) String startDate,
      @RequestParam(value = END_DATE, required = false) String endDate,
      @RequestParam(value = "resellerId") String resellerId,
      @RequestParam(value = RESELLER_STATUS, required = false) Status resellerStatus) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setStartDate(startDate);
    billentSearchRequest.setEndDate(endDate);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    billentSearchRequest.setStatus(resellerStatus);
    billentSearchRequest.setKey(resellerId);
    billentSearchRequest.setDownloadFormat("CSV");
    return ControllerResponse.buildSuccessResponse(
        backOfficeResellerOperation.fetchResellersDetailByStatusOrDateCreated(billentSearchRequest),
        "Retrieved all reseller users by status successfully");
  }

  @GetMapping("search-users")
  @PreAuthorize("hasAuthority('view-reseller-user-details')")
  @Operation(
      summary = RESELLER_CONTROLLER_FETCH_RESELLER_USER_BY_SEARCH_TITLE,
      description = RESELLER_CONTROLLER_FETCH_RESELLER_USER_BY_SEARCH_DESCRIPTION)
  public ResponseEntity<Object> searchResellerUsers(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = VALUE) String value,
      @RequestParam(value = "resellerId") String resellerId) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setValue(value);
    billentSearchRequest.setKey(resellerId);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    return ControllerResponse.buildSuccessResponse(
        backOfficeResellerOperation.searchResellerDetail(billentSearchRequest),
        "Retrieved all resellers by status successfully");
  }

  @GetMapping("export-to-csv-users")
  @PreAuthorize("hasAuthority('export-resellers')")
  @Operation(
      summary = RESELLER_CONTROLLER_EXPORT_RESELLER_USER_IN_CSV_TITLE,
      description = RESELLER_CONTROLLER_EXPORT_RESELLER_USER_IN_CSV_DESCRIPTION)
  public void downloadResellerUsersInCSV(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = START_DATE, required = false) String startDate,
      @RequestParam(value = END_DATE, required = false) String endDate,
      @RequestParam(value = "resellerId", required = false) String resellerId,
      @RequestParam(value = RESELLER_STATUS, required = false) Status resellerStatus,
      HttpServletResponse response) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setStartDate(startDate);
    billentSearchRequest.setKey(resellerId);
    billentSearchRequest.setEndDate(endDate);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    billentSearchRequest.setStatus(resellerStatus);
    billentSearchRequest.setDownloadFormat("CSV");
    backOfficeResellerOperation.downloadAllResellerUserInCSV(response, billentSearchRequest);
  }
}
