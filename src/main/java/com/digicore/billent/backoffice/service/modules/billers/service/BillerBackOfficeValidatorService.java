package com.digicore.billent.backoffice.service.modules.billers.service;

public interface BillerBackOfficeValidatorService {
    Object enableBiller(Object request, Object... args);
    Object disableBiller(Object request, Object... args);
    Object updateBillerDetail(Object request, Object... args);
}
