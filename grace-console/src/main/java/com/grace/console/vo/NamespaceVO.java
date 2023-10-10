package com.grace.console.vo;

import java.io.Serializable;

/**
 * namespace vo
 *
 * @author youzhengjie
 * @date 2023/10/11 00:02:41
 */
public class NamespaceVO implements Serializable {

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
     * 服务数量
     */
    private int serviceCount;

    /**
     * 配置数量
     */
    private int configCount;

    /**
     * 命名空间描述
     */
    private String namespaceDesc;


    public NamespaceVO() {
    }

    public NamespaceVO(String namespaceId, String namespaceName, int serviceCount, int configCount, String namespaceDesc) {
        this.namespaceId = namespaceId;
        this.namespaceName = namespaceName;
        this.serviceCount = serviceCount;
        this.configCount = configCount;
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

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public int getConfigCount() {
        return configCount;
    }

    public void setConfigCount(int configCount) {
        this.configCount = configCount;
    }

    public String getNamespaceDesc() {
        return namespaceDesc;
    }

    public void setNamespaceDesc(String namespaceDesc) {
        this.namespaceDesc = namespaceDesc;
    }

    @Override
    public String toString() {
        return "NamespaceVO{" +
                "namespaceId='" + namespaceId + '\'' +
                ", namespaceName='" + namespaceName + '\'' +
                ", serviceCount=" + serviceCount +
                ", configCount=" + configCount +
                ", namespaceDesc='" + namespaceDesc + '\'' +
                '}';
    }
}
