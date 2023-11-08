package com.grace.security.dto;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 前端用户表单传给后端接口接收
 *
 * @author youzhengjie
 * @date 2023-11-05 11:57:03
 */
public class UserFormDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Length(min = 3,max = 15,message = "帐号长度要在3-15位之间")
    private String username;

    /**
     * 这里密码不要进行校验，因为编辑用户如果不输入密码则默认是不修改密码，所以这里可以为null
     */
    private String password;

    private boolean status;

    public UserFormDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
