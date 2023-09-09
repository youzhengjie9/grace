package com.grace.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.enums.ResultType;
import com.grace.common.utils.Result;
import com.grace.security.JwtConstants;
import com.grace.security.dto.UserLoginDTO;
import com.grace.security.entity.User;
import com.grace.security.mapper.UserMapper;
import com.grace.security.service.UserService;
import com.grace.security.token.TokenManagerDelegate;
import com.grace.security.users.GraceUser;
import com.grace.security.vo.TokenVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * user service impl
 *
 * @author youzhengjie
 * @date 2023/09/08 22:29:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private TokenManagerDelegate tokenManagerDelegate;

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setTokenManagerDelegate(TokenManagerDelegate tokenManagerDelegate) {
        this.tokenManagerDelegate = tokenManagerDelegate;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Result<TokenVO> login(UserLoginDTO userLoginDTO, HttpServletRequest request) throws Throwable {
        // 前端传来的帐号
        String username = userLoginDTO.getUsername();
        // 前端传来的密码
        String password = userLoginDTO.getPassword();
        // UsernamePasswordAuthenticationToken两个参数的构造方法就是用来分别传递帐号密码的。（这里我们一定要使用这个）
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        // 使用AuthenticationManager的authenticate方法对帐号密码进行验证
        // 重点1: authenticationManager.authenticate底层原理:
        //   (1): authenticationManager.authenticate底层就是调用了UserDetailsService的loadUserByUserName方法，获取到UserDetails对象（也就是GraceUser对象）
        //   (2): 将usernamePasswordAuthenticationToken（前端传入的帐号密码）和loadUserByUsername中的userMapper.selectOne(lambdaQueryWrapper)方法查询的帐号密码进行比对，判断帐号密码输入是否正确。
        //   (3): 如果验证失败的话，就会在loadUserByUsername方法中抛出异常并且被AuthenticationEntryPointImpl方法捕获
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 注意: 走到这里说明帐号密码验证正确,因为如果帐号密码不正确的话authenticationManager.authenticate方法会抛出异常,就走不到这里了

        // 让SpringSecurity知道我们已经登录成功
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取登录成功后的UserDetails对象（例如: GraceUser对象）
        GraceUser graceUser = (GraceUser) authentication.getPrincipal();
        String userId = String.valueOf(graceUser.getUser().getId());
        // 生成accessToken和refreshToken
        Map<String, String> tokenMap = tokenManagerDelegate.createAccessTokenAndRefreshToken(userId);
        String accessToken = tokenMap.get(JwtConstants.ACCESS_TOKEN);
        String refreshToken = tokenMap.get(JwtConstants.REFRESH_TOKEN);
        // 把accessToken和refreshToken返回出去
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(accessToken);
        tokenVO.setRefreshToken(refreshToken);
        return Result.build(ResultType.LOGIN_SUCCESS,tokenVO);
    }
}
