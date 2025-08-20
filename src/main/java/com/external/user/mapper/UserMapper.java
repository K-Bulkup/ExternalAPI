package com.external.user.mapper;

import com.external.user.domain.Bank;
import com.external.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void createUser(User user);

    void createPortfolio(User user);

    String findFintechUseNumByUserId(Long userId);

    void mapFintechUseNum(@Param("bank")Bank bank, @Param("accountNumber") String accountNumber);

    String findFintechUseNumByUserInfo(@Param("bank")Bank bank, @Param("accountNumber") String accountNumber);
}
