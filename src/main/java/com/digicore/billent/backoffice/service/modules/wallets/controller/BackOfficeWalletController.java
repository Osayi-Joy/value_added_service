package com.digicore.billent.backoffice.service.modules.wallets.controller;

/*
 * @author Ademiju Taiwo
 * @createdOn Sep-07(Thur)-2023
 */

import com.digicore.api.helper.response.ControllerResponse;
import com.digicore.billent.backoffice.service.modules.wallets.service.BackOfficeWalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.digicore.billent.backoffice.service.util.BackOfficeUserServiceApiUtil.WALLET_API_V1;
import static com.digicore.billent.backoffice.service.util.SwaggerDocUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.*;
import static com.digicore.billent.data.lib.modules.common.util.PageableUtil.PAGE_SIZE_DEFAULT_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(WALLET_API_V1)
@Tag(name = WALLET_CONTROLLER_TITLE, description = WALLET_CONTROLLER_DESCRIPTION)

public class BackOfficeWalletController {

    BackOfficeWalletService backOfficeWalletService;

    @GetMapping("get-all")
    @PreAuthorize("hasAuthority('view-wallet-balance')")
    @Operation(
            summary = WALLET_CONTROLLER_GET_ALL_WALLET_TITLE,
            description = WALLET_CONTROLLER_GET_ALL_WALLET_DESCRIPTION)
    public ResponseEntity<Object> viewAllWallets(
            @RequestParam(value = PAGE_NUMBER, defaultValue = PAGE_NUMBER_DEFAULT_VALUE, required = false)
            int pageNumber,
            @RequestParam(value = PAGE_SIZE, defaultValue = PAGE_SIZE_DEFAULT_VALUE, required = false)
            int pageSize) {
        return ControllerResponse.buildSuccessResponse(
                backOfficeWalletService.getAllWallets(pageNumber, pageSize),
                "Retrieved all wallets successfully");
    }




}
