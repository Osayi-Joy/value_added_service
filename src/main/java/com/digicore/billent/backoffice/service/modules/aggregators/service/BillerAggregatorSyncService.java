package com.digicore.billent.backoffice.service.modules.aggregators.service;

import com.digicore.billent.backoffice.service.modules.aggregators.processor.BillerAggregatorProcessor;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-27(Thu)-2023
 */
@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BillerAggregatorSyncService {

  //    private final BillerAggregatorService billerAggregatorService;

  private final BillerAggregatorProcessor billerAggregatorProcessor;

  //    public BillerAggregatorSyncService(@Qualifier("EtranzactServiceImpl")BillerAggregatorService
  // billerAggregatorService, BillerAggregatorProcessor billerAggregatorProcessor) {
  //        this.billerAggregatorService = billerAggregatorService;
  //        this.billerAggregatorProcessor = billerAggregatorProcessor;
  //    }

  @RequestType(name = "refreshAggregatorBillersAndProducts")
  public void refreshAggregatorBillersAndProducts(Object request) {
    billerAggregatorProcessor.refreshAggregatorBillersAndProducts(request);
  }

  //    @RequestType(name = "ETRANZACT")
  //    public void processEtranzactResync(BillerAggregatorDTO request)  {
  //
  // billerAggregatorService.refreshAggregatorBillersAndProducts(request.getAggregatorAlias());
  //    }
  //
  //    @RequestType(name = "INTERSWITCH")
  //    public void processInterswitchResync(BillerAggregatorDTO request)  {
  //
  // billerAggregatorService.refreshAggregatorBillersAndProducts(request.getAggregatorAlias());
  //    }

}
