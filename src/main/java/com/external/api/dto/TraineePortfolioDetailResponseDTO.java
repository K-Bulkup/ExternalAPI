package com.external.api.dto;

import com.external.api.domain.Composition;
import com.external.api.domain.Snapshot;
import com.external.api.domain.Withdrawal;
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
