package com.grace.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * user mapper
 *
 * @author youzhengjie
 * @date 2023/09/08 18:05:37
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名去查询用户
     *
     * @param username 用户名
     * @return {@link User}
     */
    User getUserByUserName(@Param("username") String username);

}
