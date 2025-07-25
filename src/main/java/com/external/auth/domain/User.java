package com.external.auth.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long userId;
    private String bank;
    private String accountNum;
    private String fintechUseNum;

    public static User create(Long userId, String accountNum, Bank bank, String fintechUseNum) {
        return User.builder()
                .userId(userId)
                .bank(bank.toDbValue())
                .accountNum(accountNum)
                .fintechUseNum(fintechUseNum).build();
    }
}
