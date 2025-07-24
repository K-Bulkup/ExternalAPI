package com.external.auth.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenCreateRequestDTO {
    private BigInteger userId;
    private String bank;
    private String accountNum;
}
