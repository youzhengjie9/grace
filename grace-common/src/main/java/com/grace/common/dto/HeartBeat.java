package com.grace.common.dto;

import java.io.Serializable;

/**
 * 心跳
 *
 * @author youzhengjie
 * @date 2023/10/24 20:47:06
 */
public class HeartBeat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 命名空间id
     */
    private String namespaceId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 该实例所属的服务名
     */
    private String serviceName;

    /**
     * 该实例的ip地址
     */
    private String ipAddr;

    /**
     * 该实例的端口号
     */
    private int port;

    public HeartBeat() {
    }

    public HeartBeat(String namespaceId, String groupName, String serviceName, String ipAddr, int port) {
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.serviceName = serviceName;
        this.ipAddr = ipAddr;
        this.port = port;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "HeartBeat{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                '}';
    }
}
