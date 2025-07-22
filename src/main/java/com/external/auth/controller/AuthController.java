package com.external.auth.controller;

import com.external.auth.dto.TokenResponseDTO;
import com.external.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.external.auth.service.AuthService;
import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/api/trainee/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService assetService;

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestHeader(value = "X-Fintech-Use-Num", required = false) String fintechUseNum
                                         // @RequestHeader(value = "Authorization") String loginToken
                                         // @RequestBody 로 userID, 은행, 계좌번호 받아오기
    ) {
        if (!StringUtils.hasText(fintechUseNum)) {
            fintechUseNum = assetService.issueNewFintechUseNum();
        }

        String token = jwtUtil.generateToken(fintechUseNum);
        TokenResponseDTO responseDTO = new TokenResponseDTO(token, fintechUseNum);

//        String jwt = token.replace("Bearer ", "");
//        Long userId = jwtUtil.extractUserId(jwt);

        return ResponseEntity.ok(responseDTO);
    }
}
