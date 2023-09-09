package com.grace.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.security.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * role mapper
 *
 * @author youzhengjie
 * @date 2023/09/08 18:07:43
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {


}
