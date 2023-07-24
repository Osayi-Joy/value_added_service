package com.digicore.billent.backoffice.service.modules.billers.processor;

import com.digicore.billent.backoffice.service.modules.billers.service.BillerBackOfficeService;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BackOfficeBillerProcessor {
    private final BillerBackOfficeService billerBackOfficeService;

    @RequestType(name = "updateBiller")
    public Object updateBiller(Object request){
        return billerBackOfficeService.updateBiller(request);
    }

}