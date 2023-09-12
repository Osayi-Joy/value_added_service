package com.digicore.billent.backoffice.service.modules.wallets.service;

import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletBalanceResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.implementation.WalletServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeWalletService {

    private final WalletServiceImpl walletServiceImpl;

    public WalletBalanceResponseData fetchWalletBalance(String systemWalletId) {
        return walletServiceImpl.retrieveWalletBalance(systemWalletId);
    }

    public WalletBalanceResponseData fetchAllWallet() {
        return walletServiceImpl.retrieveWallet();
    }
}
