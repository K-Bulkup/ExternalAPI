package com.external.portfolio.service;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Withdrawal;
import com.external.portfolio.dto.PortfolioDTO;
import com.external.portfolio.mapper.UserAssetMapper;
import com.external.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final UserAssetMapper userAssetMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public PortfolioDTO getAllAssetData(String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        List<Snapshot> snapshots = userAssetMapper.findSnapshotsByUserId(userId);
        List<Withdrawal> withdrawals = userAssetMapper.findWithdrawalsByUserId(userId);
        Composition composition = userAssetMapper.findCompositionByUserId(userId);

        return new PortfolioDTO(snapshots, withdrawals, composition);
    }
}
