package com.digicore.billent.backoffice.service.modules.wallets.service;

import com.digicore.billent.data.lib.modules.common.constants.AuditLogActivity;
import com.digicore.billent.data.lib.modules.common.wallet.dto.TopUpWalletDTO;
import com.digicore.billent.data.lib.modules.common.wallet.service.WalletService;
import com.digicore.request.processor.annotations.MakerChecker;
import com.digicore.request.processor.processors.AuditLogProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BackOfficeWalletProcessorService implements BackOfficeWalletValidatorService {

    private final WalletService walletServiceImpl;
    private final AuditLogProcessor auditLogProcessor;

    @MakerChecker(
            checkerPermission = "approve-credit-wallet",
            makerPermission = "credit-wallet",
            requestClassName = "com.digicore.billent.data.lib.modules.common.wallet.dto.TopUpWalletDTO")

    @Override
    public Object creditWallet(Object request, Object... args) {
        TopUpWalletDTO topUpWalletDTO = (TopUpWalletDTO) request;
        walletServiceImpl.topUpWallet(topUpWalletDTO);
        auditLogProcessor.saveAuditWithDescription(
                AuditLogActivity.APPROVE_CREDIT_WALLET_POSITION,
                AuditLogActivity.BACKOFFICE,
                AuditLogActivity.APPROVE_CREDIT_WALLET_POSITION_DESCRIPTION.replace("{}", topUpWalletDTO.getSystemWalletID()));
        return Optional.empty();
    }
}
