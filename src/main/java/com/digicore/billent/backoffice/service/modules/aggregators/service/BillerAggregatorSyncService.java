package com.digicore.billent.backoffice.service.modules.aggregators.service;

import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.service.BillerAggregatorService;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jul-27(Thu)-2023
 */
@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BillerAggregatorSyncService {

    @Qualifier("EtranzactServiceImpl")
    private final BillerAggregatorService billerAggregatorService;

    @RequestType(name = "ETRANZACT")
    public void process(BillerAggregatorDTO request)  {
        billerAggregatorService.refreshAggregatorBillersAndProducts(request.getAggregatorAlias());
    }
}
