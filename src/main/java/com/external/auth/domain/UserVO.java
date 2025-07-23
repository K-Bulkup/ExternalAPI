package com.external.auth.domain;

import lombok.*;

import java.math.BigInteger;


@Data
@NoArgsConstructor
@Builder
public class UserVO {
    private BigInteger userId;
    private String bank;
    private String accountNum;
    private String fintechUseNum;

    private UserVO(BigInteger userId, String accountNum, String bank, String fintechUseNum) {
        this.userId = userId;
        this.accountNum = accountNum;
        this.bank = bank;
        this.fintechUseNum = fintechUseNum;
    }

    public static UserVO of(BigInteger userId, String accountNum, String bank, String fintechUseNum) {
        return new UserVO(userId, accountNum, bank, fintechUseNum);
    }
}
