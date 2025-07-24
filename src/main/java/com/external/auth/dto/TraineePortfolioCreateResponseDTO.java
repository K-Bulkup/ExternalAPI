package com.external.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TraineePortfolioCreateResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String fintechUseNum;

    public static TraineePortfolioCreateResponseDTO from(String accessToken, String refreshToken, String fintechUseNum) {
        return TraineePortfolioCreateResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .fintechUseNum(fintechUseNum).build();
    }
}
