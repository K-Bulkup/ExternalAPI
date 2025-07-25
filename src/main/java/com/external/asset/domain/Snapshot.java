package com.external.asset.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class Snapshot {
    private Long snapshotId;
    private Long userId;
    private BigInteger balance;
    private LocalDateTime snapshotDate;

    public static Snapshot createSnapshot(Long snapshotId, Long userId, BigInteger balance, LocalDateTime snapshotDate) {
        return Snapshot.builder()
                .snapshotId(snapshotId)
                .userId(userId)
                .balance(balance)
                .snapshotDate(snapshotDate)
                .build();
    }
}
