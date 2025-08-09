package com.external.portfolio.mapper;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Transaction;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserAssetMapper {
     void assignSnapshotPools(@Param("userId") Long userId,
                             @Param("snapshots") List<Snapshot> snapshots);

     void assignCompositionPool(@Param("userId") Long userId, @Param("composition") Composition composition);

    void assignTransactionPools(@Param("userId") Long userId, @Param("transactions") List<Transaction> transactions);

    List<Snapshot> findSnapshotsByUserId(Long userId);

    List<Transaction> findTransactionByUserId(@Param("userId") Long userId,
                                              @Param("start") LocalDate start,
                                              @Param("end") LocalDate end);

    Composition findCompositionByUserId(Long userId);

    Composition pickNewComposition();
}
