package com.digicore.billent.backoffice.service.modules.products.processor;

import com.digicore.billent.backoffice.service.modules.products.service.ProductBackOfficeService;
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
public class BackOfficeProductProcessor {
    private final ProductBackOfficeService productBackOfficeService;
    @RequestType(name = "enableProduct")
    public Object enableProduct(Object request){
        return productBackOfficeService.enableProduct(request);
    }

    @RequestType(name = "disableProduct")
    public Object disableProduct(Object request){
        return productBackOfficeService.disableProduct(request);
    }

}