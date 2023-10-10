package com.digicore.billent.backoffice.service.modules.products.service;
/*
 * @author Joy Osayi
 * @createdOn Jul-26(Wed)-2023
 */
public interface ProductBackOfficeValidatorService {
    Object enableProduct(Object request, Object... args);
    Object disableProduct(Object request, Object... args);
}
