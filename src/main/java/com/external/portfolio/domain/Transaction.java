package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Transaction {
    private Long transactionId;
    private String fintechUseNum;
    private TransactionType transactionType;
    private Long amount;
    private TransactionCategory transactionCategory;
    private LocalDate tranDate;

    public static Transaction createTransaction(
            Long transactionId,
            String fintechUseNum,
            Long amount,
            TransactionCategory transactionCategory,
            LocalDate tranDate
    ) {
        return Transaction.builder()
                .transactionId(transactionId)
                .fintechUseNum(fintechUseNum)
                .amount(amount)
                .transactionCategory(transactionCategory)
                .tranDate(tranDate)
                .build();
    }
}
