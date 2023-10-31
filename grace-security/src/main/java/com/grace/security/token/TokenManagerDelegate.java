package com.grace.security.token;

import com.grace.security.token.impl.CachedJwtTokenManager;
import com.grace.security.token.impl.JwtTokenManager;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 令牌管理器的委派（代表）
 * <p>
 * 作用是只需要通过调用这个令牌管理器的委派（代表）,就可以同时操作多个底层的实现类（JwtTokenManager、CachedJwtTokenManager）
 *
 * @author youzhengjie
 * @date 2023/08/29 13:19:25
 */
@Component
public class TokenManagerDelegate implements TokenManager {

    /**
     * 开启令牌缓存（默认true为开启）
     */
    private boolean enableTokenCached = true;

    /**
     * 没有缓存的jwt令牌管理器
     */
    private JwtTokenManager jwtTokenManager;

    /**
     * 有缓存的jwt令牌管理器（推荐使用这个）
     */
    private CachedJwtTokenManager cachedJwtTokenManager;

    @Autowired
    public void setJwtTokenManager(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Autowired
    public void setCachedJwtTokenManager(CachedJwtTokenManager cachedJwtTokenManager) {
        this.cachedJwtTokenManager = cachedJwtTokenManager;
    }

    /**
     * 选择我们使用的底层实现类对象（JwtTokenManager、CachedJwtTokenManager）。委派模式的核心。
     * <p>
     * 作用：通过enableTokenCached变量来选择底层实现类对象（JwtTokenManager、CachedJwtTokenManager）
     * 1：如果enableTokenCached为true,则使用cachedJwtTokenManager对象（说明我们要使用《有缓存》的令牌管理器对象）
     * 2：如果enableTokenCached为false,则使用jwtTokenManager对象（说明我们要使用《没有缓存》的令牌管理器对象）
     *
     * @return {@link TokenManager}
     */
    private TokenManager getTokenManager() {
        return enableTokenCached ? cachedJwtTokenManager : jwtTokenManager;
    }


    @Override
    public String createAccessToken(long userId) {
        return getTokenManager().createAccessToken(userId);
    }

    /**
     * @param accessToken
     * @return {@link Claims}
     */
    @Override
    public Claims parseAccessToken(String accessToken) {
        return getTokenManager().parseAccessToken(accessToken);
    }

    @Override
    public Boolean deleteAccessToken(String accessToken) {
        return getTokenManager().deleteAccessToken(accessToken);
    }

    @Override
    public long getUserIdByAccessToken(String accessToken) {
        return getTokenManager().getUserIdByAccessToken(accessToken);
    }

    @Override
    public String createRefreshToken(long userId) {
        return getTokenManager().createRefreshToken(userId);
    }

    @Override
    public Claims parseRefreshToken(String refreshToken) {
        return getTokenManager().parseRefreshToken(refreshToken);
    }

    @Override
    public Boolean deleteRefreshToken(String refreshToken) {
        return getTokenManager().deleteRefreshToken(refreshToken);
    }

    @Override
    public long getUserIdByRefreshToken(String refreshToken) {
        return getTokenManager().getUserIdByRefreshToken(refreshToken);
    }

    @Override
    public Map<String, String> createAccessTokenAndRefreshToken(long userId) {
        return getTokenManager().createAccessTokenAndRefreshToken(userId);
    }

    @Override
    public String toRefreshToken(String refreshToken) {
        return getTokenManager().toRefreshToken(refreshToken);
    }

    @Override
    public Authentication createAuthentication(String token, String tokenType) {
        return getTokenManager().createAuthentication(token,tokenType);
    }

    @Override
    public Authentication getAuthentication(String token, String tokenType) {
        return getTokenManager().getAuthentication(token,tokenType);
    }

    @Override
    public boolean isAccessTokenExpired(String accessToken) {
        return getTokenManager().isAccessTokenExpired(accessToken);
    }

    @Override
    public boolean isRefreshTokenExpired(String refreshToken) {
        return getTokenManager().isRefreshTokenExpired(refreshToken);
    }
}
