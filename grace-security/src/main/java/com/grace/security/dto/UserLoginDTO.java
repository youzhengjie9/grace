package com.grace.security.dto;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * UserLoginDto 用于前端登录表单传给controller用的
 *
 * @author youzhengjie
 * @date 2023/09/06 19:49:48
 */
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @Length(min = 3,max = 15,message = "帐号长度要在3-15位之间")
    private String username;
    /**
     * 密码
     */
    @Length(min = 5,max = 20,message = "密码长度要在5-20位之间")
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
