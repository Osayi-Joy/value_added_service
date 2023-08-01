package com.digicore.billent.backoffice.service.modules.billers.service;
/*
 * @author Joy Osayi
 * @createdOn Jul-26(Wed)-2023
 */
public interface BillerBackOfficeValidatorService {
    Object enableBiller(Object request, Object... args);
    Object disableBiller(Object request, Object... args);
}
