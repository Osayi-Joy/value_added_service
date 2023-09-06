package com.digicore.billent.backoffice.service.modules.resellers.processor;

import com.digicore.billent.backoffice.service.modules.resellers.service.impl.BackOfficeResellerOperation;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

/**
 * @author Joy Osayi
 * @createdOn Sep-06(Wed)-2023
 */
@RequestHandler(type = RequestHandlerType.PROCESS_MAKER_REQUESTS)
@RequiredArgsConstructor
public class BackOfficeResellerProfileProcessor {
    private final BackOfficeResellerOperation backOfficeResellerOperation;

    @RequestType(name = "enableContributorUser")
    public Object enableContributorUser(Object request) {
        return backOfficeResellerOperation.enableContributorUser(request);
    }
}
