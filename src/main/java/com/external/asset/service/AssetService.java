package com.external.asset.service;

import com.external.asset.domain.Composition;
import com.external.asset.domain.Snapshot;
import com.external.asset.domain.Withdrawal;
import com.external.asset.dto.TraineePortfolioDetailResponseDTO;
import com.external.asset.mapper.UserAssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private UserAssetMapper userAssetMapper;

    @Transactional
    public TraineePortfolioDetailResponseDTO getAllAssetData(Long userId) {
        List<Snapshot> snapshots = userAssetMapper.findSnapshotsByUserId(userId);
        List<Withdrawal> withdrawals = userAssetMapper.findWithdrawalsByUserId(userId);
        Composition composition = userAssetMapper.findCompositionByUserId(userId);

        return new TraineePortfolioDetailResponseDTO(snapshots, withdrawals, composition);
    }
}
