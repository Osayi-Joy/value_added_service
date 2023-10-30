package com.digicore.billent.backoffice.service.modules.resellers.service;

public interface BackOfficeResellerOperationValidatorService {
    Object disableResellerUser(Object requestDTO, Object... args);
    Object enableResellerUser(Object requestDTO, Object... args);
}
