package com.digicore.billent.backoffice.service.modules.wallets.controller;

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.wallets.service.BackOfficeWalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.WALLET_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE_DEFAULT_VALUE;
/*
 * @author Ademiju Taiwo
 * @createdOn Sept-08 Fri)-2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(WALLET_API_V1)
@Tag(name = WALLET_CONTROLLER_TITLE, description = WALLET_CONTROLLER_DESCRIPTION)

public class BackOfficeWalletController {

    private final BackOfficeWalletService backOfficeWalletService;

    @GetMapping("retrieve-{systemWalletId}-balance")
    @PreAuthorize("hasAuthority('view-all-wallet-balances')")
    @Operation(
            summary = WALLET_CONTROLLER_GET_A_WALLET_BALANCE_TITLE,
            description = WALLET_CONTROLLER_GET_A_WALLET_BALANCE_DESCRIPTION)
    public ResponseEntity<Object> viewWalletBalance(@PathVariable String systemWalletId){
            return ControllerResponse.buildSuccessResponse(
            backOfficeWalletService.fetchWalletBalance(systemWalletId), "Retrieved wallet balance successfully");

    }




}
