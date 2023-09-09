package com.grace.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.security.entity.Role;
import com.grace.security.mapper.RoleMapper;
import com.grace.security.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * role service impl
 *
 * @author youzhengjie
 * @date 2023/09/08 22:33:12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


}
