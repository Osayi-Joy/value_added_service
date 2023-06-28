package com.digicore.billentbackofficeservice.service;


import com.digicore.billentbackofficeservice.service.dto.GenericResponseDTO;

import org.springframework.data.domain.Pageable;

public interface TransactionLogService {

    GenericResponseDTO findByCustomerId(String customerId, Pageable pageable);

    GenericResponseDTO findTransactionBySearchKey(String customerId, String key, Pageable pageable);

    GenericResponseDTO findByCustomerIdAndAndReference(String customerId, String reference);
}
