package com.digicore.billent.backoffice.service.modules.billers.service;


import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.billent.data.lib.modules.billers.service.BillerService;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.SearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.digicore.registhentication.registration.enums.Status;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class BillerBackOfficeService {
    private final BillerService billerService;
    private final CsvService csvService;


    public PaginatedResponseDTO<BillerDto> getAllBillers(int pageNumber, int pageSize){
        return billerService.retrieveAllBillers(pageNumber, pageSize);
    }
    public PaginatedResponseDTO<BillerDto> fetchBillersByStatus(Status billerStatus, String startDate, String endDate, int pageNumber, int pageSize){
        return billerService.filterBillersByStatus(billerStatus, startDate, endDate, pageNumber, pageSize);
    }

    public void downloadAllBillersInCSV(HttpServletResponse response, SearchRequest searchRequest, int pageNumber, int pageSize) {
        CsvDto<BillerDto> csvDto = new CsvDto<>();
        csvDto.setSearchRequest(searchRequest);
        csvDto.setResponse(response);
        csvDto.setPage(pageNumber);
        csvDto.setPageSize(pageSize);
        csvService.prepareCSVExport(csvDto, billerService::prepareCSV);
    }
}
