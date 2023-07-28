package com.digicore.billent.backoffice.service.modules.billers.service.impl;

import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeValidatorService;
import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.billent.data.lib.modules.billers.model.Biller;
import com.digicore.billent.data.lib.modules.billers.service.BillerService;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.SearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.registhentication.registration.enums.Status;
import com.digicore.request.processor.annotations.MakerChecker;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/*
 * @author Joy Osayi
 * @createdOn Jul-03(Mon)-2023
 */
@Service
@RequiredArgsConstructor
public class BillerBackOfficeService implements BillerBackOfficeValidatorService {
  private final BillerService<BillerDto, Biller> billerService;
  private final CsvService csvService;

  public PaginatedResponseDTO<BillerDto> getAllBillers(int pageNumber, int pageSize) {
    return billerService.retrieveAllBillers(pageNumber, pageSize);
  }

  public PaginatedResponseDTO<BillerDto> fetchBillersByStatus(
      Status billerStatus, String startDate, String endDate, int pageNumber, int pageSize) {
    return billerService.filterBillersByStatus(
        billerStatus, startDate, endDate, pageNumber, pageSize);
  }

  public void downloadAllBillersInCSV(
      HttpServletResponse response,
      Status billerStatus,
      String startDate,
      String endDate,
      String downLoadFormat,
      int pageNumber,
      int pageSize) {
    SearchRequest searchRequest = new SearchRequest();
    searchRequest.setStatus(billerStatus);
    searchRequest.setStartDate(startDate);
    searchRequest.setEndDate(endDate);
    searchRequest.setDownloadFormat(downLoadFormat);

    CsvDto<BillerDto> csvDto = new CsvDto<>();
    csvDto.setSearchRequest(searchRequest);
    csvDto.setResponse(response);
    csvDto.setPage(pageNumber);
    csvDto.setPageSize(pageSize);
    csvService.prepareCSVExport(csvDto, billerService::prepareCSV);
  }

  public BillerDto fetchBillerById(String billerSystemId) {
    return billerService.retrieveBillerDetailsById(billerSystemId);
  }

  @MakerChecker(
      checkerPermission = "approve-edit-billers",
      makerPermission = "edit-billers",
      requestClassName = "com.digicore.billent.data.lib.modules.billers.dto.BillerDto")
  public Object updateBillerDetail(Object request, Object... args) {
    return billerService.editBiller((BillerDto) request);
  }
  @MakerChecker(
          checkerPermission = "approve-enable-biller",
          makerPermission = "enable-biller",
          requestClassName = "com.digicore.billent.data.lib.modules.billers.dto.BillerDto")
  public Object enableBiller(Object request, Object... args) {
    return billerService.enableBiller((BillerDto) request);
  }
}
