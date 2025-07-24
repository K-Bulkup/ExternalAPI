package com.external.api.mapper;

import com.external.api.domain.Composition;
import com.external.api.domain.Snapshot;
import com.external.api.domain.Withdrawal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DummyMapper {

    List<Snapshot> pickRandom(@Param("count") int count);

    List<Withdrawal> getAll();

    Composition pickOneRandom();
}
