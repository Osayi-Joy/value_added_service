package com.digicore.billent.backoffice.service.modules.dashboard.service;


import com.digicore.billent.data.lib.modules.backoffice.dashboard.domain.TransactionLog;
import com.digicore.billent.data.lib.modules.backoffice.dashboard.service.TransactionLogService;
import com.digicore.billent.data.lib.modules.backoffice.dashboard.service.dto.TransactionLogDTO;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.SearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.registhentication.registration.enums.Status;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BackofficeTransactionLogService {

    private final TransactionLogService<TransactionLogDTO, TransactionLog> transactionLogService;
    private final CsvService csvService;

    public BackofficeTransactionLogService(TransactionLogService<TransactionLogDTO, TransactionLog> transactionLogService, CsvService csvService) {
        this.transactionLogService = transactionLogService;
        this.csvService = csvService;
    }

    public PaginatedResponseDTO<TransactionLogDTO> findByCustomerId(String customerId, int page, int pageSize) {
        return transactionLogService.findByCustomerId(customerId, page, pageSize);
    }

    public void downloadTransactionsInCSV(String customerId,
                                          int pageNumber, int pageSize,
                                          String downLoadFormat,
                                          HttpServletResponse response) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setStatus(Status.ACTIVE);
        searchRequest.setStartDate(LocalDate.now().toString());
        searchRequest.setEndDate(LocalDate.now().toString());
        searchRequest.setDownloadFormat(downLoadFormat);

        CsvDto<TransactionLogDTO> csvDto = new CsvDto<>();
        csvDto.setSearchRequest(searchRequest);
        csvDto.setResponse(response);
        csvDto.setPage(pageNumber);
        csvDto.setPageSize(pageSize);
        csvDto.getFieldMappings().put("CustomerID", obj -> customerId);
        csvService.prepareCSVExport(csvDto, transactionLogService::prepareCSV);
    }
}
