//package com.digicore.billent.backoffice.service.modules.billers.service;
//
//import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeValidatorService;
//import com.digicore.billent.data.lib.modules.billers.dto.BillerDto;
//import com.digicore.billent.data.lib.modules.billers.model.Biller;
//import com.digicore.billent.data.lib.modules.billers.service.BillerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class BillerBackOfficeProxyService {
//    private final BillerService<BillerDto, Biller> billerService;
//    private final BillerBackOfficeValidatorService validatorService;
//
//    public Object enableBiller(String billerSystemId){
//        BillerDto billerDto = new BillerDto();
//        billerDto.setBillerSystemId(billerSystemId);
//        billerService.isBillerPresent(billerDto.getBillerSystemId());
//        return validatorService.enableBiller(billerDto);
//    }
//
//    public Object disableBiller(String billerSystemId){
//        BillerDto billerDto = new BillerDto();
//        billerDto.setBillerSystemId(billerSystemId);
//        billerService.isBillerPresent(billerDto.getBillerSystemId());
//        return validatorService.disableBiller(billerDto);
//    }
//    public Object updateBillerDetail(BillerDto billerDto){
//        billerService.isBillerPresent(billerDto.getBillerSystemId());
//        return validatorService.updateBillerDetail(billerDto);
//    }
//
//}
