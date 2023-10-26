package com.digicore.billent.backoffice.service.modules.wallets.service;

import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletBalanceResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletOperationRequest;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.AuditLogProcessor;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeWalletService implements BackOfficeWalletValidatorService {

  private final WalletService walletServiceImpl;

  private final CsvService csvService;

  private final AuditLogProcessor auditLogProcessor;

  public WalletBalanceResponseData fetchWalletBalance(String systemWalletId) {
    return walletServiceImpl.retrieveWalletBalance(systemWalletId);
  }

  public PaginatedResponseDTO<WalletResponseData> fetchAllWallet(
      BillentSearchRequest billentSearchRequest) {
    return walletServiceImpl.retrieveAllWallets(billentSearchRequest);
  }

  @MakerChecker(
      checkerPermission = "approve-credit-wallet",
      makerPermission = "credit-wallet",
      requestClassName = "com.digicore.billent.data.lib.modules.common.wallet.dto.TopUpWalletDTO")
  @Override
  public Object creditWallet(Object request, Object... args) {
    WalletOperationRequest topUpWalletDTO = (WalletOperationRequest) request;
    walletServiceImpl.topUpWallet(topUpWalletDTO);
    auditLogProcessor.saveAuditWithDescription(
        AuditLogActivity.APPROVE_CREDIT_WALLET_POSITION,
        AuditLogActivity.BACKOFFICE,
        AuditLogActivity.APPROVE_CREDIT_WALLET_POSITION_DESCRIPTION.replace(
            "{}", topUpWalletDTO.getSystemWalletID()));
    return Optional.empty();
  }

  public PaginatedResponseDTO<WalletResponseData> searchWallets(
      BillentSearchRequest billentSearchRequest) {
    return walletServiceImpl.searchWallets(billentSearchRequest);
  }

  public PaginatedResponseDTO<WalletResponseData> filterWallets(
      BillentSearchRequest billentSearchRequest) {
    return walletServiceImpl.filterWallets(billentSearchRequest);
  }

  public PaginatedResponseDTO<WalletResponseData> fetchWalletTransactions(
      BillentSearchRequest billentSearchRequest, String systemWalletId) {
    return walletServiceImpl.retrieveWalletTransaction(billentSearchRequest, systemWalletId);
  }

  public void downloadWalletInCsv(
      HttpServletResponse response, BillentSearchRequest billentSearchRequest) {
    CsvDto<WalletResponseData> csvDto = new CsvDto<>();
    csvDto.setBillentSearchRequest(billentSearchRequest);
    csvDto.setResponse(response);
    csvService.prepareCSVExport(csvDto, walletServiceImpl::prepareWalletCSV);
  }
}
