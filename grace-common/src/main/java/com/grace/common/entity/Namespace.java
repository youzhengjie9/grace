package com.grace.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * grace命名空间表实体类
 *
 * @author youzhengjie
 * @date 2023-10-07 09:25:39
 */
@TableName("sys_namespace")
public class Namespace implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，命令空间id
     */
    @TableId(value = "namespace_id",type = IdType.INPUT)
    private String namespaceId;

    /**
     * 命名空间名称
     */
    @TableField("namespace_name")
    private String namespaceName;

    /**
     * 该命名空间最大的配置数
     */
    @TableField("max_config_count")
    private int maxConfigCount;

    /**
     * 命名空间描述
     */
    @TableField("namespace_desc")
    private String namespaceDesc;


    public Namespace() {
    }

    public Namespace(String namespaceId, String namespaceName, int maxConfigCount, String namespaceDesc) {
        this.namespaceId = namespaceId;
        this.namespaceName = namespaceName;
        this.maxConfigCount = maxConfigCount;
        this.namespaceDesc = namespaceDesc;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public int getMaxConfigCount() {
        return maxConfigCount;
    }

    public void setMaxConfigCount(int maxConfigCount) {
        this.maxConfigCount = maxConfigCount;
    }

    public String getNamespaceDesc() {
        return namespaceDesc;
    }

    public void setNamespaceDesc(String namespaceDesc) {
        this.namespaceDesc = namespaceDesc;
    }

    @Override
    public String toString() {
        return "Namespace{" +
                "namespaceId='" + namespaceId + '\'' +
                ", namespaceName='" + namespaceName + '\'' +
                ", maxConfigCount=" + maxConfigCount +
                ", namespaceDesc='" + namespaceDesc + '\'' +
                '}';
    }
}
