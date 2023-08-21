package com.digicore.billent.backoffice.service.modules.aggregators.service;

import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.service.BillerAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
@Service
@RequiredArgsConstructor
public class BillerAggregatorBackOfficeProxyService {
  private final BillerAggregatorService billerAggregatorServiceImpl;
  private final BillerAggregatorBackOfficeValidatorService validatorService;

  public Object enableBillerAggregator(String aggregatorSystemId) {
    billerAggregatorServiceImpl.isBillerAggregatorPresent(aggregatorSystemId);
    return validatorService.enableBillerAggregator(aggregatorSystemId);
  }

    public Object disableBillerAggregator(String aggregatorSystemId) {
      billerAggregatorServiceImpl.isBillerAggregatorPresent(aggregatorSystemId);
      return validatorService.disableBillerAggregator(aggregatorSystemId);
    }

  public Object updateBillerAggregatorDetail(BillerAggregatorDTO billerAggregatorDTO) {
    billerAggregatorServiceImpl.isBillerAggregatorPresent(billerAggregatorDTO.getAggregatorSystemId());
    return validatorService.updateBillerAggregatorDetail(billerAggregatorDTO);
  }
}
