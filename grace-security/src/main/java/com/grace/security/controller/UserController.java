package com.grace.security.controller;

import com.grace.common.utils.Result;
import com.grace.security.dto.UserLoginDTO;
import com.grace.security.service.UserService;
import com.grace.security.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * user controller
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

    @PostMapping("/login")
    public Result<TokenVO> login(@RequestBody @Valid UserLoginDTO userLoginDTO,
                                 HttpServletRequest request) throws Throwable {
        return userService.login(userLoginDTO,request);
    }



}
