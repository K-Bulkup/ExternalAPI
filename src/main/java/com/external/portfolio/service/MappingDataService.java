package com.external.portfolio.service;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Withdrawal;
import com.external.portfolio.mapper.DummyMapper;
import com.external.portfolio.mapper.UserAssetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class MappingDataService {

    // 출금 상한 비율
    private static final double WITHDRAWAL_CAP_RATIO = 1.1;

    @Autowired
    private DummyMapper dummyMapper;

    @Autowired
    private UserAssetMapper userAssetMapper;

    @Transactional
    public void mapDummyDataToUser(Long userId) {
        // 1. 자산 추이 가져오기 (랜덤 10~30개)
        int mappableSnapshotCount = dummyMapper.getMappableSnapshotCount();
        int randomSnapshotCount = ThreadLocalRandom.current().nextInt(10, 31);
        int randomSnapshotOffset = ThreadLocalRandom.current().nextInt(0, mappableSnapshotCount - randomSnapshotCount + 1);
        List<Snapshot> snapshots = dummyMapper.pickRandom(randomSnapshotCount, randomSnapshotOffset);
        long minAssetAmount = snapshots.stream()
                .mapToLong(s -> s.getBalance().longValue())
                .min()
                .orElse(0L);

        // 2. 출금 상한 설정
        long withdrawalCap = (long) (minAssetAmount * WITHDRAWAL_CAP_RATIO);

        // 3. 출금 내역 가져오기 (랜덤 20~50개)
        int mappableWithdrawalCount = dummyMapper.getMappableWithdrawalCount(withdrawalCap);
        int randomWithdrawalCount = ThreadLocalRandom.current().nextInt(20, 51);
        int randomWithdrawalOffset = ThreadLocalRandom.current().nextInt(0, mappableWithdrawalCount - randomWithdrawalCount + 1);
        List<Withdrawal> validWithdrawals = dummyMapper.pickRandomWithdrawals(withdrawalCap, randomWithdrawalCount, randomWithdrawalOffset);

        // 4. 자산 구성 하나 선택
        int mappableCompositionCount = dummyMapper.getMappableCompositionCount();
        int randomCompositionOffset = ThreadLocalRandom.current().nextInt(0, mappableCompositionCount);
        Composition composition = dummyMapper.pickOneRandom(randomCompositionOffset);

        // 5. 매핑 실행
        userAssetMapper.assignSnapshotPools(userId, snapshots);
        userAssetMapper.assignWithdrawalPools(userId, validWithdrawals);
        userAssetMapper.assignCompositionPool(userId, composition);
    }

}
