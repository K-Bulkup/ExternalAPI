package com.external.portfolio.domain;

public enum TransactionType {
    입금("입금"),
    출금("출금");

    private final String dbValue;

    TransactionType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String toDbValue() {
        return dbValue;
    }

    public static TransactionType fromDbValue(String dbValue) {
        for (TransactionType type : values()) {
            if (type.dbValue.equals(dbValue)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown DB value: " + dbValue);
    }
}
