package com.grace.client.misc;

/**
 * 存储用户在客户端进行登录拿到的 accessToken和 refreshToken (这个类采用单例模式)
 *
 * @author youzhengjie
 * @date 2023/11/01 00:21:31
 */
public class TokenStorage {

    /**
     * 用户登录成功后拿到的accessToken
     */
    private volatile String accessToken;

    /**
     * 用户登录成功后拿到的refreshToken
     */
    private volatile String refreshToken;

    private static final TokenStorage INSTANCE = new TokenStorage();

    private TokenStorage() {
    }

    public static TokenStorage getSingleton(){
        return INSTANCE;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "TokenStorage{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
