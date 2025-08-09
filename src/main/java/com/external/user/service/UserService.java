package com.external.user.service;

import com.external.user.domain.User;
import com.external.user.dto.response.AuthResponseDTO;
import com.external.user.dto.request.TraineePortfolioCreateRequestDTO;
import com.external.user.exception.UnauthorizedException;
import com.external.user.mapper.UserMapper;
import com.external.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    /*
    static private final Long expireDuration = 3456000L;
     private final RefreshUtil refreshUtil;
     private final RedisUtil redisUtil;
     */
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponseDTO createUser(String authorization, TraineePortfolioCreateRequestDTO reqDTO) {
        Long userId = jwtUtil.getUserId(authorization);
        AuthResponseDTO resDTO = createUserAuth(userId);
        User user = User.create(userId, reqDTO.getBank(), resDTO.getFintechUseNum());
        userMapper.createUser(user);
        userMapper.createPortfolio(user);

        return resDTO;
    }

    private AuthResponseDTO createUserAuth(Long userId) {
        String accessToken = jwtUtil.generateToken(userId);
        // String refreshToken = refreshUtil.generateRefreshToken();
        String fintechUseNum = UUID.nameUUIDFromBytes(
                String.valueOf(userId).getBytes(StandardCharsets.UTF_8)
        ).toString();
        // redisUtil.setValue("refresh:user:" + userId, refreshToken, expireDuration);

        return AuthResponseDTO.create(accessToken, fintechUseNum);
    }

    public String createAccessToken(String authorization, String fintechUseNum) {
        Long userId = jwtUtil.getUserId(authorization);
        String fintechUseNumByUserId = userMapper.findFintechUseNumByUserId(userId);

        if (!fintechUseNumByUserId.equals(fintechUseNum)) {
            throw new UnauthorizedException("유효하지 않은 핀테크 이용번호입니다.");
        }
        return jwtUtil.generateToken(userId);
    }

    @Transactional
    public void validateUserAuth(String accessToken, String fintechUseNum) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnauthorizedException("토큰이 유효하지 않습니다.");
        }
        Long userId = jwtUtil.getUserId(accessToken);
        String fintechUseNumByUserId = userMapper.findFintechUseNumByUserId(userId);

        if (!fintechUseNumByUserId.equals(fintechUseNum)) {
            throw new UnauthorizedException("유효하지 않은 핀테크 이용번호입니다.");
        }

    }

}

