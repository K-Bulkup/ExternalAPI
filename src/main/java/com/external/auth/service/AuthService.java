package com.external.auth.service;

import com.external.auth.domain.Bank;
import com.external.auth.domain.User;
import com.external.auth.dto.TraineePortfolioCreateResponseDTO;
import com.external.auth.exception.UnauthorizedException;
import com.external.auth.mapper.AuthMapper;
import com.external.auth.util.JwtUtil;
import com.external.auth.util.RefreshUtil;
import com.external.auth.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    static private final Long expireDuration = 3456000L; // Redis 만료기간 40일

    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;
    private final RefreshUtil refreshUtil;
    private final RedisUtil redisUtil;

    @Transactional
    public TraineePortfolioCreateResponseDTO createFintechUseNum(Long userId, Bank bank, String accountNum) {
        String fintechUseNum = UUID.randomUUID().toString();

        User user = User.create(userId, accountNum, bank, fintechUseNum);
        authMapper.createUser(user);
        authMapper.createUserAsset(userId);
        String refreshToken = createRefreshToken(userId);
        String accessToken = jwtUtil.generateToken(userId);

        return TraineePortfolioCreateResponseDTO.create(accessToken, refreshToken, fintechUseNum);
    }

    private String createRefreshToken(Long userId) {
        String refreshToken = refreshUtil.generateRefreshToken();
        redisUtil.setValue("refresh:user:" + userId, refreshToken, expireDuration);
        return refreshToken;
    }

    @Transactional
    public TraineePortfolioCreateResponseDTO validateAndGetUser(String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnauthorizedException("토큰이 유효하지 않습니다.");
        }
        Long userId = jwtUtil.getUserId(accessToken);
        User user = authMapper.findByUserId(userId);

        if(user == null) {
            throw new UnauthorizedException("유효하지 않은 핀테크 이용번호입니다.");
        }

        String refreshToken = redisUtil.getValue("refresh:user:" + user.getUserId());
        String fintechUseNum = authMapper.findFintechUseNumByUserId(userId);
        return TraineePortfolioCreateResponseDTO.create(accessToken, refreshToken, fintechUseNum);
    }

    @Transactional
    public TraineePortfolioCreateResponseDTO getNewAccessTokenByUserId(String refreshToken, Long userId) {

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
        String fintechUseNum = authMapper.findFintechUseNumByUserId(userId);

        return TraineePortfolioCreateResponseDTO.create(newAccessToken, refreshToken, fintechUseNum);
    }

}

