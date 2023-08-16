package com.digicore.billent.backoffice.service.modules.aggregators.processor;

import static com.digicore.billent.data.lib.modules.exception.messages.BillerAggregatorErrorMessage.BILLER_AGGREGATOR_REFRESH_ALREADY_REQUESTED_CODE;
import static com.digicore.billent.data.lib.modules.exception.messages.BillerAggregatorErrorMessage.BILLER_AGGREGATOR_REFRESH_ALREADY_REQUESTED_MESSAGE;
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
    public void refreshAggregatorBillersAndProducts(Object request,Object... args) {
        BillerAggregatorDTO billerAggregatorDTO = (BillerAggregatorDTO) request;
        log.trace("Aggregator in sync is {}", billerAggregatorDTO.getAggregatorAlias());
        try {
            requestHandlers.handle(billerAggregatorDTO.getAggregatorAlias(), billerAggregatorDTO, BillerAggregatorDTO.class);
        } catch (ZeusRuntimeException e) {
            log.error(FUNCTION_NOT_SUPPORTED_TEXT, e);
        }
    }

    public BillerAggregatorDTO refreshAggregatorBillersAndProducts(String aggregatorSystemId){
        BillerAggregatorDTO billerAggregatorDTO = billerAggregatorService.getBillerAggregatorForRefresh(aggregatorSystemId);
        if (billerAggregatorDTO.isSyncRequested())
            exceptionHandler.processCustomException(BILLER_AGGREGATOR_REFRESH_ALREADY_REQUESTED_MESSAGE,BILLER_AGGREGATOR_REFRESH_ALREADY_REQUESTED_CODE,HttpStatus.CONFLICT,BILLER_AGGREGATOR_REFRESH_ALREADY_REQUESTED_CODE);
        billerAggregatorService.preventOtherRefreshRequest(aggregatorSystemId);
        return billerAggregatorDTO;
    }


    public BillerAggregatorDTO fetchBillerAggregatorById(String aggregatorSystemId) {
        return billerAggregatorService.retrieveBillerAggregatorDetailsById(aggregatorSystemId);
    }
}
