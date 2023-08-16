package com.digicore.billent.backoffice.service.modules.aggregators.service;

import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.service.BillerAggregatorService;
import com.digicore.request.processor.annotations.MakerChecker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
@Service
public class BillerAggregatorBackOfficeService
    implements BillerAggregatorBackOfficeValidatorService {
  private final BillerAggregatorService billerAggregatorService;

  public BillerAggregatorBackOfficeService(@Qualifier("BillerAggregatorServiceImpl") BillerAggregatorService billerAggregatorService) {
    this.billerAggregatorService = billerAggregatorService;
  }

  @MakerChecker(
      checkerPermission = "approve-enable-biller-aggregator",
      makerPermission = "enable-biller-aggregator",
      requestClassName = "com.digicore.billent.data.lib.modules.billers.dto.BillerAggregatorDTO")
  @Override
  public Object enableBillerAggregator(Object request, Object... args) {
    return billerAggregatorService.enableBillerAggregator((BillerAggregatorDTO) request);
  }
}
