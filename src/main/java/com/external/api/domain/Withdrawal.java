package com.external.api.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class Withdrawal {
    private Long transactionId;              // PK
    private Long userId;                     // FK
    private BigInteger amount;               // 출금 금액
    private WithdrawalCategory transactionCategory; // 출금 카테고리 (ENUM)
    private LocalDateTime tranDate;          // 출금 일시

    public static Withdrawal createWithdrawal(
            Long transactionId,
            Long userId,
            BigInteger amount,
            WithdrawalCategory transactionCategory,
            LocalDateTime tranDate
    ) {
        return Withdrawal.builder()
                .transactionId(transactionId)
                .userId(userId)
                .amount(amount)
                .transactionCategory(transactionCategory)
                .tranDate(tranDate)
                .build();
    }
}
