package com.external.portfolio.mapper;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Transaction;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserAssetMapper {
     void assignSnapshotPools(@Param("fintechUseNum") String fintechUseNum,
                             @Param("snapshots") List<Snapshot> snapshots);

    void assignTransactionPools(@Param("userId") Long userId, @Param("transactions") List<Transaction> transactions);

    List<Snapshot> findSnapshotsByfintechUseNum(@Param("fintechUseNum") String fintechUseNum);

    List<Transaction> findTransactionByfintechUseNum(@Param("fintechUseNum") String fintechUseNum,
                                                     @Param("start") LocalDate start,
                                                     @Param("end") LocalDate end);

    Composition findCompositionByfintechUseNum(@Param("fintechUseNum") String fintechUseNum);
}
