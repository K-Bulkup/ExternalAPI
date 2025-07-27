package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Snapshot {
    private Long snapshotId;
    private Long userId;
    private Long balance;
    private LocalDate snapshotDate;

    public static Snapshot create(Long userId, Long balance, LocalDate snapshotDate) {
        return Snapshot.builder()
                .userId(userId)
                .balance(balance)
                .snapshotDate(snapshotDate)
                .build();
    }
}
