package com.external.asset.mapper;

import com.external.asset.domain.Composition;
import com.external.asset.domain.Snapshot;
import com.external.asset.domain.Withdrawal;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface UserAssetMapper {
     void assignSnapshotPools(@Param("userId") Long userId,
                             @Param("snapshots") List<Snapshot> snapshots);

     void assignWithdrawalPools(@Param("userId") Long userId, @Param("withdrawals") List<Withdrawal> validWithdrawals);

     void assignCompositionPool(@Param("userId") Long userId, @Param("composition") Composition composition);

    List<Snapshot> findSnapshotsByUserId(Long userId);

    List<Withdrawal> findWithdrawalsByUserId(Long userId);

    Composition findCompositionByUserId(Long userId);
}
