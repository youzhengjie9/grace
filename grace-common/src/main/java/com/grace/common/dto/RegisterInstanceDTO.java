package com.grace.common.dto;

import java.io.Serializable;

/**
 * 注册实例dto
 *
 * @author youzhengjie
 * @date 2023/07/13 17:31:24
 */
public class RegisterInstanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 该实例所属的命名空间
     */
    private String namespace;

    /**
     * 该实例所属的service名称
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

    /**
     * 该实例的权重
     */
    private int weight;

    /**
     * 该实例的元数据
     */
    private String metaData;

    public RegisterInstanceDTO() {
    }

    public RegisterInstanceDTO(String namespace, String serviceName, String ipAddr, int port, int weight, String metaData) {
        this.namespace = namespace;
        this.serviceName = serviceName;
        this.ipAddr = ipAddr;
        this.port = port;
        this.weight = weight;
        this.metaData = metaData;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "RegisterInstanceDTO{" +
                "namespace='" + namespace + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", metaData='" + metaData + '\'' +
                '}';
    }
}
