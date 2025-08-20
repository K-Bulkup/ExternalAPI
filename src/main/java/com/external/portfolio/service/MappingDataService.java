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

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MappingDataService {

    private final UserAssetMapper userAssetMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public void mapUserAsset(String fintechUseNum) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(3).withDayOfMonth(1);
        List<Transaction> transactions = userAssetMapper.findTransactionByfintechUseNum(fintechUseNum, start, end);
        // 1) 거래 정렬 (시간 오름차순)
        transactions.sort(Comparator.comparing(Transaction::getTranDate));

        // 2) 날짜별 그룹핑 (TreeMap으로 날짜 오름차순 보장)
        Map<LocalDate, List<Transaction>> byDate = transactions.stream()
                .collect(Collectors.groupingBy(
                        tx -> tx.getTranDate(),
                        TreeMap::new,
                        Collectors.toList()
                ));

        // 3) 일자별로 모두 반영 → 그 날짜의 '마감 잔액' 스냅샷 1개 생성
        List<Snapshot> snapshots = new ArrayList<>();
        long balance = 10_000_000L; // 시작 잔액(초기값)

        for (Map.Entry<LocalDate, List<Transaction>> e : byDate.entrySet()) {
            // 같은 날짜 내부에서도 시간이 있으면 정렬(안전)
            e.getValue().sort(Comparator.comparing(Transaction::getTranDate));

            for (Transaction tx : e.getValue()) {
                if (tx.getTransactionType() == TransactionType.입금) {
                    balance += tx.getAmount();
                } else {
                    balance -= tx.getAmount();
                }
            }

            // 날짜의 '마감 잔액'으로 스냅샷 1건 (타임스탬프는 팀 규칙대로)
            LocalDate date = e.getKey();
            snapshots.add(Snapshot.create(fintechUseNum, balance, date));
        }
        userAssetMapper.assignSnapshotPools(fintechUseNum, snapshots);

    }

}
