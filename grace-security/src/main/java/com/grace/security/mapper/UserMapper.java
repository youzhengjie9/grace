package com.grace.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * user mapper
 *
 * @author youzhengjie
 * @date 2023/09/08 18:05:37
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {




}
