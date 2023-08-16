package com.digicore.billent.backoffice.service.modules.aggregators.service;

import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.service.BillerAggregatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
@Service
public class BillerAggregatorBackOfficeProxyService {
  private final BillerAggregatorService billerAggregatorService;
  private final BillerAggregatorBackOfficeValidatorService validatorService;

  public BillerAggregatorBackOfficeProxyService(
          @Qualifier("BillerAggregatorServiceImpl") BillerAggregatorService billerAggregatorService,
          BillerAggregatorBackOfficeValidatorService validatorService) {
    this.billerAggregatorService = billerAggregatorService;
    this.validatorService = validatorService;
  }

  public Object enableBillerAggregator(String aggregatorSystemId) {
    billerAggregatorService.isBillerAggregatorPresent(aggregatorSystemId);
    return validatorService.enableBillerAggregator(aggregatorSystemId);
  }

    public Object disableBillerAggregator(String aggregatorSystemId) {
      billerAggregatorService.isBillerAggregatorPresent(aggregatorSystemId);
      return validatorService.disableBillerAggregator(aggregatorSystemId);
    }
}
