package com.grace.common.vo;

import java.io.Serializable;

public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    public UserInfoVO() {
    }

    public UserInfoVO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "username='" + username + '\'' +
                '}';
    }
}
