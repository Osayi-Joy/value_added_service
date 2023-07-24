package com.digicore.billent.backoffice.service.modules.dashboard.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.dashboard.service.BackofficeTransactionLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.DASHBOARD_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.DOWNLOAD_FORMAT;

@RestController
@RequestMapping(DASHBOARD_API_V1)
@Tag(name = DASHBOARD_CONTROLLER_TITLE, description = DASHBOARD_CONTROLLER_DESCRIPTION)
public class TransactionLogController {

    private final BackofficeTransactionLogService transactionLogService;

    public TransactionLogController(BackofficeTransactionLogService transactionLogService) {
        this.transactionLogService = transactionLogService;
    }

    @GetMapping("get-user-transactions/{customerId}")
    @PreAuthorize("hasAuthority('view-transactions')")
    @Operation(
            summary = DASHBOARD_CONTROLLER_GET_USER_TRANSACTIONS,
            description = DASHBOARD_CONTROLLER_GET_USER_TRANSACTIONS_DESCRIPTION)
    public ResponseEntity<Object> findAllUserTransaction(@PathVariable("customerId") String customerId,
                                                         @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
                                                         int pageNumber,
                                                         @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
                                                         int pageSize) {
        return ControllerResponse.buildSuccessResponse(
                transactionLogService.findByCustomerId(customerId, pageNumber, pageSize), "Transactions retrieved successfully");
    }

    @GetMapping("export-to-csv")
    @PreAuthorize("hasAuthority('export-transactions')")
    @Operation(
            summary = DASHBOARD_CONTROLLER_EXPORT_TRANSACTIONS_IN_CSV_TITLE,
            description = DASHBOARD_CONTROLLER_EXPORT_TRANSACTIONS_IN_CSV_DESCRIPTION)
    public void downloadTransactionsInCSV(
            @RequestParam(value = "customerId") String customerId,
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = DOWNLOAD_FORMAT, required = false) String downloadFormat,
            HttpServletResponse response) {
        transactionLogService.downloadTransactionsInCSV(customerId, pageNumber, pageSize, downloadFormat, response);
    }
}
