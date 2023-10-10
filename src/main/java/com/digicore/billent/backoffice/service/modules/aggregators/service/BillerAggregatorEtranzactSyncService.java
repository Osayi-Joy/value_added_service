package com.digicore.billent.backoffice.service.modules.aggregators.service;

import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.service.BillerAggregatorService;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import com.digicore.request.processor.processors.AuditLogProcessor;
import lombok.RequiredArgsConstructor;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-19(Sat)-2023
 */
@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BillerAggregatorEtranzactSyncService {
  private final BillerAggregatorService etranzactServiceImpl;
  private final AuditLogProcessor auditLogProcessor;

  @RequestType(name = "ETRANZACT")
  public void processEtranzactResync(BillerAggregatorDTO request) {
    etranzactServiceImpl.refreshAggregatorBillersAndProducts(request.getAggregatorAlias());
    auditLogProcessor.saveAuditWithDescription(
            AuditLogActivity.APPROVE_REFRESH_BILLERS_AND_PRODUCTS_UNDER_AN_AGGREGATOR,
            AuditLogActivity.BACKOFFICE,
            AuditLogActivity.APPROVE_REFRESH_BILLERS_AND_PRODUCTS_UNDER_AN_AGGREGATOR_DESCRIPTION.replace("{}", request.getAggregatorName()));
  }
}
