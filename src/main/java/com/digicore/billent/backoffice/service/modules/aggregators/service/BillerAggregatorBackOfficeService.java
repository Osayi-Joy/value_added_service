package com.digicore.billent.backoffice.service.modules.aggregators.service;

import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.service.BillerAggregatorService;
import com.digicore.request.processor.annotations.MakerChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
@Service
@RequiredArgsConstructor
public class BillerAggregatorBackOfficeService
    implements BillerAggregatorBackOfficeValidatorService {
  private final BillerAggregatorService billerAggregatorServiceImpl;



  @MakerChecker(
      checkerPermission = "approve-enable-biller-aggregator",
      makerPermission = "enable-biller-aggregator",
      requestClassName = "com.digicore.billent.data.lib.modules.billers.dto.BillerAggregatorDTO")
  @Override
  public Object enableBillerAggregator(Object request, Object... args) {
    return billerAggregatorServiceImpl.enableBillerAggregator((BillerAggregatorDTO) request);
  }

  @MakerChecker(
          checkerPermission = "approve-disable-biller-aggregator",
          makerPermission = "disable-biller-aggregator",
          requestClassName = "com.digicore.billent.data.lib.modules.billers.dto.BillerAggregatorDTO")
  @Override
  public Object disableBillerAggregator(Object request, Object... args) {
    return billerAggregatorServiceImpl.disableBillerAggregator((BillerAggregatorDTO) request);
  }

  @MakerChecker(
          checkerPermission = "approve-edit-biller-aggregator",
          makerPermission = "edit-biller-aggregator",
          requestClassName = "com.digicore.billent.data.lib.modules.billers.dto.BillerAggregatorDTO")
  @Override
  public Object updateBillerAggregatorDetail(Object request, Object... args) {
    return billerAggregatorServiceImpl.editBillerAggregator((BillerAggregatorDTO)request);
  }
}
