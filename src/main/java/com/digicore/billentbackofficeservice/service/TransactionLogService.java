package com.digicore.billentbackofficeservice.service;


import com.digicore.billentbackofficeservice.service.dto.GenericResponseDTO;

import java.awt.print.Pageable;

public interface TransactionLogService {

    GenericResponseDTO findByCustomerId(String customerId, Pageable pageable);

    GenericResponseDTO findByCustomerIdAndAndReference(String customerId, String reference);
}
