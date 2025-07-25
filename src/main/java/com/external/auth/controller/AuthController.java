package com.external.auth.controller;

import com.external.asset.dto.TraineePortfolioDetailResponseDTO;
import com.external.asset.service.AssetService;
import com.external.asset.service.MappingService;
import com.external.auth.dto.TraineePortfolioCreateRequestDTO;
import com.external.auth.dto.AuthDTO;
import com.external.auth.dto.TraineePortfolioCreateResponseDTO;
import com.external.auth.exception.BadRequestException;
import com.external.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mydata")
public class AuthController {

    private final AuthService authService;
    private final MappingService mappingService;
    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestHeader(value = "Authorization") String authorization, //로그인 액세스 토큰
                                        @RequestBody TraineePortfolioCreateRequestDTO reqDTO) {
        // 현재 외부api의 액세스 토큰, 리프레시 토큰 발급 X
        // Long userId = authService.getUserId(authorization); 로그인 구현시 활성화
        Long userId = 1L;
        String fintechUseNum = authService.createUser(userId, reqDTO);

        mappingService.mapDummyDataToUser(userId);
        TraineePortfolioDetailResponseDTO assetData = assetService.getAllAssetData(userId);

        TraineePortfolioCreateResponseDTO resDTO = TraineePortfolioCreateResponseDTO.create(fintechUseNum, assetData);

        return ResponseEntity.ok(resDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken( // 액세스 토큰 갱신 by 리프레시 토큰
            @RequestHeader("Authorization") String refreshHeader,
            @RequestHeader("X-User-Id") Long userId
    ) {
        String refreshToken = refreshHeader.replace("Bearer ", "");
        AuthDTO dto = authService.getNewAccessTokenByUserId(refreshToken, userId);

        return ResponseEntity.ok(dto);
    }
}
