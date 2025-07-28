package com.external.user.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO { // 액세스, 리프레시 용도
    private String accessToken;
    // private String refreshToken;
    private String fintechUseNum;

    public static AuthResponseDTO create(String accessToken, String fintechUseNum) {
        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                // .refreshToken(refreshToken)
                .fintechUseNum(fintechUseNum).build();
    }
}
