package com.external.auth.service;

import com.external.auth.domain.UserVO;
import com.external.auth.dto.TokenResponseDTO;
import com.external.auth.mapper.AuthMapper;
import com.external.auth.util.JwtUtil;
import com.external.auth.util.RefreshUtil;
import com.external.auth.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.UUID;

@Service
public class AuthService {

    static private final Long expireDuration = 3456000L; // Redis 만료기간 40일

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshUtil refreshUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Transactional
    public TokenResponseDTO createFintechUseNum() {
        String fintechUseNum = UUID.randomUUID().toString();

        UserVO userVO = UserVO.of(
                BigInteger.valueOf(1),
                "111111-222222",
                "국민은행",
                fintechUseNum
        );
        authMapper.createUser(userVO);
        String refreshToken = createRefreshToken(fintechUseNum);
        String accessToken = jwtUtil.generateToken(fintechUseNum);

        return new TokenResponseDTO(accessToken, refreshToken, fintechUseNum);
    }

    public String createRefreshToken(String fintechUseNum) {
        String refreshToken = refreshUtil.generateRefreshToken();
        redisUtil.setValue(fintechUseNum, refreshToken, expireDuration);

        return refreshToken;
    }
}

