package com.digicore.billent.backoffice.service.modules.wallets.processor;

import com.digicore.billent.backoffice.service.modules.wallets.service.BackOfficeWalletService;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BackOfficeWalletProcessorService {
    private final BackOfficeWalletService backOfficeWalletService;

    @RequestType(name = "creditWallet")
    public Object creditWalletPosition(Object request) {
        return backOfficeWalletService.creditWallet(request);
    }

}
