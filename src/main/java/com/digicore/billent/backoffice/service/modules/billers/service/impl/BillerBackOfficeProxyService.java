package com.digicore.billent.backoffice.service.modules.billers.service.impl;

import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeValidatorService;
import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
import com.digicore.billent.data.lib.modules.billers.model.Biller;
import com.digicore.billent.data.lib.modules.billers.service.BillerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillerBackOfficeProxyService {
    private final BillerService<BillerDto, Biller> billerService;
    private final BillerBackOfficeValidatorService validatorService;

    public Object enableBiller(BillerDto billerDto){
        billerService.isBillerPresent(billerDto.getBillerSystemId());
        return validatorService.enableBiller(billerDto);
    }

    public Object disableBiller(BillerDto billerDto){
        billerService.isBillerPresent(billerDto.getBillerSystemId());
        return validatorService.disableBiller(billerDto);
    }

}