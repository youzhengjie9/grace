package com.grace.security.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.grace.common.enums.ResultType;
import com.grace.common.utils.Result;
import com.grace.security.dto.UserLoginDTO;
import com.grace.security.service.UserService;
import com.grace.security.users.GraceUser;
import com.grace.security.vo.TokenVO;
import com.grace.security.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户控制器
 *
 * @author youzhengjie
 * @date 2023/09/06 19:43:30
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     *
     * @param userLoginDTO
     * @param request
     * @return {@link Result}<{@link TokenVO}>
     * @throws Throwable
     */
    @PostMapping("/login")
    public Result<TokenVO> login(@RequestBody @Valid UserLoginDTO userLoginDTO,
                                 HttpServletRequest request) throws Throwable {
        // 调用登录方法
        TokenVO tokenVO = userService.login(userLoginDTO, request);
        return Result.build(ResultType.LOGIN_SUCCESS,tokenVO);
    }

    /**
     * 退出登录
     *
     * @param accessToken  accessToken
     * @param refreshToken refreshToken(没有可以不传)
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping("/logout")
    public Result<Boolean> logout(@RequestHeader(value = "accessToken") String accessToken,
                                  @RequestHeader(value = "refreshToken",required = false) String refreshToken){

        return Result.ok(userService.logout(accessToken,refreshToken));
    }

    /**
     * 获取当前用户信息(请求头要携带accessToken)
     *
     * @return {@link Result}<{@link UserInfoVO}>
     */
    @GetMapping("/getCurrentUserInfo")
    public Result<UserInfoVO> getCurrentUserInfo(){
        return Result.ok(userService.getCurrentUserInfo());
    }

}
