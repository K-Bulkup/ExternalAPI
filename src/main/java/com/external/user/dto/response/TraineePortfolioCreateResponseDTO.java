package com.external.user.dto.response;

import com.external.portfolio.dto.PortfolioDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TraineePortfolioCreateResponseDTO {
    private String fintechUseNum;
    private PortfolioDTO portfolio;

    public static TraineePortfolioCreateResponseDTO create(String fintechUseNum, PortfolioDTO dto) {
        return TraineePortfolioCreateResponseDTO.builder()
                .fintechUseNum(fintechUseNum)
                .portfolio(dto)
                .build();
    }
}
