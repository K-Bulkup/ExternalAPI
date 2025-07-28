package com.external.user.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long userId;
    private String bank;
    private String fintechUseNum;

    public static User create(Long userId, Bank bank, String fintechUseNum) {
        return User.builder()
                .userId(userId)
                .bank(bank.toDbValue())
                .fintechUseNum(fintechUseNum).build();
    }
}
