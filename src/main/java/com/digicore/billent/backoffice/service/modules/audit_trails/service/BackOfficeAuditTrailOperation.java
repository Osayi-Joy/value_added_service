package com.digicore.billent.backoffice.service.modules.audit_trails.service;
/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-07(Thu)-2023
 */

import com.digicore.billent.data.lib.modules.common.audit_trail.service.AuditTrailService;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.dto.AuditLogDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeAuditTrailOperation {
    private final AuditTrailService backOfficeAuditTrailServiceImpl;
    private final CsvService csvService;

    public PaginatedResponseDTO<AuditLogDTO> fetchSelfTrails(BillentSearchRequest billentSearchRequest){
        return backOfficeAuditTrailServiceImpl.retrieveSelfAuditTrail(billentSearchRequest);
    }

    public PaginatedResponseDTO<AuditLogDTO> fetchAllTrails(BillentSearchRequest billentSearchRequest){
        return backOfficeAuditTrailServiceImpl.retrieveAllAuditTrail(billentSearchRequest);
    }

    public PaginatedResponseDTO<AuditLogDTO> fetchFilteredAuditTrails(BillentSearchRequest billentSearchRequest) {
        return backOfficeAuditTrailServiceImpl.filterAuditTrailsByActivityAndDateRange(billentSearchRequest);
    }

    public PaginatedResponseDTO<AuditLogDTO> searchAuditTrails(BillentSearchRequest billentSearchRequest) {
        return backOfficeAuditTrailServiceImpl.searchAuditTrails(billentSearchRequest);
    }

    public void exportAuditTrails(BillentSearchRequest billentSearchRequest, HttpServletResponse httpServletResponse) {
        CsvDto<AuditLogDTO> parameter = new CsvDto<>();
        parameter.setBillentSearchRequest(billentSearchRequest);
        parameter.setResponse(httpServletResponse);
        parameter.setPage(billentSearchRequest.getPage());
        parameter.setPageSize(billentSearchRequest.getSize());
        csvService.prepareCSVExport(parameter, backOfficeAuditTrailServiceImpl::prepareAuditTrailsCSV);
    }
}
