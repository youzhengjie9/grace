package com.grace.common.dto;

import java.io.Serializable;

/**
 * 创建命名空间DTO
 *
 * @author youzhengjie
 * @date 2023/06/19 22:39:26
 */
public class CreateNamespaceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 命名空间名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    public CreateNamespaceDTO() {
    }

    public CreateNamespaceDTO(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "CreateNamespaceDTO{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
