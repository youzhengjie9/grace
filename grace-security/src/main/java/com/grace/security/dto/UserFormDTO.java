package com.grace.security.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    private String userName;

    @Length(min = 3,max = 15,message = "昵称长度要在3-15位之间")
    private String nickName;

    //这里密码不要进行校验，因为编辑用户如果不输入密码则默认是不修改密码，所以这里可以为null
    private String password;

    private boolean status;

    @Email(message = "邮箱格式不合理")
    private String email;

    @NotBlank(message = "手机号不能为空")

    private String phone;

    @NotBlank

    private String sex;

//    @NotBlank(message = "头像不能为空")
    private String avatar;

    public UserFormDTO() {
    }

    public UserFormDTO(Long id, String userName, String nickName, String password, boolean status, String email, String phone, String sex, String avatar) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
