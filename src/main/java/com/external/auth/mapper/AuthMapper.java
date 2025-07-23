package com.external.auth.mapper;

import com.external.auth.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    void createUser(UserVO userVO);
}
