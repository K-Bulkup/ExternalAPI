package com.external.user.service;

import com.external.user.domain.User;
import com.external.user.dto.AuthDTO;
import com.external.user.dto.request.TraineePortfolioCreateRequestDTO;
import com.external.user.exception.UnauthorizedException;
import com.external.user.mapper.UserMapper;
import com.external.user.util.JwtUtil;
import com.external.user.util.RefreshUtil;
import com.external.user.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    static private final Long expireDuration = 3456000L; // Redis 만료기간 40일

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final RefreshUtil refreshUtil;
    private final RedisUtil redisUtil;

    public Long getUserId(String token) {
        return jwtUtil.getUserId(token.replace("Bearer ", ""));
    }

    @Transactional
    public String createUser(Long userId, TraineePortfolioCreateRequestDTO dto) {
        String fintechUseNum = UUID.randomUUID().toString();
        User user = User.create(userId, dto.getAccountNum(), dto.getBank(), fintechUseNum);
        //String accessToken = jwtUtil.generateToken(userId);
        //createRefreshToken(userId);
        userMapper.createUser(user);
        userMapper.createUserAsset(userId);

        return fintechUseNum;
    }

    private void createRefreshToken(Long userId) {
        String refreshToken = refreshUtil.generateRefreshToken();
        redisUtil.setValue("refresh:user:" + userId, refreshToken, expireDuration);
    }

    @Transactional
    public AuthDTO validateAndGetUser(String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnauthorizedException("토큰이 유효하지 않습니다.");
        }
        Long userId = jwtUtil.getUserId(accessToken);
        String fintechUseNum = userMapper.findFintechUseNumByUserId(userId);

        if(fintechUseNum == null) {
            throw new UnauthorizedException("유효하지 않은 핀테크 이용번호입니다.");
        }

        User user = userMapper.findUserByUserId(userId);
        String refreshToken = redisUtil.getValue("refresh:user:" + user.getUserId());
        return AuthDTO.create(accessToken, refreshToken, fintechUseNum);
    }

    @Transactional
    public AuthDTO getNewAccessTokenByUserId(String refreshToken, Long userId) {
        refreshToken = refreshToken.replace("Bearer ", "");

        // 1. Redis에서 저장된 리프레시 토큰 조회
        String storedRefreshToken = redisUtil.getValue("refresh:user:" + userId);

        if (storedRefreshToken == null) {
            throw new UnauthorizedException("계좌/은행 정보 재등록 필요");
        }

        if (!refreshToken.equals(storedRefreshToken)) {
            throw new UnauthorizedException("유효하지 않은 리프레시 토큰입니다.");
        }

        // 2. 새로운 액세스 토큰 발급
        String newAccessToken = jwtUtil.generateToken(userId);
        String fintechUseNum = userMapper.findFintechUseNumByUserId(userId);

        return AuthDTO.create(newAccessToken, refreshToken, fintechUseNum);
    }

}

