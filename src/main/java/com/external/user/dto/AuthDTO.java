package com.external.user.dto;

import lombok.*;

@Getter
@Builder
public class AuthDTO { // 액세스, 리프레시 용도
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
