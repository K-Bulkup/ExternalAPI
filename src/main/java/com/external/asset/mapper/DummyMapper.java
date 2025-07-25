package com.external.asset.mapper;

import com.external.asset.domain.Composition;
import com.external.asset.domain.Snapshot;
import com.external.asset.domain.Withdrawal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DummyMapper {

    int getMappableSnapshotCount();

    List<Snapshot> pickRandom(@Param("count") int count, @Param("offset") int offset);

    List<Withdrawal> getAll();

    int getMappableWithdrawalCount(@Param("cap") long cap);

    List<Withdrawal> pickRandomWithdrawals(@Param("cap") long cap, @Param("count") int count, @Param("offset") int offset);

    int getMappableCompositionCount();

    Composition pickOneRandom(@Param("offset") int offset);
}
