package com.digicore.billent.backoffice.service.modules.resellers.service;


import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.contributor.dto.BackOfficeResellerProfileDTO;
import com.digicore.billent.data.lib.modules.common.contributor.dto.BackOfficeResellerProfileDetailDTO;
import com.digicore.billent.data.lib.modules.common.contributor.service.BackOfficeContributorService;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.billent.data.lib.modules.reseller.dto.ResellerDTO;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.annotations.MakerChecker;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-03(Sun)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeResellerOperation implements BackOfficeResellerValidatorService {

  private final BackOfficeContributorService<BackOfficeResellerProfileDTO, BackOfficeResellerProfileDetailDTO> backOfficeResellerServiceImpl;
  private final CsvService csvService;

  public PaginatedResponseDTO<BackOfficeResellerProfileDTO> getAllResellers(
      int pageNumber, int pageSize) {
    return backOfficeResellerServiceImpl.retrieveAllContributors(pageNumber, pageSize);
  }

  public PaginatedResponseDTO<UserProfileDTO> getAllResellerUsers(BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.retrieveContributorUsers(billentSearchRequest);
  }

  public BackOfficeResellerProfileDetailDTO fetchResellerProfile(String resellerId) {
    return backOfficeResellerServiceImpl.retrieveContributorDetailsById(resellerId);
  }

  public BackOfficeResellerProfileDetailDTO fetchResellerWalletBalance(String resellerId) {
    return backOfficeResellerServiceImpl.retrieveContributorWalletBalance(resellerId);
  }

  public PaginatedResponseDTO<UserProfileDTO> searchResellerDetail(
      BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.searchContributorDetail(billentSearchRequest);
  }

  public PaginatedResponseDTO<UserProfileDTO> fetchResellersDetailByStatusOrDateCreated(
      BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.filterContributorDetailByStatusOrDateCreated(
        billentSearchRequest);
  }

  public void downloadAllResellerUserInCSV(
      HttpServletResponse response, BillentSearchRequest billentSearchRequest) {
    CsvDto<UserProfileDTO> csvDto = new CsvDto<>();
    csvDto.setBillentSearchRequest(billentSearchRequest);
    csvDto.setResponse(response);
    csvService.prepareCSVExport(csvDto, backOfficeResellerServiceImpl::prepareContributorUserCSV);
  }

  public PaginatedResponseDTO<BackOfficeResellerProfileDTO> searchReseller(
      BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.searchContributor(billentSearchRequest);
  }

  public PaginatedResponseDTO<BackOfficeResellerProfileDTO> fetchResellersByStatusOrDateCreated(
      BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.filterContributorsByStatusOrDateCreated(billentSearchRequest);
  }

  public void downloadAllResellersInCSV(
      HttpServletResponse response, BillentSearchRequest billentSearchRequest) {
    CsvDto<BackOfficeResellerProfileDTO> csvDto = new CsvDto<>();
    csvDto.setBillentSearchRequest(billentSearchRequest);
    csvDto.setResponse(response);
    csvService.prepareCSVExport(csvDto, backOfficeResellerServiceImpl::prepareContributorCSV);
  }

  @MakerChecker(
          checkerPermission = "approve_disable-reseller",
          makerPermission = "disable-reseller",
          requestClassName = "com.digicore.billent.data.lib.modules.reseller.dto.ResellerDTO")
  @Override
  public void disableReseller(Object request, Object... args) {
    ResellerDTO resellerDTO = (ResellerDTO) request;
    backOfficeResellerServiceImpl.disableContributor(resellerDTO.getResellerId());
  }
}
