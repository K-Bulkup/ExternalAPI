package com.external.auth.dto;

import lombok.*;

@Getter
@Builder
public class TraineePortfolioCreateResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String fintechUseNum;

    public static TraineePortfolioCreateResponseDTO create(String accessToken, String refreshToken, String fintechUseNum) {
        return TraineePortfolioCreateResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .fintechUseNum(fintechUseNum).build();
    }
}
