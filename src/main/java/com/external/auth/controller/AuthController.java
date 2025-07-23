package com.external.auth.controller;

import com.external.auth.domain.UserVO;
import com.external.auth.dto.TokenRequestDTO;
import com.external.auth.dto.TokenResponseDTO;
import com.external.auth.exception.BadRequestException;
import com.external.auth.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.external.auth.service.AuthService;
import org.springframework.util.StringUtils;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/trainee/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestHeader(value = "Authorization", required = false) String authorization,
                                         @RequestBody(required = false) TokenRequestDTO reqDTO) {
        TokenResponseDTO dto;

        if (!StringUtils.hasText(authorization)) {
            if (reqDTO == null || reqDTO.getUserId() == null) {
                throw new BadRequestException("userId가 필요합니다.");
            }

            dto = authService.createFintechUseNum(reqDTO.getUserId(), reqDTO.getBank(), reqDTO.getAccountNum());
        } else {
            String token = authorization.replace("Bearer ", "");
            dto = authService.validateAndGetUser(token);
        }

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
            @RequestHeader("Authorization") String refreshHeader,
            @RequestHeader("X-User-Id") BigInteger userId
    ) {
        String refreshToken = refreshHeader.replace("Bearer ", "");
        TokenResponseDTO dto = authService.getNewAccessTokenByUserId(refreshToken, userId);

        return ResponseEntity.ok(dto);
    }
}
