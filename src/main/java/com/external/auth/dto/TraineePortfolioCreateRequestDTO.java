package com.external.auth.dto;

import com.external.auth.domain.Bank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineePortfolioCreateRequestDTO {
    private Bank bank;
    private String accountNum;
}
