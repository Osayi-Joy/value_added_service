package com.digicore.billent.backoffice.service.modules.aggregators.service;



/**
 * @author Ezenwa Opara
 * @createdOn 15/08/2023
 */
public interface BillerAggregatorBackOfficeValidatorService {
    Object enableBillerAggregator(Object request, Object... args);

    Object disableBillerAggregator(Object request, Object... args);

    Object updateBillerAggregatorDetail(Object request, Object... args);
}
