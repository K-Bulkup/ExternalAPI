package com.external.user.controller;

import com.external.portfolio.dto.PortfolioDTO;
import com.external.portfolio.service.PortfolioService;
import com.external.portfolio.service.MappingDataService;
import com.external.user.dto.request.TraineePortfolioCreateRequestDTO;
import com.external.user.dto.response.AuthResponseDTO;
import com.external.user.dto.response.TraineePortfolioCreateResponseDTO;
import com.external.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external-api")
public class UserController {

    private final UserService userService;
    private final MappingDataService mappingDataService;
    private final PortfolioService portfolioService;

    @PostMapping("/token")
    public ResponseEntity<?> createUser(@RequestHeader(value = "Authorization") String authorization, //로그인 액세스 토큰
                                        @RequestBody TraineePortfolioCreateRequestDTO reqDTO) {

        //액세스 토큰, 리프레시 토큰 발급
        // 추후에 데이터 추가 생각해보기
        AuthResponseDTO authResponseDTO = userService.createUser(authorization, reqDTO); // Long userId = authService.getUserId(authorization); 로그인 구현시 활성화

        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/user-data")
    public ResponseEntity<?> createUserData(@RequestHeader(value = "Authorization") String authorization) { // 외부 API 액세스 토큰
        String fintechUseNum = userService.validateUserAuth(authorization);
        mappingDataService.mapDummyDataToUser(authorization);
        PortfolioDTO dto = portfolioService.getAllAssetData(authorization);

        TraineePortfolioCreateResponseDTO resDTO = TraineePortfolioCreateResponseDTO.create(fintechUseNum, dto);
        return ResponseEntity.ok(resDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken( // 액세스 토큰 갱신 by 리프레시 토큰
                                                 @RequestHeader("Authorization") String refreshHeader,
                                                 @RequestHeader("X-User-Id") Long userId
    ) {
        AuthResponseDTO dto = userService.getNewAccessTokenByUserId(refreshHeader, userId);

        return ResponseEntity.ok(dto);
    }
}
