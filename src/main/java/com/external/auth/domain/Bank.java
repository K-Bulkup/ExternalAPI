package com.external.auth.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Bank {
    KB("국민은행"),
    SINHAN("신한은행"),
    KAKAO("카카오뱅크"),
    TOSS("토스뱅크"),
    NH("농협"),
    WOORI("우리은행"),
    HANA("하나은행"),
    ETC("기타");

    private final String dbValue;

    Bank(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String toDbValue() {
        return dbValue;
    }

    @JsonCreator
    public static Bank fromDbValue(String dbValue) {
        for (Bank bank : values()) {
            if (bank.dbValue.equals(dbValue)) {
                return bank;
            }
        }
        throw new IllegalArgumentException("Unknown DB value: " + dbValue);
    }
}
