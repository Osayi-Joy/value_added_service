package com.digicore.billentbackofficeservice.repository;

import com.digicore.billentbackofficeservice.domain.TransactionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    Page<TransactionLog> findTransactionLogsByCustomerIdOrderByTransactionDateDesc(String customerId, Pageable pageable);
    @Query("select t from TransactionLog t " +
            "where (lower(concat(t.channel, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.productId, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.productName, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.resellerId, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.resellerName, '')) like lower(concat('%',:key,'%')) or " +
            "lower(concat(t.reference, '')) like lower(concat('%',:key,'%'))) and " +
            "t.customerId = :customerId order by t.transactionDate")
    Page<TransactionLog> findTransactionLogsBySearchKey(@Param("customerId") String customerId, @Param("key") String key , Pageable pageable);

    TransactionLog findTransactionLogByCustomerIdAndReference(String customerId, String reference);
}
