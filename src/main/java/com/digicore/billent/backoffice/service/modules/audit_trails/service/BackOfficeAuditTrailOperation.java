package com.digicore.billent.backoffice.service.modules.audit_trails.service;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-07(Thu)-2023
 */

import com.digicore.billent.data.lib.modules.backoffice.audit_trail.implementation.BackOfficeAuditTrailServiceImpl;
import com.digicore.billent.data.lib.modules.common.audit_trail.service.AuditTrailService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.dto.AuditLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeAuditTrailOperation {
    private final AuditTrailService backOfficeAuditTrailServiceImpl;

    public PaginatedResponseDTO<AuditLogDTO> fetchSelfTrails(BillentSearchRequest billentSearchRequest){
        return backOfficeAuditTrailServiceImpl.retrieveSelfAuditTrait(billentSearchRequest);
    }

    public PaginatedResponseDTO<AuditLogDTO> fetchAllTrails(BillentSearchRequest billentSearchRequest){
        return backOfficeAuditTrailServiceImpl.retrieveAllAuditTrait(billentSearchRequest);
    }

    public PaginatedResponseDTO<AuditLogDTO> fetchFilteredAuditTrails(BillentSearchRequest billentSearchRequest) {
        return backOfficeAuditTrailServiceImpl.filterAuditTrailsByActivityOrDateRange(billentSearchRequest);
    }
}
