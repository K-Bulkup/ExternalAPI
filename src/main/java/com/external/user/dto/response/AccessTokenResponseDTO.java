package com.external.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponseDTO {
    private String accessToken;

    public static AccessTokenResponseDTO create(String accessToken) {
        return AccessTokenResponseDTO.builder()
                .accessToken(accessToken).
                build();
    }
}
