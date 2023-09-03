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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(RESELLERS_API_V1)
@Tag(name = RESELLER_CONTROLLER_TITLE, description = RESELLER_CONTROLLER_DESCRIPTION)
public class BackOfficeResellerController {

  private final BackOfficeResellerOperation backOfficeResellerOperation;

  @GetMapping("get-all")
  // @PreAuthorize("hasAuthority('view-resellers')")
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

  @GetMapping("filter")
  // @PreAuthorize("hasAuthority('view-resellers')")
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

  @GetMapping("export-to-csv")
  // @PreAuthorize("hasAuthority('export-resellers')")
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
}
