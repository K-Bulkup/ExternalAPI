package com.external.user.mapper;

import com.external.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void createUser(User user);

    void createUserAsset(Long userId);

    User findUserByUserId(Long userId);

    String findFintechUseNumByUserId(Long userId);
}
