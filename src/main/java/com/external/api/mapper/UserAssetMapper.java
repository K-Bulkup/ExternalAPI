package com.external.api.mapper;

import com.external.api.domain.Composition;
import com.external.api.domain.Snapshot;
import com.external.api.domain.Withdrawal;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface UserAssetMapper {
     void assignSnapshotPools(@Param("userId") BigInteger userId,
                             @Param("snapshots") List<Snapshot> snapshots);

     void assignWithdrawalPools(@Param("userId") BigInteger userId, @Param("withdrawals") List<Withdrawal> validWithdrawals);

     void assignCompositionPool(@Param("userId") BigInteger userId, @Param("composition") Composition composition);

    List<Snapshot> findSnapshotsByUserId(BigInteger userId);

    List<Withdrawal> findWithdrawalsByUserId(BigInteger userId);

    Composition findCompositionByUserId(BigInteger userId);
}
