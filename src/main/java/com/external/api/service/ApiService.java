package com.external.api.service;

import com.external.api.domain.Composition;
import com.external.api.domain.Snapshot;
import com.external.api.domain.Withdrawal;
import com.external.api.dto.TraineePortfolioDetailResponseDTO;
import com.external.api.mapper.UserAssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class ApiService {

    @Autowired
    private UserAssetMapper userAssetMapper;

    @Transactional
    public TraineePortfolioDetailResponseDTO getAllAssetData(BigInteger userId) {
        List<Snapshot> snapshots = userAssetMapper.findSnapshotsByUserId(userId);
        List<Withdrawal> withdrawals = userAssetMapper.findWithdrawalsByUserId(userId);
        Composition composition = userAssetMapper.findCompositionByUserId(userId);

        return new TraineePortfolioDetailResponseDTO(snapshots, withdrawals, composition);
    }
}
