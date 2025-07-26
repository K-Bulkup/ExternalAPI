package com.external.portfolio.service;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Withdrawal;
import com.external.portfolio.dto.PortfolioDTO;
import com.external.portfolio.mapper.UserAssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private UserAssetMapper userAssetMapper;

    @Transactional
    public PortfolioDTO getAllAssetData(Long userId) {
        List<Snapshot> snapshots = userAssetMapper.findSnapshotsByUserId(userId);
        List<Withdrawal> withdrawals = userAssetMapper.findWithdrawalsByUserId(userId);
        Composition composition = userAssetMapper.findCompositionByUserId(userId);

        return new PortfolioDTO(snapshots, withdrawals, composition);
    }
}
