package com.digicore.billent.backoffice.service.modules.audit_trails.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.AUDIT_TRAIL_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_ACTIVITY_TYPES;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_ACTIVITY_TYPES_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_EXPORT_AUDIT_TRAILS_TO_CSV;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_EXPORT_AUDIT_TRAILS_TO_CSV_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_FETCH_ALL_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_FETCH_ALL_TITLE;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_FETCH_FILTERED_AUDIT_TRAILS;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_FETCH_FILTERED_AUDIT_TRAILS_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_FETCH_SELF_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_FETCH_SELF_TITLE;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_TRAILS;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.AUDIT_TRAIL_CONTROLLER_TITLE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.DOWNLOAD_FORMAT;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.END_DATE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_NUMBER;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_NUMBER_DEFAULT_VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE_DEFAULT_VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PRODUCT_STATUS;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.START_DATE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.VALUE;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.audit_trails.service.BackOfficeAuditTrailOperation;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.request.processor.annotations.TokenValid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-07(Thu)-2023
 */
@RestController
@RequestMapping(AUDIT_TRAIL_API_V1)
@Tag(name = AUDIT_TRAIL_CONTROLLER_TITLE, description = AUDIT_TRAIL_CONTROLLER_DESCRIPTION)
@RequiredArgsConstructor
public class BackOfficeAuditTrailController {
    private final BackOfficeAuditTrailOperation auditTrailOperation;


    @TokenValid()
    @PreAuthorize("hasAuthority('view-self-audit-trails')")
    @GetMapping("get-self")
    @Operation(
            summary = AUDIT_TRAIL_CONTROLLER_FETCH_SELF_TITLE,
            description = AUDIT_TRAIL_CONTROLLER_FETCH_SELF_DESCRIPTION)
    public ResponseEntity<Object> fetchSelfAuditTrail(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
            int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
            int pageSize) {
        BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
        billentSearchRequest.setPage(pageNumber);
        billentSearchRequest.setSize(pageSize);
        return ControllerResponse.buildSuccessResponse(auditTrailOperation.fetchSelfTrails(billentSearchRequest),"Self audit trail fetched successfully");
    }


    @TokenValid()
    @GetMapping("get-all")
    @PreAuthorize("hasAuthority('view-all-audit-trails')")
    @Operation(
            summary = AUDIT_TRAIL_CONTROLLER_FETCH_ALL_TITLE,
            description = AUDIT_TRAIL_CONTROLLER_FETCH_ALL_DESCRIPTION)
    public ResponseEntity<Object> fetchAllAuditTrail(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
            int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
            int pageSize) {
        BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
        billentSearchRequest.setPage(pageNumber);
        billentSearchRequest.setSize(pageSize);
        return ControllerResponse.buildSuccessResponse(auditTrailOperation.fetchAllTrails(billentSearchRequest),"All audit trail fetched successfully");
    }

    @TokenValid()
    @GetMapping("filter")
    @PreAuthorize("hasAuthority('view-all-audit-trails')")
    @Operation(
        summary = AUDIT_TRAIL_CONTROLLER_FETCH_FILTERED_AUDIT_TRAILS,
        description = AUDIT_TRAIL_CONTROLLER_FETCH_FILTERED_AUDIT_TRAILS_DESCRIPTION)
    public ResponseEntity<Object> fetchFilteredAuditTrails(
        @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
        int pageNumber,
        @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
        int pageSize,
        @RequestParam(value = "activity", required = false) String activity,
        @RequestParam(value = START_DATE, required = false) String startDate,
        @RequestParam(value = END_DATE, required = false) String endDate
    ) {
        BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
        billentSearchRequest.setPage(pageNumber);
        billentSearchRequest.setSize(pageSize);
        billentSearchRequest.setKey(activity);
        billentSearchRequest.setStartDate(startDate);
        billentSearchRequest.setEndDate(endDate);
        return ControllerResponse.buildSuccessResponse(auditTrailOperation.fetchFilteredAuditTrails(billentSearchRequest),"Filtered audit trails fetched successfully");
    }

    @TokenValid()
    @GetMapping("search")
    @PreAuthorize("hasAuthority('view-all-audit-trails')")
    @Operation(
        summary = AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_TRAILS,
        description = AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_DESCRIPTION)
    public ResponseEntity<Object> searchAuditTrails(
        @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
        int pageNumber,
        @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
        int pageSize,
        @RequestParam(value = VALUE) @NotBlank(message = "value should not be blank") String value
    ) {
        BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
        billentSearchRequest.setPage(pageNumber);
        billentSearchRequest.setSize(pageSize);
        billentSearchRequest.setValue(value);
        return ControllerResponse.buildSuccessResponse(auditTrailOperation.searchAuditTrails(billentSearchRequest),"Searched audit trails successfully");
    }

    @TokenValid()
    @GetMapping("export-to-csv")
    @PreAuthorize("hasAuthority('view-all-audit-trails')")
    @Operation(
        summary = AUDIT_TRAIL_CONTROLLER_EXPORT_AUDIT_TRAILS_TO_CSV,
        description = AUDIT_TRAIL_CONTROLLER_EXPORT_AUDIT_TRAILS_TO_CSV_DESCRIPTION)
    public void exportAuditTrails(
        @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
        int pageNumber,
        @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
        int pageSize,
        @RequestParam(value = "activity", required = false) String activity,
        @RequestParam(value = START_DATE, required = false) String startDate,
        @RequestParam(value = END_DATE, required = false) String endDate,
        @RequestParam(value = DOWNLOAD_FORMAT, required = false) String downloadFormat,
        HttpServletResponse httpServletResponse
    ) {
        BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
        billentSearchRequest.setPage(pageNumber);
        billentSearchRequest.setSize(pageSize);
        billentSearchRequest.setKey(activity);
        billentSearchRequest.setStartDate(startDate);
        billentSearchRequest.setEndDate(endDate);
        billentSearchRequest.setDownloadFormat(downloadFormat);
        auditTrailOperation.exportAuditTrails(billentSearchRequest, httpServletResponse);
    }

    @TokenValid()
    @GetMapping("activity-types")
    @PreAuthorize("hasAuthority('view-all-audit-trails')")
    @Operation(
        summary = AUDIT_TRAIL_ACTIVITY_TYPES,
        description = AUDIT_TRAIL_ACTIVITY_TYPES_DESCRIPTION)
    public ResponseEntity<Object> fetchAllAuditActivityTypes(
    ) {
        return ControllerResponse.buildSuccessResponse(auditTrailOperation.fetchAllAuditActivityTypes(),"All audit activity types fetched successfully");
    }

}
