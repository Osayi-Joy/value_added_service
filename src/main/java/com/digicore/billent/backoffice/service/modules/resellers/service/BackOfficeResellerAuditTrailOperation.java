package com.digicore.billent.backoffice.service.modules.resellers.service;
/*
 * @author Ikechi Ucheagwu
 * @createdOn Sep-21(Thu)-2023
 */

import com.digicore.billent.data.lib.modules.common.audit_trail.service.AuditTrailService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.dto.AuditLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeResellerAuditTrailOperation {

  private final AuditTrailService resellerAuditTrailServiceImpl;

  public PaginatedResponseDTO<AuditLogDTO> searchAuditTrails(
      BillentSearchRequest billentSearchRequest) {
    return resellerAuditTrailServiceImpl.searchAuditTrails(billentSearchRequest);
  }
}
