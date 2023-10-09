package com.grace.security.token.impl;

import com.grace.security.JwtConstants;
import com.grace.security.entity.User;
import com.grace.security.service.MenuService;
import com.grace.security.service.UserService;
import com.grace.security.token.TokenManager;
import com.grace.security.users.GraceUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 没有缓存的jwt令牌管理器
 *
 * @author youzhengjie
 * @date 2023/08/29 13:16:56
 */
@Component
public class JwtTokenManager implements TokenManager {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenManager.class);

    /**
     * accessToken密钥
     */
    @Value("${jwt.accessToken.secret:accessToken-secret}")
    private String accessTokenSecret;

    /**
     * accessToken过期时间，单位是（毫秒）。一小时=3600000毫秒
     */
    @Value("${jwt.accessToken.expired:10800000}")
    private long accessTokenExpired;

    /**
     * refreshToken密钥
     */
    @Value("${jwt.refreshToken.secret:refreshToken-secret}")
    private String refreshTokenSecret;

    /**
     * refreshToken过期时间，单位是（毫秒）。一小时=3600000毫秒
     */
    @Value("${jwt.refreshToken.expired:21600000}")
    private long refreshTokenExpired;

    @Value("${jwt.issuer:youzhengjie}")
    private String issuer;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * @return 随机的id
     */
    private String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public String createAccessToken(long userId) {
        // 当前时间毫秒值
        long currentTimeMillis = System.currentTimeMillis();
        // accessToken过期时间毫秒值
        long expiredTimeMillis = currentTimeMillis + accessTokenExpired;
        return Jwts.builder()
                // 唯一的ID
                .setId(getUUID())
                // userId就存放到这里
                .setSubject(String.valueOf(userId))
                // 签发者
                .setIssuer(issuer)
                // 颁发token时间
                .setIssuedAt(new Date(currentTimeMillis))
                // token过期时间
                .setExpiration(new Date(expiredTimeMillis))
                // 密钥
                .signWith(SignatureAlgorithm.HS256,accessTokenSecret)
                .compact();
    }

    @Override
    public Claims parseAccessToken(String accessToken) {
        return Jwts
                .parser()
                .setSigningKey(accessTokenSecret)
                .parseClaimsJws(accessToken)
                .getBody();
    }

    @Override
    public long getUserIdByAccessToken(String accessToken) {
        return Long.parseLong(parseAccessToken(accessToken)
                .getSubject());
    }

    @Override
    public String createRefreshToken(long userId) {
        // 当前时间毫秒值
        long currentTimeMillis = System.currentTimeMillis();
        // refreshToken过期时间毫秒值
        long expiredTimeMillis = currentTimeMillis + refreshTokenExpired;
        return Jwts.builder()
                // 唯一的ID
                .setId(getUUID())
                // userId就存放到这里
                .setSubject(String.valueOf(userId))
                // 签发者
                .setIssuer(issuer)
                // 颁发token时间
                .setIssuedAt(new Date(currentTimeMillis))
                //token过期时间
                .setExpiration(new Date(expiredTimeMillis))
                // 密钥
                .signWith(SignatureAlgorithm.HS256,refreshTokenSecret)
                .compact();
    }

    @Override
    public Claims parseRefreshToken(String refreshToken) {
        return Jwts
                .parser()
                .setSigningKey(refreshTokenSecret)
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    @Override
    public long getUserIdByRefreshToken(String refreshToken) {
        return Long.parseLong(parseRefreshToken(refreshToken)
                .getSubject());
    }

    @Override
    public Map<String, String> createAccessTokenAndRefreshToken(long userId) {
        Map<String,String> tokenMap = new ConcurrentHashMap<>();
        // 创建accessToken
        String accessToken = createAccessToken(userId);
        // 创建refreshToken
        String refreshToken = createRefreshToken(userId);
        // 放到一个map中
        tokenMap.put(JwtConstants.ACCESS_TOKEN,accessToken);
        tokenMap.put(JwtConstants.REFRESH_TOKEN,refreshToken);
        return tokenMap;
    }

    @Override
    public String toRefreshToken(String refreshToken) {
        //先判断refreshToken是否过期，看看能不能刷新token，否则返回null
        if(!canRefresh(refreshToken)){
            return null;
        }
        // 解析refreshToken获取其userId
        long userId = getUserIdByRefreshToken(refreshToken);

        // 重新生成accessToken
        return createAccessToken(userId);
    }

    @Override
    public Authentication getAuthentication(String token, String tokenType) {
        throw new RuntimeException("JwtTokenManager类不能获取Authentication对象,请使用CachedJwtTokenManager类进行获取");
    }

    /**
     * 创建Authentication对象。
     *
     * @param token token
     * @param tokenType tokenType
     * @return {@link Authentication}
     */
    @Override
    public Authentication createAuthentication(String token, String tokenType) {
        if(tokenType.equalsIgnoreCase("accessToken")){
            // 根据accessToken获取userId
            long userId = getUserIdByAccessToken(token);
            // 根据userId查询用户信息
            User user = userService.lambdaQuery()
                    .select(
                            User::getUserName,
                            User::getStatus,
                            User::getDelFlag
                    )
                    .eq(User::getId, userId)
                    .one();
            // 根据userId查询权限列表
            List<String> permissions = menuService.getAllPermissionsByUserId(userId);
            // 给user对象设置userId
            user.setId(userId);
            // 封装GraceUser对象
            GraceUser graceUser = new GraceUser(user,permissions);
            // 获取用户的权限列表
            Collection<? extends GrantedAuthority> authorities = graceUser.getAuthorities();
            // 创建Authentication对象
            return new UsernamePasswordAuthenticationToken(graceUser,"",authorities);
        }else if (tokenType.equalsIgnoreCase("refreshToken")) {
            // 根据refreshToken获取userId
            long userId = getUserIdByRefreshToken(token);
            // 根据userId查询用户信息
            User user = userService.lambdaQuery()
                    .select(
                            User::getUserName,
                            User::getStatus,
                            User::getDelFlag
                    )
                    .eq(User::getId, userId)
                    .one();
            // 根据userId查询权限列表
            List<String> permissions = menuService.getAllPermissionsByUserId(userId);
            // 给user对象设置userId
            user.setId(userId);
            // 封装GraceUser对象
            GraceUser graceUser = new GraceUser(user,permissions);
            // 获取用户的权限列表
            Collection<? extends GrantedAuthority> authorities = graceUser.getAuthorities();
            // 创建Authentication对象
            return new UsernamePasswordAuthenticationToken(graceUser,"",authorities);
        }
        return null;
    }

    @Override
    public boolean isAccessTokenExpired(String accessToken) {
        Claims claims = parseAccessToken(accessToken);
        Date expiredDate = claims.getExpiration();
        return expiredDate.before(new Date());
    }

    @Override
    public boolean isRefreshTokenExpired(String refreshToken) {
        Claims claims = parseRefreshToken(refreshToken);
        Date expiredDate = claims.getExpiration();
        return expiredDate.before(new Date());
    }

    /**
     * 判断token是否可以被刷新（主要就是看refreshToken是否过期，如果没有过期则可以刷新token）
     *
     * @return true就是refreshToken可以使用，反之则不行
     */
    public boolean canRefresh(String refreshToken){
        return !isRefreshTokenExpired(refreshToken);
    }

    public long getAccessTokenExpired() {
        return accessTokenExpired;
    }

    public long getRefreshTokenExpired() {
        return refreshTokenExpired;
    }
}
