package com.grace.security.token.impl;

import com.grace.security.constant.JwtConstants;
import com.grace.security.token.TokenManager;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private JwtTokenManager jwtTokenManager;

    //    /**
//     * 定时清理过期token
//     */
//    @Scheduled(initialDelay = 30000, fixedDelay = 60000) //启动后30s执行第一次，之后在上一次执行完毕时间点之后 60 秒再执行
//    private void cleanExpiredToken() {
//
//    }

    @Override
    public String createAccessToken(long userId) {
        String accessToken = jwtTokenManager.createAccessToken(userId);
        // 当前时间毫秒值
        long currentTimeMillis = System.currentTimeMillis();
        // accessToken过期时间毫秒值
        long accessTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getAccessTokenExpired();
        // 通过accessToken创建Authentication对象
        Authentication authentication =
                jwtTokenManager.createAuthentication(accessToken, JwtConstants.ACCESS_TOKEN);
        // 封装accessToken的属性
        TokenAttributes accessTokenAttributes = new TokenAttributes();
        accessTokenAttributes.setUserId(userId);
        accessTokenAttributes.setExpiredTimeMillis(accessTokenExpiredTimeMillis);
        accessTokenAttributes.setAuthentication(authentication);
        // 将accessToken和属性缓存起来
        accessTokenMap.put(accessToken,accessTokenAttributes);
        return accessToken;
    }

    @Override
    public Claims parseAccessToken(String accessToken) {
        return jwtTokenManager.parseAccessToken(accessToken);
    }

    @Override
    public Boolean deleteAccessToken(String accessToken) {
        accessTokenMap.remove(accessToken);
        return true;
    }

    @Override
    public long getUserIdByAccessToken(String accessToken) {
        return jwtTokenManager.getUserIdByAccessToken(accessToken);
    }

    @Override
    public String createRefreshToken(long userId) {
        String refreshToken = jwtTokenManager.createRefreshToken(userId);
        // 当前时间毫秒值
        long currentTimeMillis = System.currentTimeMillis();
        // refreshToken过期时间毫秒值
        long refreshTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getRefreshTokenExpired();
        // 通过refreshToken创建Authentication对象
        Authentication authentication =
                jwtTokenManager.createAuthentication(refreshToken, JwtConstants.REFRESH_TOKEN);
        // 封装refreshToken的属性
        TokenAttributes refreshTokenAttributes = new TokenAttributes();
        refreshTokenAttributes.setUserId(userId);
        refreshTokenAttributes.setExpiredTimeMillis(refreshTokenExpiredTimeMillis);
        refreshTokenAttributes.setAuthentication(authentication);
        // 将refreshToken和属性缓存起来
        refreshTokenMap.put(refreshToken,refreshTokenAttributes);
        return refreshToken;
    }

    @Override
    public Claims parseRefreshToken(String refreshToken) {
        return jwtTokenManager.parseRefreshToken(refreshToken);
    }

    @Override
    public Boolean deleteRefreshToken(String refreshToken) {
        refreshTokenMap.remove(refreshToken);
        return true;
    }

    @Override
    public long getUserIdByRefreshToken(String refreshToken) {
        return jwtTokenManager.getUserIdByRefreshToken(refreshToken);
    }

    @Override
    public Map<String, String> createAccessTokenAndRefreshToken(long userId) {

        // 同时创建accessToken和refreshToken
        Map<String, String> tokenMap =
                jwtTokenManager.createAccessTokenAndRefreshToken(userId);
        // 当前时间毫秒值
        long currentTimeMillis = System.currentTimeMillis();

        // 获取accessToken
        String accessToken = tokenMap.get(JwtConstants.ACCESS_TOKEN);
        // accessToken过期时间毫秒值
        long accessTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getAccessTokenExpired();
        // 通过accessToken创建Authentication
        Authentication accessTokenAuthentication =
                createAuthentication(accessToken, JwtConstants.ACCESS_TOKEN);

        // 获取refreshToken
        String refreshToken = tokenMap.get(JwtConstants.REFRESH_TOKEN);
        // refreshToken过期时间毫秒值
        long refreshTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getRefreshTokenExpired();
        // 通过refreshToken创建Authentication
        Authentication refreshTokenAuthentication =
                createAuthentication(refreshToken, JwtConstants.REFRESH_TOKEN);

        // 封装accessToken属性
        TokenAttributes accessTokenAttributes = new TokenAttributes();
        accessTokenAttributes.setUserId(userId);
        accessTokenAttributes.setExpiredTimeMillis(accessTokenExpiredTimeMillis);
        accessTokenAttributes.setAuthentication(accessTokenAuthentication);

        // 封装refreshToken属性
        TokenAttributes refreshTokenAttributes = new TokenAttributes();
        refreshTokenAttributes.setUserId(userId);
        refreshTokenAttributes.setExpiredTimeMillis(refreshTokenExpiredTimeMillis);
        refreshTokenAttributes.setAuthentication(refreshTokenAuthentication);

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
                long userId = refreshTokenAttributes.getUserId();
                // accessToken过期时间毫秒值
                long accessTokenExpiredTimeMillis = currentTimeMillis + jwtTokenManager.getAccessTokenExpired();
                // 给accessToken创建Authentication对象（用户登录对象）
                Authentication authentication = createAuthentication(accessToken, JwtConstants.ACCESS_TOKEN);
                // 封装accessToken的属性
                TokenAttributes accessTokenAttributes = new TokenAttributes();
                accessTokenAttributes.setUserId(userId);
                accessTokenAttributes.setExpiredTimeMillis(accessTokenExpiredTimeMillis);
                accessTokenAttributes.setAuthentication(authentication);
                // 将accessToken和其属性放入accessTokenMap中
                accessTokenMap.put(accessToken, accessTokenAttributes);
                return accessToken;
            }
            return null;
        }
        return null;
    }

    @Override
    public Authentication createAuthentication(String token, String tokenType) {
        return jwtTokenManager.createAuthentication(token,tokenType);
    }

    /**
     * 从缓存中获取Authentication对象
     *
     * @param token token
     * @param tokenType tokenType
     * @return {@link Authentication}
     */
    @Override
    public Authentication getAuthentication(String token, String tokenType) {
        if(tokenType.equalsIgnoreCase("accessToken")){
            // 如果这个accessToken在accessTokenMap缓存中
            if(accessTokenMap.containsKey(token)){
                // 从accessTokenMap中获取这个accessToken的属性（TokenAttributes）
                TokenAttributes accessTokenAttributes = accessTokenMap.get(token);
                // 返回accessToken属性（TokenAttributes）中的Authentication对象
                return accessTokenAttributes.getAuthentication();
            }
        }else if (tokenType.equalsIgnoreCase("refreshToken")) {
            // 如果这个refreshToken在refreshTokenMap缓存中
            if(accessTokenMap.containsKey(token)){
                // 从refreshTokenMap中获取这个refreshToken的属性（TokenAttributes）
                TokenAttributes refreshTokenAttributes = refreshTokenMap.get(token);
                // 返回refreshToken属性（TokenAttributes）中的Authentication对象
                return refreshTokenAttributes.getAuthentication();
            }
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
        private long userId;

        /**
         * token的过期时间毫秒值（当前时间毫秒值 > 过期时间毫秒值,则说明token过期）
         */
        private long expiredTimeMillis;

        /**
         * 当前token对应的SpringSecurity用户认证信息对象（该Authentication对象包含了用户的帐号、权限列表信息）,
         * 每一个登录成功的用户都会有一个这个对象
         * <p>
         * 解析,为什么一个token可以对应一个Authentication对象: 因为每一次创建token成功,就意味着有一个用户登录成功了,
         * 同时会产生一个Authentication对象（例如UsernamePasswordAuthenticationToken对象），我们便把它和token关联起来,
         * <p>
         * 作用: 方便通过token去找到对应的Authentication对象,因为重新创建Authentication对象比较复杂,
         * 又需要去数据库重新查询用户权限。
         * <p>
         * 注意,当用户信息被修改后: 可以通过token移除这个TokenAttributes,然后重新生成最新的TokenAttributes对象（
         * 特别是Authentication属性）
         */
        private Authentication authentication;

        public TokenAttributes() {
        }

        public TokenAttributes(long userId, long expiredTimeMillis, Authentication authentication) {
            this.userId = userId;
            this.expiredTimeMillis = expiredTimeMillis;
            this.authentication = authentication;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getUserId() {
            return userId;
        }

        public long getExpiredTimeMillis() {
            return expiredTimeMillis;
        }

        public void setExpiredTimeMillis(long expiredTimeMillis) {
            this.expiredTimeMillis = expiredTimeMillis;
        }

        public Authentication getAuthentication() {
            return authentication;
        }

        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public String toString() {
            return "TokenAttributes{" +
                    "userId=" + userId +
                    ", expiredTimeMillis=" + expiredTimeMillis +
                    ", authentication=" + authentication +
                    '}';
        }
    }
    
}
