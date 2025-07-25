package com.external.auth.mapper;

import com.external.auth.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    void createUser(User user);

    void createUserAsset(Long userId);

    User findByUserId(Long userId);

    String findFintechUseNumByUserId(Long userId);
}
