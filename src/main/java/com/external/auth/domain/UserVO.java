package com.external.auth.domain;

import lombok.*;

import java.math.BigInteger;


@Data
@Builder
public class UserVO {
    private BigInteger userId;
    private String bank;
    private String accountNum;
    private String fintechUseNum;

    public static UserVO createUserVO(BigInteger userId, String accountNum, String bank, String fintechUseNum) {
        return UserVO.builder()
                .userId(userId)
                .bank(bank)
                .accountNum(accountNum)
                .fintechUseNum(fintechUseNum).build();
    }
}
