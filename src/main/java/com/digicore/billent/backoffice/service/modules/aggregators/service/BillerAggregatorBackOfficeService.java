package com.digicore.billent.backoffice.service.modules.aggregators.service;


import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.service.BillerAggregatorService;
import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.AuditLogProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
@Service
@RequiredArgsConstructor
public class BillerAggregatorBackOfficeService
    implements BillerAggregatorBackOfficeValidatorService {
  private final BillerAggregatorService billerAggregatorServiceImpl;
  private final AuditLogProcessor auditLogProcessor;



  @MakerChecker(
      checkerPermission = "approve-enable-biller-aggregator",
      makerPermission = "enable-biller-aggregator",
      requestClassName = "com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO")
  @Override
  public Object enableBillerAggregator(Object request, Object... args) {
    BillerAggregatorDTO billerAggregatorDTO = (BillerAggregatorDTO) request;
    billerAggregatorServiceImpl.enableBillerAggregator(billerAggregatorDTO);
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_ENABLE_BILLER_AGGREGATOR,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_ENABLE_BILLER_AGGREGATOR_DESCRIPTION.replace("{}", billerAggregatorDTO.getAggregatorSystemId()));
    return Optional.empty();
  }

  @MakerChecker(
          checkerPermission = "approve-disable-biller-aggregator",
          makerPermission = "disable-biller-aggregator",
          requestClassName = "com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO")
  @Override
  public Object disableBillerAggregator(Object request, Object... args) {
    BillerAggregatorDTO billerAggregatorDTO = (BillerAggregatorDTO) request;
    billerAggregatorServiceImpl.disableBillerAggregator((BillerAggregatorDTO) request);
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_DISABLE_BILLER_AGGREGATOR,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_DISABLE_BILLER_AGGREGATOR_DESCRIPTION.replace("{}", billerAggregatorDTO.getAggregatorSystemId()));
    return Optional.empty();
  }

  @MakerChecker(
          checkerPermission = "approve-edit-biller-aggregator",
          makerPermission = "edit-biller-aggregator",
          requestClassName = "com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO")
  @Override
  public Object updateBillerAggregatorDetail(Object request, Object... args) {
    BillerAggregatorDTO billerAggregatorDTO = (BillerAggregatorDTO) request;
    billerAggregatorServiceImpl.editBillerAggregator((BillerAggregatorDTO)request);
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_EDIT_BILLER_AGGREGATOR,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_EDIT_BILLER_AGGREGATOR_DESCRIPTION.replace("{}", billerAggregatorDTO.getAggregatorName()));
    return Optional.empty();
  }

  public PaginatedResponseDTO<BillerDto> viewBillersUnderAnAggregator(int pageNumbe, int pageSize, String aggregatorSystemId){
    return billerAggregatorServiceImpl.retrieveAllBillersUnderAnAggregator(pageNumbe, pageSize, aggregatorSystemId);
  }
}
