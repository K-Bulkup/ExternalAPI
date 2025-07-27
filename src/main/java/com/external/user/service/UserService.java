package com.external.user.service;

import com.external.user.domain.User;
import com.external.user.dto.response.AuthResponseDTO;
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
        return jwtUtil.getUserId(token.replace("Bearer ", "").trim());
    }

    @Transactional
    public AuthResponseDTO createUser(String authorization, TraineePortfolioCreateRequestDTO reqDTO) {
        // jwtUtil.getUserId(authorization); 로그인 구현 시 활성화
        Long userId = 1L;
        AuthResponseDTO resDTO = createUserAuth(userId);
        User user = User.create(userId, reqDTO.getAccountNum(), reqDTO.getBank(), resDTO.getFintechUseNum());
        userMapper.createUser(user);
        userMapper.createPortfolio(userId);

        return resDTO;
    }

    private AuthResponseDTO createUserAuth(Long userId) {
        String accessToken = jwtUtil.generateToken(userId);
        String refreshToken = refreshUtil.generateRefreshToken();
        String fintechUseNum = UUID.randomUUID().toString();

        redisUtil.setValue("refresh:user:" + userId, refreshToken, expireDuration);

        return AuthResponseDTO.create(accessToken, refreshToken, fintechUseNum);
    }

    @Transactional
    public String validateUserAuth(String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnauthorizedException("토큰이 유효하지 않습니다.");
        }
        Long userId = jwtUtil.getUserId(accessToken);
        String fintechUseNum = userMapper.findFintechUseNumByUserId(userId);

        if(fintechUseNum == null) {
            throw new UnauthorizedException("유효하지 않은 핀테크 이용번호입니다.");
        }

        return fintechUseNum;
    }

    @Transactional
    public AuthResponseDTO getNewAccessTokenByUserId(String refreshToken, Long userId) {
        refreshToken = refreshToken.replace("Bearer ", "").trim();

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

        return AuthResponseDTO.create(newAccessToken, refreshToken, fintechUseNum);
    }

}

