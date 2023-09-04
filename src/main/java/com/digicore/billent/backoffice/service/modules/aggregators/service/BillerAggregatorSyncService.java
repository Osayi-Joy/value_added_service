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

  private final BillerAggregatorBackOfficeService billerAggregatorBackOfficeService;

  private final BillerAggregatorProcessor billerAggregatorProcessor;


  @RequestType(name = "refreshAggregatorBillersAndProducts")
  public void refreshAggregatorBillersAndProducts(Object request) {
    billerAggregatorProcessor.refreshAggregatorBillersAndProducts(request);
  }

  @RequestType(name = "disableBillerAggregator")
  public Object disableBillerAggregator(Object request) {
    return billerAggregatorBackOfficeService.disableBillerAggregator(request);
  }

  @RequestType(name = "enableBillerAggregator")
  public Object enableBillerAggregator(Object request) {
    return billerAggregatorBackOfficeService.enableBillerAggregator(request);
  }

  @RequestType(name = "updateBillerAggregatorDetail")
  public Object updateBillerAggregatorDetail(Object request) {
    return billerAggregatorBackOfficeService.updateBillerAggregatorDetail(request);
  }


}
