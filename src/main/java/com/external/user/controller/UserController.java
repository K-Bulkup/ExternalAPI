package com.external.user.controller;

import com.external.user.dto.request.TraineePortfolioCreateRequestDTO;
import com.external.user.dto.response.AccessTokenResponseDTO;
import com.external.user.dto.response.AuthResponseDTO;
import com.external.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external-api")
public class UserController {

    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestHeader("Authorization") String authorization, //로그인 액세스 토큰
                                        @RequestBody TraineePortfolioCreateRequestDTO reqDTO) {
        AuthResponseDTO authResponseDTO = userService.createUser(authorization, reqDTO);

        return ResponseEntity.ok(authResponseDTO); //액세스 토큰, 핀테크 번호 반환
    }

    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestHeader("Authorization") String authorization, @RequestParam("fintechUseNum") String fintechUseNum) {
        String accessToken = userService.createAccessToken(authorization, fintechUseNum);
        return ResponseEntity.ok(AccessTokenResponseDTO.create(accessToken));
    }
}
