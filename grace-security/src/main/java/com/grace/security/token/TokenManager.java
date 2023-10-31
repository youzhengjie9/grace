package com.grace.security.token;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.Map;


/**
 * token（令牌）管理器
 * <p>
 *
 * 使用jwtToken缓存的调用顺序: 用户调用 --> TokenManagerDelegate --> CachedJwtTokenManager --> JwtTokenManager
 * <p>
 * 不使用jwtToken缓存的调用顺序: 用户调用 --> TokenManagerDelegate --> JwtTokenManager
 *
 * @author youzhengjie
 * @date 2023/08/27 15:29:57
 */
public interface TokenManager {


    /**
     * 根据用户id生成accessToken
     *
     * @param userId 用户id
     * @return accessToken
     */
    String createAccessToken(long userId);

    /**
     * 解析accessToken
     *
     * @param accessToken accessToken
     * @return 获取Claims，失败返回null
     */
    Claims parseAccessToken(String accessToken);

    /**
     * 删除accessToken（只有当实现类为CachedJwtTokenManager时生效）
     *
     * @param accessToken accessToken
     * @return {@link Boolean}
     */
    Boolean deleteAccessToken(String accessToken);

    /**
     * 根据accessToken获取用户id（通过用户id存储在Claims的subject中,如果不是则获取不到）
     *
     * @param accessToken accessToken
     * @return 获取用户id，失败返回null
     */
    long getUserIdByAccessToken(String accessToken);

    /**
     * 根据用户id生成refreshToken
     *
     * @param userId 用户id
     * @return refreshToken
     */
    String createRefreshToken(long userId);

    /**
     * 解析refreshToken
     *
     * @param refreshToken refreshToken
     * @return 获取Claims，失败返回null
     */
    Claims parseRefreshToken(String refreshToken);

    /**
     * 删除refreshToken（只有当实现类为CachedJwtTokenManager时生效）
     *
     * @param refreshToken refreshToken
     * @return {@link Boolean}
     */
    Boolean deleteRefreshToken(String refreshToken);

    /**
     * 根据refreshToken获取用户id（通过用户id存储在Claims的subject中,如果不是则获取不到）
     *
     * @param refreshToken refreshToken
     * @return 获取用户id，失败返回null
     */
    long getUserIdByRefreshToken(String refreshToken);

    /**
     * 通过map集合.get(JwtConstants.ACCESS_TOKEN)可以拿到accessToken
     * 通过map集合.get(JwtConstants.REFRESH_TOKEN)可以拿到refreshToken
     *
     * @return 同时返回accessToken和refreshToken的map集合
     */
    Map<String,String> createAccessTokenAndRefreshToken(long userId);

    /**
     * 去刷新token。本质上就是重新生成新的accessToken并替代掉前端localstorage的旧的accessToken
     *
     * @return 返回新的accessToken，失败返回null。
     */
    String toRefreshToken(String refreshToken);

    /**
     * 创建Authentication对象。
     *
     * @param token token
     * @param tokenType tokenType
     * @return {@link Authentication}
     */
    Authentication createAuthentication(String token, String tokenType);

    /**
     * 从缓存中获取Authentication对象
     * <p>
     * 根据token获取SpringSecurity用户认证信息对象（Authentication）,每一个登录成功的用户都会有一个这个对象
     *
     * @param token
     * @param tokenType
     * @return {@link Authentication}
     */
    Authentication getAuthentication(String token,String tokenType);

    /**
     * 判断accessToken是否过期
     *
     * @param accessToken accessToken
     * @return true说明过期了，false说明没有过期
     */
    boolean isAccessTokenExpired(String accessToken);

    /**
     * 判断refreshToken是否过期
     *
     * @param refreshToken refreshToken
     * @return true说明过期了，false说明没有过期
     */
    boolean isRefreshTokenExpired(String refreshToken);

    
}
