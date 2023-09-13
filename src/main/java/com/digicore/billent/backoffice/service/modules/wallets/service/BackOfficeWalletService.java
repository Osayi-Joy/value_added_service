package com.digicore.billent.backoffice.service.modules.wallets.service;

import com.digicore.billent.data.lib.modules.common.wallet.dto.WalletBalanceResponseData;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeWalletService {

    private final WalletService walletServiceImpl;

    public WalletBalanceResponseData fetchWalletBalance(String systemWalletId) {
        return walletServiceImpl.retrieveWalletBalance(systemWalletId);
    }
}
