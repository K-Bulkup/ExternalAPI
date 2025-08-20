package com.external.user.service;

import com.external.user.dto.response.AuthResponseDTO;
import com.external.user.dto.request.TraineePortfolioCreateRequestDTO;
import com.external.user.exception.UnauthorizedException;
import com.external.user.mapper.UserMapper;
import com.external.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AuthResponseDTO createUser(TraineePortfolioCreateRequestDTO reqDTO) {
        userMapper.mapFintechUseNum(reqDTO.getBank(), reqDTO.getAccountNumber());
        String fintechUseNum = userMapper.findFintechUseNumByUserInfo(reqDTO.getBank(), reqDTO.getAccountNumber());
        String accessToken = jwtUtil.generateToken(fintechUseNum);
        return AuthResponseDTO.create(accessToken, fintechUseNum);
    }

    public String createAccessToken(String fintechUseNum) {
        String accessToken = jwtUtil.generateToken(fintechUseNum);
        return accessToken;
    }

    @Transactional
    public void validateUserAuth(String accessToken, String fintechUseNum) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnauthorizedException("토큰이 유효하지 않습니다.");
        }
        /*String TokenOfFintechUseNum = jwtUtil.getFintechUseNum(accessToken);
        String fintechUseNumByUserId = userMapper.findFintechUseNumByUserId(TokenOfFintechUseNum);

        if (!fintechUseNumByUserId.equals(fintechUseNum)) {
            throw new UnauthorizedException("유효하지 않은 핀테크 이용번호입니다.");
        }*/
    }

}

