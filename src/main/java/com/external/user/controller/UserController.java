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
    public ResponseEntity<?> createUser(@RequestHeader("Authorization") String authorization, //로그인 액세스 토큰
                                        @RequestBody TraineePortfolioCreateRequestDTO reqDTO) {
        AuthResponseDTO authResponseDTO = userService.createUser(authorization, reqDTO);

        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/user-data")
    public ResponseEntity<?> getUserData(@RequestHeader(value = "Authorization") String authorization) { // 외부 API 액세스 토큰
        String fintechUseNum = userService.validateUserAuth(authorization);
        mappingDataService.mapUserAsset(authorization);
        PortfolioDTO dto = portfolioService.getAllAssetData(authorization);

        TraineePortfolioCreateResponseDTO resDTO = TraineePortfolioCreateResponseDTO.create(fintechUseNum, dto);
        return ResponseEntity.ok(resDTO);
    }

}
