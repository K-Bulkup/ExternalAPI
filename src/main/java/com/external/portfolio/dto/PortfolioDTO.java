package com.external.portfolio.dto;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Withdrawal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PortfolioDTO {
    private List<Snapshot> snapshots;
    private List<Withdrawal> withdrawals;
    private Composition composition;
}
