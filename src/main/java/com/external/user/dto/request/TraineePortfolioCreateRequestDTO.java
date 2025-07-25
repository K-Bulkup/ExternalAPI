package com.external.user.dto.request;

import com.external.user.domain.Bank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineePortfolioCreateRequestDTO {
    private Bank bank;
    private String accountNum;
}
