package com.grace.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.security.entity.User;
import com.grace.security.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * user mapper
 *
 * @author youzhengjie
 * @date 2023/09/08 18:05:37
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有用户并分页（并对分页功能进行性能调优）
     */
    List<User> getUserList(@Param("page") int page, @Param("size") int size);

    /**
     * 获取总用户数
     */
    int getUserCount();


    /**
     * 根据用户名获取用户列表并分页
     */
    List<User> getUserListByUsername(@Param("username") String username,
                                     @Param("page") int page,
                                     @Param("size") int size);


    /**
     * 根据用户名获取用户数量
     *
     * @param username 用户名
     * @return int
     */
    int getUserCountByUsername(@Param("username") String username);

    /**
     * 用户信息修改
     */
    int modifyUser(User user);


    /**
     * 给用户添加角色
     *
     * @param userRoleList 用户角色列表
     * @return int
     */
    int addRoleToUser(@Param("userRoleList") List<UserRole> userRoleList);

    /**
     * 删除用户所有角色
     *
     * @param userId 用户id
     * @return int
     */
    int deleteUserAllRoles(@Param("userId") long userId);


    /**
     * 根据用户名去查询用户
     *
     * @param username 用户名
     * @return {@link User}
     */
    User getUserByUserName(@Param("username") String username);

}
