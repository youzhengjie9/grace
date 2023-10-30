package com.grace.common.dto;

import java.io.Serializable;

/**
 * 客户端地址绑定配置（用于push模式,刷新所有绑定了这个被修改的配置的客户端）
 *
 * @author youzhengjie
 * @date 2023/10/30 15:31:22
 */
public class ClientAddressBindConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 绑定配置的命名空间id
     */
    private String namespaceId;

    /**
     * 绑定配置的分组名称
     */
    private String groupName;

    /**
     * 绑定配置的dataId
     */
    private String dataId;

    /**
     * 绑定配置的客户端地址（格式例如 192.168.184.100:6001 ）
     */
    private String clientAddress;

    public ClientAddressBindConfigDTO() {
    }

    public ClientAddressBindConfigDTO(String namespaceId, String groupName, String dataId, String clientAddress) {
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.dataId = dataId;
        this.clientAddress = clientAddress;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    @Override
    public String toString() {
        return "ClientAddressBindConfigDTO{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", dataId='" + dataId + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                '}';
    }
}
