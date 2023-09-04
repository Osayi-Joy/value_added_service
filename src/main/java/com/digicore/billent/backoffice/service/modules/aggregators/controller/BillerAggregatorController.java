package com.digicore.billent.backoffice.service.modules.aggregators.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.aggregators.processor.BillerAggregatorProcessor;
import com.digicore.billent.backoffice.service.modules.aggregators.service.BillerAggregatorBackOfficeProxyService;
import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.request.processor.annotations.LogActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLER_AGGREGATORS_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-27(Thu)-2023
 */
@RestController
@RequestMapping(BILLER_AGGREGATORS_API_V1)
@RequiredArgsConstructor
@Tag(name = BILLER_AGGREGATOR_CONTROLLER_TITLE, description = BILLER_AGGREGATOR_CONTROLLER_DESCRIPTION)
public class BillerAggregatorController {
 private final BillerAggregatorProcessor billerAggregatorProcessor;
 private final BillerAggregatorBackOfficeProxyService billerAggregatorBackOfficeProxyService;

    @LogActivity(
            activity = AuditLogActivity.REFRESH_BILLERS_AND_PRODUCTS_UNDER_AN_AGGREGATOR,
            auditType = AuditLogActivity.BACKOFFICE,
            auditDescription = AuditLogActivity.REFRESH_BILLERS_AND_PRODUCTS_UNDER_AN_AGGREGATOR_DESCRIPTION)
   @PreAuthorize("hasAuthority('refresh-billers-products-under-an-aggregator')")
    @PatchMapping("refresh-{systemAggregatorId}")
   @Operation(
           summary = BILLER_AGGREGATOR_CONTROLLER_REFRESH_BILLERS_AND_PRODUCTS_TITLE,
           description = BILLER_AGGREGATOR_CONTROLLER_REFRESH_BILLERS_AND_PRODUCTS_DESCRIPTION)
    public ResponseEntity<Object> refreshAggregatorBillerAndProduct(@PathVariable String systemAggregatorId){
       BillerAggregatorDTO billerAggregatorDTO =  billerAggregatorProcessor.refreshAggregatorBillersAndProducts(systemAggregatorId);
       billerAggregatorProcessor.refreshAggregatorBillersAndProducts(billerAggregatorDTO);
       return ControllerResponse.buildSuccessResponse();
    }
    @GetMapping("get-all")
    @PreAuthorize("hasAuthority('view-biller-aggregators')")
    @Operation(
            summary = BILLER_AGGREGATOR_CONTROLLER_GET_ALL_AGGREGATORS_TITLE,
            description = BILLER_AGGREGATOR_CONTROLLER_GET_ALL_AGGREGATORS_DESCRIPTION)
    public ResponseEntity<Object> getAllAggregators(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
            int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
            int pageSize)
    {
        return ControllerResponse.buildSuccessResponse(
                billerAggregatorProcessor.getAllAggregators(pageNumber, pageSize), "Aggregators retrieved successfully");
    }

    @GetMapping("export-to-csv")
    @PreAuthorize("hasAuthority('export-billers')")
    @Operation(
            summary = BILLER_AGGREGATOR_CONTROLLER_EXPORT_AGGREGATORS_IN_CSV_TITLE,
            description = BILLER_AGGREGATOR_CONTROLLER_EXPORT_AGGREGATORS_IN_CSV_DESCRIPTION)
    public void downloadListOfAggregatorsInCSV(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = START_DATE, required = false) String startDate,
            @RequestParam(value = END_DATE, required = false) String endDate,
            @RequestParam(value = BILLER_STATUS, required = false) Status aggregatorStatus,
            @RequestParam(value = DOWNLOAD_FORMAT, required = false) String downloadFormat,
            HttpServletResponse response) {
        billerAggregatorProcessor.downloadAllAggregatorsInCSV(response, aggregatorStatus, startDate, endDate, downloadFormat, pageNumber, pageSize);
    }
    @GetMapping("get-{aggregatorSystemId}-details")
    @PreAuthorize("hasAuthority('view-biller-aggregator-details')")
    @Operation(
            summary = BILLER_AGGREGATOR_CONTROLLER_GET_AGGREGATOR_TITLE,
            description = BILLER_AGGREGATOR_CONTROLLER_GET_AGGREGATOR_DESCRIPTION)
    public ResponseEntity<Object> fetchBillerAggregatorById(@PathVariable String aggregatorSystemId) {
        return ControllerResponse.buildSuccessResponse(
                billerAggregatorProcessor.fetchBillerAggregatorById(aggregatorSystemId), "Retrieved aggregator's details successfully");
    }

    @LogActivity(
            activity = AuditLogActivity.ENABLE_BILLER_AGGREGATOR,
            auditType = AuditLogActivity.BACKOFFICE,
            auditDescription = AuditLogActivity.ENABLE_BILLER_AGGREGATOR_DESCRIPTION)
    @PatchMapping("enable-{aggregatorSystemId}")
    @PreAuthorize("hasAuthority('enable-biller-aggregator')")
    @Operation(
            summary = BILLER_AGGREGATOR_CONTROLLER_ENABLE_AGGREGATOR_TITLE,
            description = BILLER_AGGREGATOR_CONTROLLER_ENABLE_AGGREGATOR_DESCRIPTION)
    public ResponseEntity<Object> enableAggregator(@PathVariable String aggregatorSystemId) {
        return ControllerResponse.buildSuccessResponse(
                billerAggregatorBackOfficeProxyService.enableBillerAggregator(aggregatorSystemId),
                "Aggregator enabled successfully");
    }

    @LogActivity(
            activity = AuditLogActivity.DISABLE_BILLER_AGGREGATOR,
            auditType = AuditLogActivity.BACKOFFICE,
            auditDescription = AuditLogActivity.DISABLE_BILLER_AGGREGATOR_DESCRIPTION)
    @PatchMapping("disable-{aggregatorSystemId}")
    @PreAuthorize("hasAuthority('disable-biller-aggregator')")
    @Operation(
            summary = BILLER_AGGREGATOR_CONTROLLER_ENABLE_AGGREGATOR_TITLE,
            description = BILLER_AGGREGATOR_CONTROLLER_ENABLE_AGGREGATOR_DESCRIPTION)
    public ResponseEntity<Object> disableAggregator(@PathVariable String aggregatorSystemId) {
        return ControllerResponse.buildSuccessResponse(
                billerAggregatorBackOfficeProxyService.disableBillerAggregator(aggregatorSystemId),
                "Aggregator disabled successfully");
    }

    @LogActivity(
            activity = AuditLogActivity.EDIT_BILLER_AGGREGATOR,
            auditType = AuditLogActivity.BACKOFFICE,
            auditDescription = AuditLogActivity.EDIT_BILLER_AGGREGATOR_DESCRIPTION)
    @PatchMapping("edit")
    @PreAuthorize("hasAuthority('edit-biller-aggregator')")
    @Operation(
            summary = BILLER_AGGREGATOR_CONTROLLER_UPDATE_AGGREGATOR_TITLE,
            description = BILLER_AGGREGATOR_CONTROLLER_UPDATE_AGGREGATOR_DESCRIPTION)
    public ResponseEntity<Object> updateBillerAggregatorDetail(@Valid @RequestBody BillerAggregatorDTO billerAggregatorDTO) {
        return ControllerResponse.buildSuccessResponse(billerAggregatorBackOfficeProxyService.updateBillerAggregatorDetail(billerAggregatorDTO),"Updated aggregator details successfully");
    }
}
