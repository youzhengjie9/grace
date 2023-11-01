package com.grace.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.vo.TokenVO;
import com.grace.common.vo.UserInfoVO;
import com.grace.common.dto.UserLoginDTO;
import com.grace.security.entity.User;

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


}
