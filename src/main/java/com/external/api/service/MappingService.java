package com.external.api.service;

import com.external.api.domain.Composition;
import com.external.api.domain.Snapshot;
import com.external.api.domain.Withdrawal;
import com.external.api.mapper.DummyMapper;
import com.external.api.mapper.UserAssetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class MappingService {

    // 출금 상한 비율
    private static final double WITHDRAWAL_CAP_RATIO = 1.1;

    @Autowired
    private DummyMapper dummyMapper;

    @Autowired
    private UserAssetMapper userAssetMapper;

    @Transactional
    public void mapDummyDataToUser(BigInteger userId) {
        // 1. 자산 추이 가져오기 (랜덤 10~30개)
        int randomCount = ThreadLocalRandom.current().nextInt(10, 31);
        log.info("snapshot randomCount 값: {}", randomCount);
        List<Snapshot> snapshots = dummyMapper.pickRandom(randomCount);
        long minAssetAmount = snapshots.stream()
                .mapToLong(s -> s.getBalance().longValue())
                .min()
                .orElse(0L);

        // 2. 출금 상한 설정
        long withdrawalCap = (long) (minAssetAmount * WITHDRAWAL_CAP_RATIO);

        // 3. 출금 내역 중 유효한 범위만 필터링
        List<Withdrawal> allWithdrawals = dummyMapper.getAll();
        List<Withdrawal> validWithdrawals = pickWithdrawalsWithinCap(allWithdrawals, BigInteger.valueOf(withdrawalCap));

        // 4. 자산 구성 하나 선택
        Composition composition = dummyMapper.pickOneRandom();

        // 5. 매핑 실행
        userAssetMapper.assignSnapshotPools(userId, snapshots);
        userAssetMapper.assignWithdrawalPools(userId, validWithdrawals);
        userAssetMapper.assignCompositionPool(userId, composition);
    }

    private List<Withdrawal> pickWithdrawalsWithinCap(List<Withdrawal> allWithdrawals, BigInteger cap) {
        int randomCount = ThreadLocalRandom.current().nextInt(20, 51);
        log.info("withdrawal randomCount 값: {}", randomCount);
        List<Withdrawal> result = new ArrayList<>();
        BigInteger total = BigInteger.ZERO;

        Collections.shuffle(allWithdrawals);

        for (Withdrawal w : allWithdrawals) {
            BigInteger amount = w.getAmount();

            if (total.add(amount).compareTo(cap) <= 0) {
                result.add(w);
                total = total.add(amount);
            }

            if (total.compareTo(cap) >= 0 || result.size() >= randomCount) {
                break;
            }
        }

        return result;
    }

}
