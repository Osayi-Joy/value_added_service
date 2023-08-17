package com.digicore.billent.backoffice.service.modules.aggregators.processor;

import static com.digicore.billent.data.lib.modules.exception.messages.BillerAggregatorErrorMessage.BILLER_AGGREGATOR_REFRESH_ALREADY_REQUESTED_CODE;
import static com.digicore.billent.data.lib.modules.exception.messages.BillerAggregatorErrorMessage.BILLER_AGGREGATOR_REFRESH_ALREADY_REQUESTED_MESSAGE;

import com.digicore.api.helper.exception.ZeusRuntimeException;
import com.digicore.billent.data.lib.modules.billers.aggregator.dto.BillerAggregatorDTO;
import com.digicore.billent.data.lib.modules.billers.aggregator.service.BillerAggregatorService;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.exceptions.ExceptionHandler;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.RequestHandlerPostProcessor;
import com.digicore.request.processor.processors.RequestHandlers;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-14(Wed)-2022
 */


@Component
@Slf4j
public class BillerAggregatorProcessor {

    private RequestHandlers requestHandlers;

    private final ExceptionHandler<String, String, HttpStatus, String> exceptionHandler;
    private final RequestHandlerPostProcessor requestHandlerPostProcessor;

    private final BillerAggregatorService billerAggregatorService;
    private final CsvService csvService;

    public BillerAggregatorProcessor(ExceptionHandler<String, String, HttpStatus, String> exceptionHandler, RequestHandlerPostProcessor requestHandlerPostProcessor, @Qualifier("BillerAggregatorServiceImpl") BillerAggregatorService billerAggregatorService, CsvService csvService) {
        this.exceptionHandler = exceptionHandler;
        this.requestHandlerPostProcessor = requestHandlerPostProcessor;
        this.billerAggregatorService = billerAggregatorService;
        this.csvService = csvService;
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
    public Object getAllAggregators(int pageNumber, int pageSize, String paginated) {
        if ("false".equalsIgnoreCase(paginated))
            return billerAggregatorService.retrieveAllAggregators();
        return billerAggregatorService.retrieveAllAggregators(pageNumber, pageSize);
    }

    public void downloadAllAggragatorsInCSV(HttpServletResponse response, Status aggregatorStatus,
                                            String startDate, String endDate, String downloadFormat,
                                            int pageNumber, int pageSize) {
        BillentSearchRequest searchRequest = new BillentSearchRequest();
        searchRequest.setStatus(aggregatorStatus);
        searchRequest.setStartDate(startDate);
        searchRequest.setEndDate(endDate);
        searchRequest.setDownloadFormat(downloadFormat);

        CsvDto<BillerAggregatorDTO> csvDto = new CsvDto<>();
        csvDto.setBillentSearchRequest(searchRequest);
        csvDto.setResponse(response);
        csvDto.setPage(pageNumber);
        csvDto.setPageSize(pageSize);
        csvService.prepareCSVExport(csvDto, billerAggregatorService::prepareCSV);
    }
}
