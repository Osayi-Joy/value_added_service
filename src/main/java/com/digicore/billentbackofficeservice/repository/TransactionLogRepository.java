package com.digicore.billentbackofficeservice.repository;

import com.digicore.billentbackofficeservice.domain.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    List<TransactionLog> findTransactionLogsByCustomerIdOrOrderByTransactionDateDesc(String customerId, Pageable pageable);

    TransactionLog findTransactionLogByCustomerIdAndAndReference(String customerId, String reference);
}
