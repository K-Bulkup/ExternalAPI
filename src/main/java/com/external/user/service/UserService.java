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
    // private final RefreshUtil refreshUtil;
    // private final RedisUtil redisUtil;

    @Transactional
    public AuthResponseDTO createUser(Long userId, TraineePortfolioCreateRequestDTO reqDTO) {
        AuthResponseDTO resDTO = createUserAuth(userId);
        User user = User.create(userId, reqDTO.getBank(), resDTO.getFintechUseNum());
        userMapper.createUser(user);
        userMapper.createPortfolio(userId);

        return resDTO;
    }

    private AuthResponseDTO createUserAuth(Long userId) {
        String accessToken = jwtUtil.generateToken(userId);
        // String refreshToken = refreshUtil.generateRefreshToken();
        String fintechUseNum = UUID.randomUUID().toString();

        // redisUtil.setValue("refresh:user:" + userId, refreshToken, expireDuration);

        return AuthResponseDTO.create(accessToken, fintechUseNum);
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

    public Long getUserId(String token) {
        return jwtUtil.getUserId(token.replace("Bearer ", "").trim());
    }

}

