package com.digicore.billent.backoffice.service.modules.aggregators.processor;

import static java.util.Objects.isNull;

import com.digicore.api.helper.exception.ZeusRuntimeException;
import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.service.BillerAggregatorService;
import com.digicore.registhentication.exceptions.ExceptionHandler;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.RequestHandlerPostProcessor;
import com.digicore.request.processor.processors.RequestHandlers;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-14(Wed)-2022
 */

@Slf4j
@Component
public class BillerAggregatorProcessor {

    private RequestHandlers requestHandlers;

    private final ExceptionHandler<String, String, HttpStatus, String> exceptionHandler;
    private final RequestHandlerPostProcessor requestHandlerPostProcessor;

    private final BillerAggregatorService billerAggregatorService;

    public BillerAggregatorProcessor(ExceptionHandler<String, String, HttpStatus, String> exceptionHandler, RequestHandlerPostProcessor requestHandlerPostProcessor, @Qualifier("BillerAggregatorServiceImpl") BillerAggregatorService billerAggregatorService) {
        this.exceptionHandler = exceptionHandler;
        this.requestHandlerPostProcessor = requestHandlerPostProcessor;
        this.billerAggregatorService = billerAggregatorService;
    }

    @PostConstruct
    public void init() {
        requestHandlers = requestHandlerPostProcessor.getHandlers();
    }

    private static final String FUNCTION_NOT_SUPPORTED_TEXT = "WebEngine does not support specified request type";

    @MakerChecker(
            checkerPermission = "approve-refresh-billers-products-under-an-aggregator",
            makerPermission = "refresh-billers-products-under-an-aggregator",
            requestClassName =
                    "com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO")
    public void process(BillerAggregatorDTO request) {
        if (isNull(request) || StringUtils.isEmpty(request.getAggregatorAlias())) {
            exceptionHandler.processCustomException("aggregator syncing failed because unknowm aggregator sync was requeted. see the aggregator passed ".concat(request.getAggregatorAlias()),"000", HttpStatus.INTERNAL_SERVER_ERROR, "000");
        }
        log.trace("Aggregator in sync is {}", request.getAggregatorAlias());
        try {
            requestHandlers.handle(request.getAggregatorAlias(), request, BillerAggregatorDTO.class);
        } catch (ZeusRuntimeException e) {
            log.error(FUNCTION_NOT_SUPPORTED_TEXT, e);
        }
    }

    public BillerAggregatorDTO refreshAggregatorBillersAndProducts(String aggregatorSystemId){
       return billerAggregatorService.getBillerAggregator(aggregatorSystemId);
    }



}
