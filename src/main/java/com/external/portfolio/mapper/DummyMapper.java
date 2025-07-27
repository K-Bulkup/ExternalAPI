package com.external.portfolio.mapper;

import com.external.portfolio.domain.Composition;
import com.external.portfolio.domain.Snapshot;
import com.external.portfolio.domain.Transaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DummyMapper {

    int getMappableCompositionCount();

    Composition pickOneRandom(@Param("offset") int offset);

    List<Transaction> pickRandomTransactions(@Param("count") int count, @Param("offset") int offset);

    int getMappableTransactionCount();
}
