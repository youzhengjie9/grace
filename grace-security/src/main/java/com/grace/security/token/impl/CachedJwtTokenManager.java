package com.grace.security.token.impl;

import com.grace.security.JwtConstants;
import com.grace.security.token.TokenManager;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 有缓存的jwt令牌管理器（推荐使用这个）
 *
 * @author youzhengjie
 * @date 2023/08/29 13:18:44
 */
@Component // 必须设置为单例,否则下面的map缓存将没有任何意义
public class CachedJwtTokenManager implements TokenManager {

    /**
     * key: accessToken值 , value: accessToken的属性
     * 作用是: 方便通过accessToken值快速找到其对应的属性和判断该accessToken是否存在,以及我们不用频繁解析token来获取里面的属性了
     */
    private volatile Map<String, TokenAttributes> accessTokenMap = new ConcurrentHashMap<>(1024);

    /**
     * key: refreshToken值 , value: refreshToken的属性
     * 作用是: 方便通过refreshToken值快速找到其对应的属性和判断该refreshToken是否存在,以及我们不用频繁解析token来获取里面的属性了
     */
    private volatile Map<String, TokenAttributes> refreshTokenMap = new ConcurrentHashMap<>(1024);

//    /**
//     * key: 用户id , value: accessToken
//     * 作用是: 方便通过用户id快速找到accessToken,防止同一个用户创建多个accessToken
//     */
//    private volatile Map<String, String> userIdAccessTokenMap = new ConcurrentHashMap<>(128);
//
//    /**
//     * key: 用户id , value: refreshToken
//     * 作用是: 方便通过用户id快速找到refreshToken,防止同一个用户创建多个refreshToken
//     */
//    private volatile Map<String, String> userIdRefreshTokenMap = new ConcurrentHashMap<>(128);


    private JwtTokenManager jwtTokenManager;

