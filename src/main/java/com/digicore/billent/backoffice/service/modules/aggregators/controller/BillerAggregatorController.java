package com.digicore.billent.backoffice.service.modules.aggregators.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.aggregators.processor.BillerAggregatorProcessor;
import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.service.BillerAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLER_AGGREGATORS_API_V1;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-27(Thu)-2023
 */
@RestController
@RequestMapping(BILLER_AGGREGATORS_API_V1)
@RequiredArgsConstructor
public class BillerAggregatorController {
 private final BillerAggregatorProcessor billerAggregatorProcessor;


    @PatchMapping("refresh-{systemAggregatorId}")
    public ResponseEntity<Object> refreshAggregatorBillerAndProduct(@PathVariable String systemAggregatorId){
       BillerAggregatorDTO billerAggregatorDTO =  billerAggregatorProcessor.refreshAggregatorBillersAndProducts(systemAggregatorId);
       billerAggregatorProcessor.refreshAggregatorBillersAndProducts(billerAggregatorDTO);
       return ControllerResponse.buildSuccessResponse();
    }
}
