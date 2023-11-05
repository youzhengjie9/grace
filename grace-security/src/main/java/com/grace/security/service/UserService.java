package com.grace.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.security.dto.UserFormDTO;
import com.grace.common.vo.TokenVO;
import com.grace.common.vo.UserInfoVO;
import com.grace.common.dto.UserLoginDTO;
import com.grace.security.entity.User;
import com.grace.security.entity.UserRole;

import java.util.List;

/**
 * user service
 *
 * @author youzhengjie
 * @date 2023/09/08 22:28:09
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     */
    TokenVO login(UserLoginDTO userLoginDTO) throws Throwable;

    /**
     * 退出登录
     *
     * @param accessToken  accessToken
     * @param refreshToken refreshToken
     * @return {@link Boolean}
     */
    Boolean logout(String accessToken,String refreshToken);

    /**
     * 获取当前用户信息(请求头要携带accessToken)
     *
     * @return {@link UserInfoVO}
     */
    UserInfoVO getCurrentUserInfo();

    /**
     * 查询所有用户并分页（并对分页功能进行性能调优）
     */
    List<User> getUserList(int page, int size);

    /**
     * 获取总用户数
     */
    int getUserCount();

    /**
     * 根据用户名获取用户列表并分页
     */
    List<User> getUserListByUsername(String username, int page, int size);


    /**
     * 根据用户名获取用户数量
     *
     * @param username 用户名
     * @return int
     */
    int getUserCountByUsername(String username);

    /**
     * 添加用户
     *
     * @param userFormDTO 用户表单DTO
     * @return int
     */
    int addUser(UserFormDTO userFormDTO);

    /**
     * 修改用户
     *
     * @param userFormDTO 用户表单dto
     * @return int
     */
    int updateUser(UserFormDTO userFormDTO);


    /**
     * 删除用户
     *
     * @param id 用户id
     * @return int
     */
    boolean deleteUser(long id);

    /**
     * 删除用户所有角色
     *
     * @param userid 用户标识
     * @return int
     */
    int deleteUserAllRoles(long userid);

    /**
     * 将角色分配给用户
     *
     * @param userRoleList 用户角色列表
     * @return boolean
     */
    boolean assignRoleToUser(List<UserRole> userRoleList);

    /**
     * 给用户添加角色
     *
     * @param userRoleList 用户角色列表
     * @return int
     */
    int addRoleToUser(List<UserRole> userRoleList);


}
