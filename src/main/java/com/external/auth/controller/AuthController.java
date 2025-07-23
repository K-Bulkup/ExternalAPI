package com.external.auth.controller;

import com.external.auth.dto.TokenResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.external.auth.service.AuthService;
import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/api/trainee/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestHeader(value = "X-Fintech-Use-Num", required = false) String fintechUseNum
                                         // @RequestHeader(value = "Authorization") String loginToken
                                         // @RequestBody 로 userID, 은행, 계좌번호 받아오기
    ) {
        TokenResponseDTO dto = null;

        if (!StringUtils.hasText(fintechUseNum)) {
            dto = authService.createFintechUseNum();
        }
        //TODO: 유효성 검증 및 기존 유저 토큰 발급

//        String jwt = token.replace("Bearer ", "");
//        Long userId = jwtUtil.extractUserId(jwt);

        return ResponseEntity.ok(dto);
    }
}
