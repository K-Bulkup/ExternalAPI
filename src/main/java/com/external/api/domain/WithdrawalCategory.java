package com.external.api.domain;

public enum WithdrawalCategory {
    식비("식비"),
    교통비("교통비"),
    주거_공과금("주거/공과금"),
    생필품("생필품"),
    의료_건강("의료/건강"),
    패션_미용("패션/미용"),
    문화생활_여가("문화생활/여가"),
    기타("기타");

    private final String dbValue;

    WithdrawalCategory(String dbValue) {
        this.dbValue = dbValue;
    }

    public String toDbValue() {
        return dbValue;
    }

    public static WithdrawalCategory fromDbValue(String dbValue) {
        for (WithdrawalCategory category : values()) {
            if (category.dbValue.equals(dbValue)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown DB value: " + dbValue);
    }
}

