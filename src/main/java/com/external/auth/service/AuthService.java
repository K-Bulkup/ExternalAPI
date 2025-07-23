package com.external.auth.service;

import com.external.auth.domain.UserVO;
import com.external.auth.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Transactional
    public String issueNewFintechUseNum() {
        String fintechUseNum = UUID.randomUUID().toString();

        UserVO userVO = UserVO.of(
                BigInteger.valueOf(1),
                "111111-222222",
                "국민은행",
                fintechUseNum
        );

        authMapper.createUser(userVO);
        return fintechUseNum;
    }

    //TODO : 기존 존재 유저면
}

