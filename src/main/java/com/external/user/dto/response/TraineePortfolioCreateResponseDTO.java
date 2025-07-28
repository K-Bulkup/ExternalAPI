package com.external.user.dto.response;

import com.external.portfolio.dto.PortfolioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
