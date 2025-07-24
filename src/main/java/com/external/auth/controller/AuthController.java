package com.external.auth.controller;

import com.external.auth.dto.TraineePortfolioCreateRequestDTO;
import com.external.auth.dto.TraineePortfolioCreateResponseDTO;
import com.external.auth.exception.BadRequestException;
import com.external.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainee/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestHeader(value = "Authorization", required = false) String authorization, // 외부 api 액세스토큰
                                         @RequestBody(required = false) TraineePortfolioCreateRequestDTO reqDTO) {
        TraineePortfolioCreateResponseDTO resDTO;

        if (!StringUtils.hasText(authorization)) {
            if (reqDTO == null || reqDTO.getUserId() == null) {
                throw new BadRequestException("userId가 필요합니다.");
            }

            resDTO = authService.createFintechUseNum(reqDTO.getUserId(), reqDTO.getBank(), reqDTO.getAccountNum());
        } else {
            String token = authorization.replace("Bearer ", "");
            resDTO = authService.validateAndGetUser(token);
        }

        return ResponseEntity.ok(resDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
            @RequestHeader("Authorization") String refreshHeader,
            @RequestHeader("X-User-Id") Long userId
    ) {
        String refreshToken = refreshHeader.replace("Bearer ", "");
        TraineePortfolioCreateResponseDTO dto = authService.getNewAccessTokenByUserId(refreshToken, userId);

        return ResponseEntity.ok(dto);
    }
}
