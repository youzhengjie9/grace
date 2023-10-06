package com.grace.console.dto;

import com.grace.common.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Instance DTO
 *
 * @author youzhengjie
 * @date 2023/10/05 15:17:46
 */
public class InstanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 该实例所在的命名空间id
     */
    private Long namespaceId;

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
    private Integer port;

    /**
     * 该实例的权重
     */
    private Double weight;

    /**
     * 实例是否健康
     */
    private Boolean healthy;

    /**
     * 是否为临时实例
     */
    private Boolean ephemeral;

    /**
     * 该实例的元数据
     */
    private String metadata;


    public InstanceDTO() {
    }


    /**
     * 校验InstanceDTO对象的必填属性是否为空,为空则抛出异常
     */
    public void validateRequired(){
        // 如果serviceName为空,则报错
        if (StringUtils.isBlank(serviceName)) {
            throw new RuntimeException("serviceName为空");
        }
        // 如果ipAddr为空,则报错
        if (StringUtils.isBlank(ipAddr)) {
            throw new RuntimeException("ipAddr为空");
        }
        // 如果port为空,则报错
        if (port == null) {
            throw new RuntimeException("port为空");
        }
    }

    /**
     * 填充默认值（注意: 不会填充必填属性！）
     */
    public void fillDefaultValue() {
        if (namespaceId == null) {
            namespaceId = Constants.DEFAULT_NAMESPACE_ID;
        }
//        if (StringUtils.isBlank(groupName)) {
//            groupName = Constants.DEFAULT_GROUP_NAME;
//        }
        if (healthy == null) {
            healthy = Constants.DEFAULT_HEALTHY;
        }
        if (weight == null) {
            weight = Constants.DEFAULT_WEIGHT;
        }
        if(ephemeral == null){
            ephemeral = Constants.DEFAULT_EPHEMERAL;
        }
    }

    public void setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
    }

    public Long getNamespaceId() {
        return namespaceId;
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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getHealthy() {
        return healthy;
    }

    public void setHealthy(Boolean healthy) {
        this.healthy = healthy;
    }

    public Boolean getEphemeral() {
        return ephemeral;
    }

    public void setEphemeral(Boolean ephemeral) {
        this.ephemeral = ephemeral;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        InstanceDTO that = (InstanceDTO) o;
        return Objects.equals(namespaceId, that.namespaceId)
                && Objects.equals(serviceName, that.serviceName)
                && Objects.equals(ipAddr, that.ipAddr)
                && Objects.equals(port, that.port)
                && Objects.equals(weight, that.weight)
                && Objects.equals(healthy, that.healthy)
                && Objects.equals(ephemeral, that.ephemeral)
                && Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespaceId, serviceName, ipAddr, port, weight, healthy, ephemeral, metadata);
    }

    @Override
    public String toString() {
        return "InstanceDTO{" +
                "namespaceId=" + namespaceId +
                ", serviceName='" + serviceName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", healthy=" + healthy +
                ", ephemeral=" + ephemeral +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
