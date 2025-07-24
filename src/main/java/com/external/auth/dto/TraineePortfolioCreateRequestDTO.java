package com.external.auth.dto;

import com.external.auth.domain.Bank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraineePortfolioCreateRequestDTO {
    private Long userId;
    private Bank bank;
    private String accountNum;
}
