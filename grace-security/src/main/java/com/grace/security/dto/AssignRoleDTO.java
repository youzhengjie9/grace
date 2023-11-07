package com.grace.security.dto;

import com.grace.security.entity.Role;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 分配角色DTO
 *
 * @author youzhengjie
 * @date 2023-11-05 13:38:13
 */
public class AssignRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 该用户新的角色列表
     */
    private List<Role> roles;

    @NotBlank(message = "需要分配角色的用户id不能为空")
    private String userid;

    public AssignRoleDTO() {
    }

    public AssignRoleDTO(List<Role> roles, String userid) {
        this.roles = roles;
        this.userid = userid;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
