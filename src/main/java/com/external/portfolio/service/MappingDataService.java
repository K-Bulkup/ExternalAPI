package com.external.portfolio.service;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Transaction;
import com.external.portfolio.domain.TransactionType;
import com.external.portfolio.mapper.UserAssetMapper;
import com.external.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MappingDataService {

    private final UserAssetMapper userAssetMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public void mapUserAsset(String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        List<Transaction> transactions = userAssetMapper.findTransactionByUserId(userId);
        List<Snapshot> snapshots = new ArrayList<>();

        long balance = 10_000_000L;

        for (Transaction tx : transactions) {
            if (tx.getTransactionType() == TransactionType.입금 ) {
                balance += tx.getAmount();
            } else {
                balance -= tx.getAmount();
            }

            snapshots.add(Snapshot.create(userId, balance, tx.getTranDate()));
        }
        userAssetMapper.assignSnapshotPools(userId, snapshots);

        Composition composition = userAssetMapper.pickNewComposition();
        userAssetMapper.assignCompositionPool(userId, composition);
    }

}
