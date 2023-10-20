package com.digicore.billent.backoffice.service.modules.wallets.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.WALLET_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.WALLET_STATUS;
import static com.digicore.registhentication.util.PageableUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.wallets.service.BackOfficeWalletService;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.billent.data.lib.modules.common.wallet.dto.TopUpWalletDTO;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.registhentication.util.PageableUtil;
import com.digicore.request.processor.annotations.LogActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/*
 * @author Ademiju Taiwo
 * @createdOn Sept-08 Fri)-2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(WALLET_API_V1)
@Tag(name = WALLET_CONTROLLER_TITLE, description = WALLET_CONTROLLER_DESCRIPTION)
public class BackOfficeWalletController {

  private final BackOfficeWalletService backOfficeWalletService;

  @GetMapping("retrieve-{systemWalletId}-balance")
  @PreAuthorize("hasAuthority('view-all-wallet-balances')")
  @Operation(
      summary = WALLET_CONTROLLER_GET_A_WALLET_BALANCE_TITLE,
      description = WALLET_CONTROLLER_GET_A_WALLET_BALANCE_DESCRIPTION)
  public ResponseEntity<Object> viewWalletBalance(@PathVariable String systemWalletId) {
    return ControllerResponse.buildSuccessResponse(
        backOfficeWalletService.fetchWalletBalance(systemWalletId),
        "Retrieved wallet balance successfully");
  }

  @GetMapping("get-all")
  @PreAuthorize("hasAuthority('view-all-wallets')")
  @Operation(
      summary = WALLET_CONTROLLER_GET_ALL_WALLET_TITLE,
      description = WALLET_CONTROLLER_GET_ALL_WALLET_DESCRIPTION)
  public ResponseEntity<Object> viewAllWallets(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(
              value = PAGE_SIZE,
              defaultValue = PageableUtil.PAGE_SIZE_DEFAULT_VALUE,
              required = false)
          int pageSize) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);

    return ControllerResponse.buildSuccessResponse(
        backOfficeWalletService.fetchAllWallet(billentSearchRequest),
        "Retrieved all wallets Successfully");
  }

  @LogActivity(
      activity = AuditLogActivity.CREDIT_WALLET_POSITION,
      auditType = AuditLogActivity.BACKOFFICE,
      auditDescription = AuditLogActivity.CREDIT_WALLET_POSITION_DESCRIPTION)
  @PostMapping("credit-position")
  @PreAuthorize("hasAuthority('credit-wallet')")
  @Operation(
      summary = WALLET_CONTROLLER_CREDIT_TITLE,
      description = WALLET_CONTROLLER_CREDIT_DESCRIPTION)
  public ResponseEntity<Object> creditCustomerWalletPosition(
      @RequestBody TopUpWalletDTO topUpWalletDTO) {
    backOfficeWalletService.creditWallet(topUpWalletDTO);
    return ControllerResponse.buildSuccessResponse();
  }

  @GetMapping("search-wallets")
  @PreAuthorize("hasAuthority('view-all-wallets')")
  @Operation(
      summary = WALLET_CONTROLLER_FETCH_WALLET_BY_SEARCH_TITLE,
      description = WALLET_CONTROLLER_FETCH_WALLET_BY_SEARCH_DESCRIPTION)
  public ResponseEntity<Object> searchWallets(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = VALUE) String value) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    billentSearchRequest.setValue(value);
    return ControllerResponse.buildSuccessResponse(
        backOfficeWalletService.searchWallets(billentSearchRequest),
        "Retrieved all wallets by search keyword successfully");
  }

  @GetMapping("filter-wallets")
  @PreAuthorize("hasAuthority('view-all-wallets')")
  @Operation(
      summary = WALLET_CONTROLLER_FETCH_WALLET_BY_STATUS_TITLE,
      description = WALLET_CONTROLLER_FETCH_WALLET_BY_STATUS_DESCRIPTION)
  public ResponseEntity<Object> filterWallets(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = START_DATE) String startDate,
      @RequestParam(value = END_DATE) String endDate,
      @RequestParam(value = WALLET_STATUS) Status walletStatus) {

    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    billentSearchRequest.setStartDate(startDate);
    billentSearchRequest.setEndDate(endDate);
    billentSearchRequest.setStatus(walletStatus);
    return ControllerResponse.buildSuccessResponse(
        backOfficeWalletService.filterWallets(billentSearchRequest),
        "Retrieved all wallets by status successfully");
  }

  @GetMapping("retrieve-transactions-{systemWalletId}")
  @PreAuthorize("hasAuthority('view-all-wallet-transactions')")
  @Operation(
      summary = WALLET_CONTROLLER_FETCH_TRANSACTION_TITLE,
      description = WALLET_CONTROLLER_FETCH_TRANSACTION_DESCRIPTION)
  public ResponseEntity<Object> fetchWalletTransactions(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(
              value = PAGE_SIZE,
              defaultValue = PageableUtil.PAGE_SIZE_DEFAULT_VALUE,
              required = false)
          int pageSize,
      @PathVariable String systemWalletId) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    return ControllerResponse.buildSuccessResponse(
        backOfficeWalletService.fetchWalletTransactions(billentSearchRequest, systemWalletId),
        "Retrieved all wallets by status successfully");
  }

  @GetMapping("export-to-csv")
  @PreAuthorize("hasAuthority('export-wallets')")
  @Operation(
      summary = WALLET_CONTROLLER_EXPORT_WALLET_IN_CSV_TITLE,
      description = WALLET_CONTROLLER_EXPORT_WALLET_IN_CSV_DESCRIPTION)
  public void downloadWalletsInCSV(
      @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
          int pageNumber,
      @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
          int pageSize,
      @RequestParam(value = START_DATE) String startDate,
      @RequestParam(value = END_DATE) String endDate,
      @RequestParam(value = WALLET_STATUS) Status walletStatus,
      HttpServletResponse response) {
    BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
    billentSearchRequest.setStartDate(startDate);
    billentSearchRequest.setEndDate(endDate);
    billentSearchRequest.setPage(pageNumber);
    billentSearchRequest.setSize(pageSize);
    billentSearchRequest.setStatus(walletStatus);
    billentSearchRequest.setDownloadFormat("CSV");
    backOfficeWalletService.downloadWalletInCsv(response, billentSearchRequest);
  }
}
