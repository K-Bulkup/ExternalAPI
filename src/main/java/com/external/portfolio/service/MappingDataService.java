package com.external.portfolio.service;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Transaction;
import com.external.portfolio.domain.TransactionType;
import com.external.portfolio.mapper.DummyMapper;
import com.external.portfolio.mapper.UserAssetMapper;
import com.external.user.service.UserService;
import com.external.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class MappingDataService {

    // 출금 상한 비율
    private static final double WITHDRAWAL_CAP_RATIO = 1.1;

    private final UserService userService;
    private final DummyMapper dummyMapper;
    private final UserAssetMapper userAssetMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public void mapDummyDataToUser(String authorization) {

        Long userId = jwtUtil.getUserId(authorization);
        userService.validateUserAuth(authorization);

        final int COUNT = ThreadLocalRandom.current().nextInt(10, 21);
        final int OFFSET = ThreadLocalRandom.current().nextInt(0, dummyMapper.getMappableTransactionCount() - COUNT + 1);

        List<Transaction> transactions = dummyMapper.pickRandomTransactions(COUNT, OFFSET);
        userAssetMapper.assignTransactionPools(userId, transactions);

        transactions.sort(Comparator.comparing(Transaction::getTranDate));
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

        int mappableCompositionCount = dummyMapper.getMappableCompositionCount();
        int randomCompositionOffset = ThreadLocalRandom.current().nextInt(0, mappableCompositionCount);
        Composition composition = dummyMapper.pickOneRandom(randomCompositionOffset);
        userAssetMapper.assignCompositionPool(userId, composition);
    }

}
