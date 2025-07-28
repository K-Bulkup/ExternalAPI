package com.external.user.dto.request;

import com.external.user.domain.Bank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineePortfolioCreateRequestDTO {
    private Bank bank;
}
