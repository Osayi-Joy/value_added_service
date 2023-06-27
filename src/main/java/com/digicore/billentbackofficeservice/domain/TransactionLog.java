package com.digicore.billentbackofficeservice.domain;

import com.digicore.billentbackofficeservice.domain.enumeration.TransactionChannel;
import com.digicore.billentbackofficeservice.domain.enumeration.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_log")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    @Column(name = "reseller_id")
    private String resellerId;
    @Column(name = "reseller_name")
    private String resellerName;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "customer_id")
    private String customerId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;
    @Column(name = "channel")
    private TransactionChannel channel;
    @Column(name = "reference")
    private String reference;
}
