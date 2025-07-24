package com.external.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class TokenCreateResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String fintechUseNum;
}
