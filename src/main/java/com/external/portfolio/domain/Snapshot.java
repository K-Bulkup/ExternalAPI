package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class Snapshot {
    private Long snapshotId;
    private Long userId;
    private BigInteger balance;
    private LocalDate snapshotDate;

    public static Snapshot createSnapshot(Long snapshotId, Long userId, BigInteger balance, LocalDate snapshotDate) {
        return Snapshot.builder()
                .snapshotId(snapshotId)
                .userId(userId)
                .balance(balance)
                .snapshotDate(snapshotDate)
                .build();
    }
}
