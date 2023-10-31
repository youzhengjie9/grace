package com.grace.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.security.dto.UserLoginDTO;
import com.grace.security.entity.User;
import com.grace.security.vo.TokenVO;
import com.grace.security.vo.UserInfoVO;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

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
    TokenVO login(UserLoginDTO userLoginDTO, HttpServletRequest request) throws Throwable;

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
