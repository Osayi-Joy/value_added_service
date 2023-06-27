package com.digicore.billentbackofficeservice.repository;

import com.digicore.billentbackofficeservice.domain.TransactionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    Page<TransactionLog> findTransactionLogsByCustomerIdOrOrderByTransactionDateDesc(String customerId, Pageable pageable);
    @Query("select t from TransactionLog t " +
            "where (lower(concat(t.channel, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.productId, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.productName, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.resellerId, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.resellerName, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.reference, '')) like lower(concat('%',:key,'%'))) and " +
            "t.customerId = :custormerId order by t.transactionDate")
    Page<TransactionLog> findTransactionLogsBySearchKey(@Param("customerId") String customerId, @Param("key") String key , Pageable pageable);

    TransactionLog findTransactionLogByCustomerIdAndAndReference(String customerId, String reference);
}
