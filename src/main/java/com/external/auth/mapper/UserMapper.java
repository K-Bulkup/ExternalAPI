package com.external.auth.mapper;

import com.external.auth.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void createUser(User user);

    void createUserAsset(Long userId);

    User findUserByUserId(Long userId);

    String findFintechUseNumByUserId(Long userId);
}
