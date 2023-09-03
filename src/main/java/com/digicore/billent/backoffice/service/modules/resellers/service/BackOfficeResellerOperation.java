package com.digicore.billent.backoffice.service.modules.resellers.service;

import com.digicore.billent.data.lib.modules.backoffice.reseller.BackOfficeResellerService;
import com.digicore.billent.data.lib.modules.backoffice.reseller.dto.BackOfficeResellerProfileDTO;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-03(Sun)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeResellerOperation {

  private final BackOfficeResellerService backOfficeResellerService;
  private final CsvService csvService;

  public PaginatedResponseDTO<BackOfficeResellerProfileDTO> getAllResellers(
      int pageNumber, int pageSize) {
    return backOfficeResellerService.retrieveAllResellers(pageNumber, pageSize);
  }

  public PaginatedResponseDTO<BackOfficeResellerProfileDTO> searchReseller(
          BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerService.search(billentSearchRequest);
  }

  public PaginatedResponseDTO<BackOfficeResellerProfileDTO> fetchResellersByStatusOrDateCreated(
      BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerService.filterResellersByStatusOrDateCreated(billentSearchRequest);
  }

  public void downloadAllResellersInCSV(
      HttpServletResponse response, BillentSearchRequest billentSearchRequest) {
    CsvDto<BackOfficeResellerProfileDTO> csvDto = new CsvDto<>();
    csvDto.setBillentSearchRequest(billentSearchRequest);
    csvDto.setResponse(response);
    csvService.prepareCSVExport(csvDto, backOfficeResellerService::prepareCSV);
  }
}