    @Autowired
    public void setJwtTokenManager(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    //    /**
//     * 定时清理过期token
//     */
//    @Scheduled(initialDelay = 30000, fixedDelay = 60000) //启动后30s执行第一次，之后在上一次执行完毕时间点之后 60 秒再执行
//    private void cleanExpiredToken() {
//
//    }

    @Override
    public String createAccessToken(String userId) {
        String accessToken = jwtTokenManager.createAccessToken(userId);
        // 当前时间毫秒值
        long currentTimeMillis = System.currentTimeMillis();
        // accessToken过期时间毫秒值
        long accessTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getAccessTokenExpired();
        // 封装accessToken的属性
        TokenAttributes accessTokenAttributes = new TokenAttributes();
        accessTokenAttributes.setUserId(userId);
        accessTokenAttributes.setExpiredTimeMillis(accessTokenExpiredTimeMillis);
        // 将accessToken和属性缓存起来
        accessTokenMap.put(accessToken,accessTokenAttributes);
        return accessToken;
    }

    @Override
    public Claims parseAccessToken(String accessToken) {
        return jwtTokenManager.parseAccessToken(accessToken);
    }

    @Override
    public String getUserIdByAccessToken(String accessToken) {
        return jwtTokenManager.getUserIdByAccessToken(accessToken);
    }

    @Override
    public String createRefreshToken(String userId) {
        return null;
    }

    @Override
    public Claims parseRefreshToken(String refreshToken) {
        return jwtTokenManager.parseRefreshToken(refreshToken);
    }

    @Override
    public String getUserIdByRefreshToken(String refreshToken) {
        return jwtTokenManager.getUserIdByRefreshToken(refreshToken);
    }

    @Override
    public Map<String, String> createAccessTokenAndRefreshToken(String userId) {

        // 同时创建accessToken和refreshToken
        Map<String, String> tokenMap =
                jwtTokenManager.createAccessTokenAndRefreshToken(userId);
        // 当前时间毫秒值
        long currentTimeMillis = System.currentTimeMillis();
        // 获取accessToken
        String accessToken = tokenMap.get(JwtConstants.ACCESS_TOKEN_NAME);
        // accessToken过期时间毫秒值
        long accessTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getAccessTokenExpired();
        // 获取refreshToken
        String refreshToken = tokenMap.get(JwtConstants.REFRESH_TOKEN_NAME);
        // refreshToken过期时间毫秒值
        long refreshTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getRefreshTokenExpired();
        // 封装accessToken属性
        TokenAttributes accessTokenAttributes = new TokenAttributes();
        accessTokenAttributes.setUserId(userId);
        accessTokenAttributes.setExpiredTimeMillis(accessTokenExpiredTimeMillis);
        // 封装refreshToken属性
        TokenAttributes refreshTokenAttributes = new TokenAttributes();
        refreshTokenAttributes.setUserId(userId);
        refreshTokenAttributes.setExpiredTimeMillis(refreshTokenExpiredTimeMillis);
        // 将accessToken和属性缓存起来
        accessTokenMap.put(accessToken,accessTokenAttributes);
        // 将refreshToken和属性缓存起来
        refreshTokenMap.put(refreshToken,refreshTokenAttributes);

        return tokenMap;
    }

    @Override
    public String toRefreshToken(String refreshToken) {
        // 判断refreshToken是否在refreshTokenMap缓存中,只有在这个缓存中才能刷新token
        if(refreshTokenMap.containsKey(refreshToken)) {
            // 拿到refreshToken的属性（也可以直接解析jwt token,但是我们已经把token的属性存到缓存中,所以不需要解析）
            TokenAttributes refreshTokenAttributes = refreshTokenMap.get(refreshToken);
            // 当前时间毫秒值
            long currentTimeMillis = System.currentTimeMillis();
            long refreshTokenExpiredTimeMillis = refreshTokenAttributes.getExpiredTimeMillis();
            // 如果refreshToken过期
            if(currentTimeMillis > refreshTokenExpiredTimeMillis){
                return null;
            }
            // 如果refreshToken没有过期
            String accessToken = jwtTokenManager.toRefreshToken(refreshToken);
            // 如果刷新token成功,则accessToken不为null,要对这种情况进行处理
            if (accessToken != null) {
                String userId = refreshTokenAttributes.getUserId();
                // accessToken过期时间毫秒值
                long accessTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getAccessTokenExpired();
                TokenAttributes accessTokenAttributes = new TokenAttributes();
                accessTokenAttributes.setUserId(userId);
                accessTokenAttributes.setExpiredTimeMillis(accessTokenExpiredTimeMillis);
                accessTokenMap.put(accessToken, accessTokenAttributes);
                return accessToken;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean isAccessTokenExpired(String accessToken) {
        return jwtTokenManager.isAccessTokenExpired(accessToken);
    }

    @Override
    public boolean isRefreshTokenExpired(String refreshToken) {
        return jwtTokenManager.isRefreshTokenExpired(refreshToken);
    }

    /**
     * token基本的属性
     *
     * @author youzhengjie
     * @date 2023/09/05 22:30:50
     */
    static class TokenAttributes {

        /**
         * token所属的用户id
         */
        private String userId;

        /**
         * token的过期时间毫秒值（当前时间毫秒值 > 过期时间毫秒值,则说明token过期）
         */
        private long expiredTimeMillis;

        public TokenAttributes() {
        }

        public TokenAttributes(String userId, long expiredTimeMillis) {
            this.userId = userId;
            this.expiredTimeMillis = expiredTimeMillis;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public long getExpiredTimeMillis() {
            return expiredTimeMillis;
        }

        public void setExpiredTimeMillis(long expiredTimeMillis) {
            this.expiredTimeMillis = expiredTimeMillis;
        }

        @Override
        public String toString() {
            return "TokenAttributes{" +
                    "userId='" + userId + '\'' +
                    ", expiredTimeMillis=" + expiredTimeMillis +
                    '}';
        }
    }
    
}
