package com.grace.common.dto;

import java.io.Serializable;

public class CreateServiceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 命名空间
     */
    private String namespaceName;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 元数据
     */
    private String metaData;

    public CreateServiceDTO() {
    }

    public CreateServiceDTO(String serviceName, String namespaceName, String groupName, String metaData) {
        this.serviceName = serviceName;
        this.namespaceName = namespaceName;
        this.groupName = groupName;
        this.metaData = metaData;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "CreateServiceDTO{" +
                "serviceName='" + serviceName + '\'' +
                ", namespaceName='" + namespaceName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", metaData='" + metaData + '\'' +
                '}';
    }
}
