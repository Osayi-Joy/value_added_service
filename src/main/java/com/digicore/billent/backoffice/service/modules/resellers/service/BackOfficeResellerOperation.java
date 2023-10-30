package com.digicore.billent.backoffice.service.modules.resellers.service;


import com.digicore.billent.data.lib.modules.backoffice.reseller.dto.BackOfficeResellerProfileDTO;
import com.digicore.billent.data.lib.modules.backoffice.reseller.dto.BackOfficeResellerProfileDetailDTO;
import com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO;
import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.contributor.service.ContributorOperationService;
import com.digicore.billent.data.lib.modules.common.contributor.service.ContributorService;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.transaction.dto.request.TransactionDTO;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.AuditLogProcessor;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Sep-03(Sun)-2023
 */
@Service
@RequiredArgsConstructor
public class BackOfficeResellerOperation implements BackOfficeResellerOperationValidatorService{

  private final ContributorOperationService backOfficeResellerOperationServiceImpl;
  private final AuditLogProcessor auditLogProcessor;
  private final ContributorService<BackOfficeResellerProfileDTO, BackOfficeResellerProfileDetailDTO> backOfficeResellerServiceImpl;
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
          checkerPermission = "approve-disable-reseller-user",
          makerPermission = "disable-reseller-user",
          requestClassName = "com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO")
  public Object disableResellerUser(Object request, Object... args) {
    UserProfileDTO userProfileDTO = (UserProfileDTO) request;
    backOfficeResellerOperationServiceImpl.disableContributorUser(userProfileDTO.getEmail());
    auditLogProcessor.saveAuditWithDescription(AuditLogActivity.APPROVE_DISABLE_RESELLER_USER,AuditLogActivity.BACKOFFICE,AuditLogActivity.APPROVE_DISABLE_RESELLER_USER_DESCRIPTION.replace("{}",userProfileDTO.getEmail()));
    return Optional.empty();
  }
  @MakerChecker(
          checkerPermission = "approve-enable-reseller-user",
          makerPermission = "enable-reseller-user",
          requestClassName = "com.digicore.billent.data.lib.modules.common.authentication.dto.UserProfileDTO")
  public Object enableResellerUser(Object request, Object... args) {
    UserProfileDTO userProfileDTO = (UserProfileDTO) request;
    backOfficeResellerOperationServiceImpl.enableContributorUser(userProfileDTO.getEmail());
    auditLogProcessor.saveAuditWithDescription(AuditLogActivity.APPROVE_ENABLE_RESELLER_USER,AuditLogActivity.BACKOFFICE,AuditLogActivity.APPROVE_ENABLE_RESELLER_USER_DESCRIPTION.replace("{}",userProfileDTO.getEmail()));
    return Optional.empty();
  }

  public UserProfileDTO fetchResellerUserDetails(String email) {
    return (UserProfileDTO) backOfficeResellerServiceImpl.retrieveContributorUserDetails(email);
  }

  public PaginatedResponseDTO<TransactionDTO> fetchAllContributorTransactions(BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.fetchAllContributorTransactions(billentSearchRequest);
  }

  public PaginatedResponseDTO<TransactionDTO> filterContributorTransactions(BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.filterContributorTransactions(billentSearchRequest);
  }

  public PaginatedResponseDTO<TransactionDTO> searchContributorTransactions(BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.searchContributorTransactions(billentSearchRequest);
  }

  public TransactionDTO fetchContributorTransaction(BillentSearchRequest billentSearchRequest) {
    return backOfficeResellerServiceImpl.fetchContributorTransaction(billentSearchRequest);
  }

  public void downloadContributorTransactions(HttpServletResponse response, BillentSearchRequest billentSearchRequest) {
    CsvDto<TransactionDTO> parameter = new CsvDto<>();
    parameter.setBillentSearchRequest(billentSearchRequest);
    parameter.setResponse(response);
    parameter.setPage(billentSearchRequest.getPage());
    parameter.setPageSize(billentSearchRequest.getSize());
    csvService.prepareCSVExport(parameter, backOfficeResellerServiceImpl::prepareContributorTransactionsCSV);
  }

}
