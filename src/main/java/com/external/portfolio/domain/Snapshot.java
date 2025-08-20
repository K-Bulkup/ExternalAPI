package com.external.portfolio.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Snapshot {
    private String fintechUseNum;
    private Long balance;
    private LocalDate snapshotDate;

    public static Snapshot create( String fintechUseNum, Long balance, LocalDate snapshotDate) {
        return Snapshot.builder()
                .fintechUseNum(fintechUseNum)
                .balance(balance)
                .snapshotDate(snapshotDate)
                .build();
    }
}
