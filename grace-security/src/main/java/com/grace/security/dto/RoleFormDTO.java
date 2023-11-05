package com.grace.security.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RoleFormDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色关键字
     */
    @NotBlank(message = "角色关键字不能为空")
    private String roleKey;


    /**
     * 角色状态（true正常 false停用）
     */
    private boolean status;

    /**
     * 备注
     */
    @Length(max = 300,message = "备注的字数不能大于300位")
    private String remark;

    public RoleFormDTO() {
    }

    public RoleFormDTO(Long id, String name, String roleKey, boolean status, String remark) {
        this.id = id;
        this.name = name;
        this.roleKey = roleKey;
        this.status = status;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
