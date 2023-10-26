package com.digicore.billent.backoffice.service.modules.resellers.processor;

import com.digicore.billent.backoffice.service.modules.resellers.service.BackOfficeResellerOperation;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BackOfficeResellerOperationProcessor {
    private final BackOfficeResellerOperation backOfficeResellerOperation;

    @RequestType(name = "disableResellerUser")
    public Object disableResellerUser(Object request) {
        return backOfficeResellerOperation.disableResellerUser(request);
    }

    @RequestType(name = "enableResellerUser")
    public Object enableResellerUser(Object request) {
        return backOfficeResellerOperation.enableResellerUser(request);
    }
}
