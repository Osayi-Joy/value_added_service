package com.digicore.billent.backoffice.service.modules.wallets.service;

import com.digicore.billent.data.lib.modules.common.util.BillentSearchRequest;
import com.digicore.billent.data.lib.modules.common.wallet.dto.TopUpWalletDTO;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletBalanceResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeWalletService {

    private final WalletService walletServiceImpl;

    public WalletBalanceResponseData fetchWalletBalance(String systemWalletId) {
        return walletServiceImpl.retrieveWalletBalance(systemWalletId);
    }

    public PaginatedResponseDTO<WalletResponseData> fetchAllWallet(BillentSearchRequest billentSearchRequest) {
        return walletServiceImpl.retrieveAllWallets(billentSearchRequest);
    }
    public void creditCustomerWalletPosition(TopUpWalletDTO topUpWalletDTO){
        walletServiceImpl.topUpWallet(topUpWalletDTO);
    }
}
