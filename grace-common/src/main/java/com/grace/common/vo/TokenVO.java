package com.grace.common.vo;

import java.io.Serializable;

/**
 * 封装accessToken和refreshToken（用于登录后的结果返回）
 *
 * @author youzhengjie
 * @date 2023/09/06 19:50:51
 */
public class TokenVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;

    private String refreshToken;

    public TokenVO() {
    }

    public TokenVO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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
        return "TokenVO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
