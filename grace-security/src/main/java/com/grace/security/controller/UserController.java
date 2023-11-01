package com.grace.security.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.enums.ResultType;
import com.grace.common.utils.Result;
import com.grace.common.vo.TokenVO;
import com.grace.common.vo.UserInfoVO;
import com.grace.common.dto.UserLoginDTO;
import com.grace.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户控制器
 *
 * @author youzhengjie
 * @date 2023/09/06 19:43:30
 */
@RestController
@RequestMapping(path = ParentMappingConstants.USER_CONTROLLER)
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     *
     * @return {@link Result}<{@link TokenVO}>
     */
    @PostMapping("/login")
    public Result<TokenVO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws Throwable {
        // 调用登录方法
        TokenVO tokenVO = userService.login(userLoginDTO);
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
