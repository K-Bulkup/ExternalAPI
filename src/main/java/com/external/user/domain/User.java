package com.external.user.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Long userId;
    private String bank;
    private String fintechUseNum;
    private LocalDateTime createdAt;

    public static User create(Long userId, Bank bank, String fintechUseNum) {
        return User.builder()
                .userId(userId)
                .bank(bank.toDbValue())
                .fintechUseNum(fintechUseNum)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
