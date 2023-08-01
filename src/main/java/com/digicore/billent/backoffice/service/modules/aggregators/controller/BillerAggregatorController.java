package com.digicore.billent.backoffice.service.modules.aggregators.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.aggregators.processor.BillerAggregatorProcessor;
import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.BILLER_AGGREGATORS_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-27(Thu)-2023
 */
@RestController
@RequestMapping(BILLER_AGGREGATORS_API_V1)
@RequiredArgsConstructor
@Tag(name = BILLER_AGGREGATOR_CONTROLLER_TITLE, description = BILLER_AGGREGATOR_CONTROLLER_DESCRIPTION)
public class BillerAggregatorController {
 private final BillerAggregatorProcessor billerAggregatorProcessor;

   @PreAuthorize("hasAuthority('refresh-billers-products-under-an-aggregator')")
    @PatchMapping("refresh-{systemAggregatorId}")
   @Operation(
           summary = BILLER_AGGREGATOR_CONTROLLER_REFRESH_BILLERS_AND_PRODUCTS_TITLE,
           description = BILLER_AGGREGATOR_CONTROLLER_REFRESH_BILLERS_AND_PRODUCTS_DESCRIPTION)
    public ResponseEntity<Object> refreshAggregatorBillerAndProduct(@PathVariable String systemAggregatorId){
       BillerAggregatorDTO billerAggregatorDTO =  billerAggregatorProcessor.refreshAggregatorBillersAndProducts(systemAggregatorId);
       billerAggregatorProcessor.refreshAggregatorBillersAndProducts(billerAggregatorDTO);
       return ControllerResponse.buildSuccessResponse();
    }
}