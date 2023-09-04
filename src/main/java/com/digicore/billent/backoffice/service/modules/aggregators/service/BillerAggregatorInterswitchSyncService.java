package com.digicore.billent.backoffice.service.modules.aggregators.service;


import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.backoffice.biller_aggregator.service.BillerAggregatorService;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-19(Sat)-2023
 */
@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BillerAggregatorInterswitchSyncService {
  private final BillerAggregatorService interswitchServiceImpl;

  @RequestType(name = "INTERSWITCH")
  public void processInterswitchResync(BillerAggregatorDTO request) {
    interswitchServiceImpl.refreshAggregatorBillersAndProducts(request.getAggregatorAlias());
  }
}
