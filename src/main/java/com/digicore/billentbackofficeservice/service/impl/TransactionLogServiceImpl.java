package com.digicore.billentbackofficeservice.service.impl;

import com.digicore.billentbackofficeservice.domain.TransactionLog;
import com.digicore.billentbackofficeservice.repository.TransactionLogRepository;
import com.digicore.billentbackofficeservice.service.TransactionLogService;
import com.digicore.billentbackofficeservice.service.dto.GenericResponseDTO;
import com.digicore.billentbackofficeservice.service.dto.TransactionLogDTO;
import com.digicore.billentbackofficeservice.service.mapper.TransactionLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    private final TransactionLogRepository transactionLogRepository;
    private final TransactionLogMapper transactionLogMapper;

    public TransactionLogServiceImpl(TransactionLogRepository transactionLogRepository,
                                     TransactionLogMapper transactionLogMapper) {
        this.transactionLogRepository = transactionLogRepository;
        this.transactionLogMapper = transactionLogMapper;
    }

    @Override
    public GenericResponseDTO findByCustomerId(String customerId, Pageable pageable) {
        Page<TransactionLog> userTransactions = transactionLogRepository.findTransactionLogsByCustomerIdOrderByTransactionDateDesc(customerId, pageable);
        log.info("User transactions found ==> {}", userTransactions.getContent());

        return getTransactionsPageResult(userTransactions);
    }

    @Override
    public GenericResponseDTO findTransactionBySearchKey(String customerId, String key, Pageable pageable) {
        Page<TransactionLog> userTransactions = transactionLogRepository.findTransactionLogsBySearchKey(customerId, key, pageable);
        log.info("User transactions found by search ==> {}", userTransactions.getContent());

        return getTransactionsPageResult(userTransactions);
    }

    private GenericResponseDTO getTransactionsPageResult(Page<TransactionLog> userTransactions) {
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        if (userTransactions.getContent().isEmpty()) {
            responseDTO.setCode("99");
            responseDTO.setMessage("No transaction found");
            responseDTO.setStatus(HttpStatus.NOT_FOUND);
            return responseDTO;
        }

        List<TransactionLogDTO> userTransactionsDTO = transactionLogMapper.toDto(userTransactions.getContent());
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("size", userTransactions.getSize());
        metadata.put("totalNumberOfRecords", userTransactions.getTotalElements());

        responseDTO.setCode("00");
        responseDTO.setMessage("Successful");
        responseDTO.setStatus(HttpStatus.OK);
        responseDTO.setData(userTransactionsDTO);
        responseDTO.setMetaData(metadata);
        return responseDTO;
    }

    @Override
    public GenericResponseDTO findByCustomerIdAndAndReference(String customerId, String reference) {
        TransactionLog userTransaction = transactionLogRepository.findTransactionLogByCustomerIdAndReference(customerId, reference);
        log.info("User transaction found ==> {}", userTransaction);
        TransactionLogDTO userTransactionsDTO = transactionLogMapper.toDto(userTransaction);

        GenericResponseDTO responseDTO = new GenericResponseDTO();
        if (Objects.isNull(userTransactionsDTO)) {
            responseDTO.setCode("99");
            responseDTO.setMessage("No transaction found");
            responseDTO.setStatus(HttpStatus.NOT_FOUND);
            return responseDTO;
        }

        responseDTO.setCode("00");
        responseDTO.setMessage("Successful");
        responseDTO.setStatus(HttpStatus.OK);
        return responseDTO;
    }
}
