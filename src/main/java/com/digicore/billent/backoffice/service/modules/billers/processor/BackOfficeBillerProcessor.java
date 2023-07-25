package com.digicore.billent.backoffice.service.modules.billers.processor;

import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeService;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;
/*
 * @author Joy Osayi
 * @createdOn Jul-03(Mon)-2023
 */
@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BackOfficeBillerProcessor {
    private final BillerBackOfficeService billerBackOfficeService;

    @RequestType(name = "enableBiller")
    public Object enableBiller(Object request){
        return billerBackOfficeService.enableBiller(request);
    }
    @RequestType(name = "disableBiller")
    public Object disableBiller(Object request){
        return billerBackOfficeService.disableBiller(request);
    }
    @RequestType(name = "updateBillerDetail")
    public Object updateBillerDetail(Object request){
        return billerBackOfficeService.updateBillerDetail(request);
    }

}