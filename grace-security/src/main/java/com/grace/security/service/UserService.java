package com.grace.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.utils.Result;
import com.grace.security.dto.UserLoginDTO;
import com.grace.security.entity.User;
import com.grace.security.vo.TokenVO;

import javax.servlet.http.HttpServletRequest;

/**
 * user service
 *
 * @author youzhengjie
 * @date 2023/09/08 22:28:09
 */
public interface UserService extends IService<User> {

    Result<TokenVO> login(UserLoginDTO userLoginDTO, HttpServletRequest request) throws Throwable;


}
