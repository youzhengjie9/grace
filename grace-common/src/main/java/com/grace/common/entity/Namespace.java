package com.grace.common.entity;

import java.io.Serializable;

/**
 * grace命名空间表实体类
 *
 * @author youzhengjie
 * @date 2023-10-07 09:25:39
 */
public class Namespace implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 命令空间id
     */
    private String namespaceId;

    /**
     * 命名空间名称
     */
    private String namespaceName;

    /**
     * 配额
     */
    private int quota;

    /**
     * 配置数量
     */
    private int configCount;

    /**
     * 命名空间类型。（ 0 : Global configuration， 1 : Default private namespace ，2 : Custom namespace.）
     */
    private int type;

    /**
     * 命名空间描述
     */
    private String namespaceDesc;


    public Namespace() {
    }

    public Namespace(String namespaceId, String namespaceName, int quota, int configCount, int type, String namespaceDesc) {
        this.namespaceId = namespaceId;
        this.namespaceName = namespaceName;
        this.quota = quota;
        this.configCount = configCount;
        this.type = type;
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

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getConfigCount() {
        return configCount;
    }

    public void setConfigCount(int configCount) {
        this.configCount = configCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
                ", quota=" + quota +
                ", configCount=" + configCount +
                ", type=" + type +
                ", namespaceDesc='" + namespaceDesc + '\'' +
                '}';
    }
}
