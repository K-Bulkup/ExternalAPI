package com.external.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Bank {
    국민은행("국민은행"),
    신한은행("신한은행"),
    기업은행("기업은행"),
    농협은행("농협은행"),
    우리은행("우리은행"),
    하나은행("하나은행");

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
