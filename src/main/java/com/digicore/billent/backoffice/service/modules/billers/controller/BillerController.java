package com.digicore.billent.backoffice.service.modules.billers.controller;


import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLERS_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.BackOfficePageableUtil.*;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeService;
import com.digicore.billent.data.lib.modules.common.util.SearchRequest;
import com.digicore.registhentication.registration.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BILLERS_API_V1)
@Tag(name = BILLER_CONTROLLER_TITLE, description = BILLER_CONTROLLER_DESCRIPTION)
public class BillerController {
    private final BillerBackOfficeService billerBackOfficeService;

    @GetMapping("get-all-billers")
    @PreAuthorize("hasAuthority('view-billers')")
    @Operation(
            summary = BILLER_CONTROLLER_GET_ALL_BILLERS_TITLE,
            description = BILLER_CONTROLLER_GET_ALL_BILLERS_DESCRIPTION)
    public ResponseEntity<Object> viewAllBillers(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize)
    {
        return ControllerResponse.buildSuccessResponse(
                billerBackOfficeService.getAllBillers(pageNumber, pageSize), "Retrieved All Billers Successfully");
    }
    
    @GetMapping("export-billers-to-csv")
    @PreAuthorize("hasAuthority('export-billers')")
    @Operation(
            summary = BILLER_CONTROLLER_EXPORT_BILLERS_IN_CSV_TITLE,
            description = BILLER_CONTROLLER_EXPORT_BILLERS_IN_CSV_DESCRIPTION)
    public void downloadListOfBillersInCSV(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @Valid @RequestBody SearchRequest searchRequest,
            HttpServletResponse response) {
        billerBackOfficeService.downloadAllBillersInCSV(response, searchRequest, pageNumber, pageSize);
    }

    @GetMapping("filter-billers-by-billerStatus")
    @PreAuthorize("hasAuthority('view-billers')")
    @Operation(
            summary = BILLER_CONTROLLER_FETCH_BILLERS_BY_STATUS_TITLE,
            description = BILLER_CONTROLLER_FETCH_BILLERS_BY_STATUS_DESCRIPTION)
    public ResponseEntity<Object> viewAllBillers(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = START_DATE, required = false) String startDate,
            @RequestParam(value = END_DATE, required = false) String endDate,
            @RequestParam(value = "billerStatus", required = false) Status billerStatus)
    {
        return ControllerResponse.buildSuccessResponse(
                billerBackOfficeService.fetchBillersByStatus(billerStatus, startDate, endDate, pageNumber, pageSize), "Retrieved All Billers by Status Successfully");
    }
}
