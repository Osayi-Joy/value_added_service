package com.digicore.billent.backoffice.service.modules.wallets.service;

import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.billent.data.lib.modules.common.dto.CsvDto;
import com.digicore.billent.data.lib.modules.common.services.CsvService;
import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.billent.data.lib.modules.common.wallet.dto.TopUpWalletDTO;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletBalanceResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeWalletService {

    private final WalletService walletServiceImpl;

    private final CsvService csvService;
    public WalletBalanceResponseData fetchWalletBalance(String systemWalletId) {
        return walletServiceImpl.retrieveWalletBalance(systemWalletId);
    }

    public PaginatedResponseDTO<WalletResponseData> fetchAllWallet(BillentSearchRequest billentSearchRequest) {
        return walletServiceImpl.retrieveAllWallets(billentSearchRequest);
    }
    public void creditCustomerWalletPosition(TopUpWalletDTO topUpWalletDTO){
        walletServiceImpl.topUpWallet(topUpWalletDTO);
    }

    public PaginatedResponseDTO<WalletResponseData> searchWallets(BillentSearchRequest billentSearchRequest) {
        return walletServiceImpl.searchWallets(billentSearchRequest);
    }

    public PaginatedResponseDTO<WalletResponseData> filterWallets(BillentSearchRequest billentSearchRequest){
        return walletServiceImpl.filterWallets(billentSearchRequest);
    }
    public PaginatedResponseDTO<WalletResponseData> fetchWalletTransactions(BillentSearchRequest billentSearchRequest, String systemWalletId){
        return walletServiceImpl.retrieveWalletTransaction(billentSearchRequest,systemWalletId);
    }

    public void downloadWalletInCsv(HttpServletResponse response, BillentSearchRequest billentSearchRequest) {
        CsvDto<WalletResponseData> csvDto = new CsvDto<>();
        csvDto.setBillentSearchRequest(billentSearchRequest);
        csvDto.setResponse(response);
        csvService.prepareCSVExport(csvDto, walletServiceImpl::prepareWalletCSV);
    }
}
