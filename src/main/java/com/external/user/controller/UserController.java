package com.external.user.controller;

import com.external.portfolio.dto.PortfolioDTO;
import com.external.portfolio.service.PortfolioService;
import com.external.portfolio.service.MappingDataService;
import com.external.user.dto.request.TraineePortfolioCreateRequestDTO;
import com.external.user.dto.AuthDTO;
import com.external.user.dto.response.TraineePortfolioCreateResponseDTO;
import com.external.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mydata")
public class UserController {

    private final UserService userService;
    private final MappingDataService mappingDataService;
    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestHeader(value = "Authorization") String authorization, //로그인 액세스 토큰
                                        @RequestBody TraineePortfolioCreateRequestDTO reqDTO) {
        // 현재 외부api의 액세스 토큰, 리프레시 토큰 발급 X
        // Long userId = authService.getUserId(authorization); 로그인 구현시 활성화
        Long userId = 1L;
        String fintechUseNum = userService.createUser(userId, reqDTO);

        mappingDataService.mapDummyDataToUser(userId);
        PortfolioDTO assetData = portfolioService.getAllAssetData(userId);

        TraineePortfolioCreateResponseDTO resDTO = TraineePortfolioCreateResponseDTO.create(fintechUseNum, assetData);

        return ResponseEntity.ok(resDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken( // 액세스 토큰 갱신 by 리프레시 토큰
            @RequestHeader("Authorization") String refreshHeader,
            @RequestHeader("X-User-Id") Long userId
    ) {
        AuthDTO dto = userService.getNewAccessTokenByUserId(refreshHeader, userId);

        return ResponseEntity.ok(dto);
    }
}
