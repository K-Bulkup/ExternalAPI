package com.external.auth.service;

import com.external.auth.domain.UserVO;
import com.external.auth.dto.TokenResponseDTO;
import com.external.auth.exception.UnauthorizedException;
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
    public TokenResponseDTO createFintechUseNum(BigInteger userId, String bank, String accountNum) {
        String fintechUseNum = UUID.randomUUID().toString();

        UserVO userVO = UserVO.of(userId, accountNum, bank, fintechUseNum);
        authMapper.createUser(userVO);
        String refreshToken = createRefreshToken(userId);
        String accessToken = jwtUtil.generateToken(userId);

        return new TokenResponseDTO(accessToken, refreshToken, fintechUseNum);
    }

    private String createRefreshToken(BigInteger userId) {
        String refreshToken = refreshUtil.generateRefreshToken();
        redisUtil.setValue("refresh:user:" + userId, refreshToken, expireDuration);
        return refreshToken;
    }

    @Transactional
    public TokenResponseDTO validateAndGetUser(String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnauthorizedException("토큰이 유효하지 않습니다.");
        }
        BigInteger userId = jwtUtil.getUserId(accessToken);
        UserVO user = authMapper.findByUserId(userId);

        if(user == null) {
            throw new UnauthorizedException("유효하지 않은 핀테크 이용번호입니다.");
        }

        String refreshToken = redisUtil.getValue("refresh:user:" + user.getUserId());
        String fintechUseNum = authMapper.findFintechUseNumByUserId(userId);
        return new TokenResponseDTO(accessToken, refreshToken, fintechUseNum);
    }

}

