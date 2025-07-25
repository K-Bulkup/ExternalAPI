package com.external.auth.dto;

import lombok.*;

@Getter
@Builder
public class AuthDTO {
    private String accessToken;
    private String refreshToken;
    private String fintechUseNum;

    public static AuthDTO create(String accessToken, String refreshToken, String fintechUseNum) {
        return AuthDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .fintechUseNum(fintechUseNum).build();
    }
}
