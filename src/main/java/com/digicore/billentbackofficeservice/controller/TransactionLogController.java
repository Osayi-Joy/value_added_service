package com.digicore.billentbackofficeservice.controller;

import com.digicore.billentbackofficeservice.service.TransactionLogService;
import com.digicore.billentbackofficeservice.service.dto.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/transaction-log")
public class TransactionLogController {

    private final TransactionLogService transactionLogService;

    public TransactionLogController(TransactionLogService transactionLogService) {
        this.transactionLogService = transactionLogService;
    }

    @GetMapping("/all-user-transactions/{customerId}")
    public ResponseEntity<GenericResponseDTO> findAllUserTransaction(@PathVariable("customerId") String customerId, Pageable pageable) {
        GenericResponseDTO responseDTO = transactionLogService.findByCustomerId(customerId, pageable);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }

    @GetMapping("/search-user-transactions")
    public ResponseEntity<GenericResponseDTO> findAllUserTransaction(@RequestParam("customerId") String customerId,
                                                                     @RequestParam("key") String key,
                                                                     Pageable pageable) {
        GenericResponseDTO responseDTO = transactionLogService.findTransactionBySearchKey(customerId, key, pageable);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }

    @GetMapping("/user-transaction/{customerId}/{reference}")
    public ResponseEntity<GenericResponseDTO> findUserTransaction(@PathVariable("reference") String reference, @PathVariable("customerId") String customerId) {
        GenericResponseDTO responseDTO = transactionLogService.findByCustomerIdAndAndReference(customerId, reference);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }
}
