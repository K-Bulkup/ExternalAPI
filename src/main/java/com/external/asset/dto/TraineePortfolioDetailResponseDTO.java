package com.external.asset.dto;

import com.external.asset.domain.Composition;
import com.external.asset.domain.Snapshot;
import com.external.asset.domain.Withdrawal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TraineePortfolioDetailResponseDTO {
    private List<Snapshot> snapshots;
    private List<Withdrawal> withdrawals;
    private Composition composition;
}
