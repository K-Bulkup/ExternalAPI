package com.external.portfolio.controller;

import com.external.portfolio.service.PortfolioService;
import com.external.user.dto.response.TraineePortfolioCreateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external-api")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping("/user-data")
    public ResponseEntity<?> getUserData(@RequestHeader(value = "Authorization") String authorization, @RequestParam("fintechUseNum") String fintechUseNum) { // 외부 API 액세스 토큰
        TraineePortfolioCreateResponseDTO dto = TraineePortfolioCreateResponseDTO.create(fintechUseNum, portfolioService.getAllAssetData(authorization, fintechUseNum));
        return ResponseEntity.ok(dto);
    }
}
