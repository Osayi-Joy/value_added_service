package com.digicore.billent.backoffice.service.modules.wallets.service;

import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class WalletSyncService {
    private final BackOfficeWalletProcessorService backOfficeWalletProcessorService;

    @RequestType(name = "creditWallet")
    public Object creditWalletPosition(Object request) {
        return backOfficeWalletProcessorService.creditWallet(request);
    }

}
