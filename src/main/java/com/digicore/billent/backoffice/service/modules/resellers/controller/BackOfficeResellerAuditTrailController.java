package com.digicore.billent.backoffice.service.modules.resellers.controller;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.RESELLER_AUDIT_TRAIL_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.RESELLER_AUDIT_TRAIL_CONTROLLER_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.RESELLER_AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_DESCRIPTION;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.RESELLER_AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_TRAILS;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.RESELLER_AUDIT_TRAIL_CONTROLLER_TITLE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_NUMBER;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_NUMBER_DEFAULT_VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE_DEFAULT_VALUE;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.VALUE;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.resellers.service.BackOfficeResellerAuditTrailOperation;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.request.processor.annotations.TokenValid;
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
 * @author Ikechi Ucheagwu
 * @createdOn Sep-21(Thu)-2023
 */
@RestController
@RequestMapping(RESELLER_AUDIT_TRAIL_API_V1)
@Tag(name = RESELLER_AUDIT_TRAIL_CONTROLLER_TITLE, description = RESELLER_AUDIT_TRAIL_CONTROLLER_DESCRIPTION)
@RequiredArgsConstructor
public class BackOfficeResellerAuditTrailController {
    private final BackOfficeResellerAuditTrailOperation auditTrailOperation;

    @TokenValid()
    @GetMapping("search")
    @PreAuthorize("hasAuthority('view-all-reseller-audit-trails')")
    @Operation(
        summary = RESELLER_AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_TRAILS,
        description = RESELLER_AUDIT_TRAIL_CONTROLLER_SEARCH_AUDIT_DESCRIPTION)
    public ResponseEntity<Object> searchAuditTrails(
        @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
        int pageNumber,
        @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
        int pageSize,
        @RequestParam(value = VALUE) String value
    ) {
        BillentSearchRequest billentSearchRequest = new BillentSearchRequest();
        billentSearchRequest.setPage(pageNumber);
        billentSearchRequest.setSize(pageSize);
        billentSearchRequest.setValue(value);
        return ControllerResponse.buildSuccessResponse(auditTrailOperation.searchAuditTrails(billentSearchRequest),"Searched audit trails successfully");
    }

}
