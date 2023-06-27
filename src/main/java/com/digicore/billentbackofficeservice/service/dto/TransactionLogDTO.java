package com.digicore.billentbackofficeservice.service.dto;

import com.digicore.billentbackofficeservice.domain.enumeration.TransactionChannel;
import com.digicore.billentbackofficeservice.domain.enumeration.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionLogDTO {
    private Long id;
    private LocalDateTime transactionDate;
    private String resellerId;
    private String resellerName;
    private String productName;
    private String productId;
    private BigDecimal amount;
    private String customerId;
    private TransactionStatus status;
    private TransactionChannel channel;
    private String reference;
}
