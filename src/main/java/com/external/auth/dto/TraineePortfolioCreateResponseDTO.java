package com.external.auth.dto;

import com.external.asset.dto.TraineePortfolioDetailResponseDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TraineePortfolioCreateResponseDTO {
    private String fintechUseNum;
    private TraineePortfolioDetailResponseDTO portfolio;

    public static TraineePortfolioCreateResponseDTO create(String fintechUseNum, TraineePortfolioDetailResponseDTO dto) {
        return TraineePortfolioCreateResponseDTO.builder()
                .fintechUseNum(fintechUseNum)
                .portfolio(dto)
                .build();
    }
}
