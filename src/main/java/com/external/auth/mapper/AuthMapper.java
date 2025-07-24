package com.external.auth.mapper;

import com.external.auth.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface AuthMapper {
    void createUser(UserVO userVO);

    void createUserAsset(BigInteger userId);

    UserVO findByUserId(BigInteger userId);

    String findFintechUseNumByUserId(BigInteger userId);
}
