package com.grace.console.dto;

import com.grace.common.constant.Constants;
import com.grace.common.entity.Instance;
import com.grace.common.entity.builder.InstanceBuilder;
import com.grace.console.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册Instance DTO
 *
 * @author youzhengjie
 * @date 2023/10/05 15:17:46
 */
public class RegisterInstanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 该实例所在的命名空间id
     */
    private String namespaceId;

    /**
     * 该实例所在的分组名
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
     * 是否在线(可修改)
     */
    private Boolean online;

    /**
     * 是否为临时实例
     */
    private Boolean ephemeral;

    /**
     * 该实例的元数据
     */
    private String metadata;


    public RegisterInstanceDTO() {
    }

    /**
     * 校验InstanceDTO对象的必填属性是否为空,为空则抛出异常
     */
    public void validateRequired(){
        // 如果serviceName为空,则报错
        if (StringUtils.isBlank(serviceName)) {
            throw new RuntimeException("serviceName不能为空");
        }
        // 如果ipAddr为空,则报错
        if (StringUtils.isBlank(ipAddr)) {
            throw new RuntimeException("ipAddr不能为空");
        }
        // 如果port为空,则报错
        if (port == null) {
            throw new RuntimeException("port不能为空");
        }
    }

    /**
     * 填充默认值（注意: 不会填充必填属性！）
     */
    public void fillDefaultValue() {
        if (namespaceId == null) {
            namespaceId = Constants.DEFAULT_NAMESPACE_ID;
        }
        if (StringUtils.isBlank(groupName)) {
            groupName = Constants.DEFAULT_GROUP_NAME;
        }
        if (healthy == null) {
            healthy = Constants.DEFAULT_HEALTHY;
        }
        if (weight == null) {
            weight = Constants.DEFAULT_WEIGHT;
        }
        if (online == null) {
            online = Constants.DEFAULT_ONLINE;
        }
        if(ephemeral == null){
            ephemeral = Constants.DEFAULT_EPHEMERAL;
        }
    }

    /**
     * 通过RegisterInstanceDTO对象构建实例（Instance）
     *
     * @return {@link Instance}
     */
    public Instance buildInstanceByRegisterInstanceDTO() {
        // 将Map<Object, Object>类型的metadata转成Map<String,String>类型
        final Map<Object, Object> oldMetadataMap = JsonUtils.jsonStr2Map(metadata);
        final Map<String,String> newMetadataMap = new ConcurrentHashMap<>();
        if(oldMetadataMap != null){
            oldMetadataMap.forEach((key,value) -> {
                String k = String.valueOf(key);
                String v = String.valueOf(value);
                newMetadataMap.put(k,v);
            });
        }
        return InstanceBuilder.newBuilder()
                .instanceId(UUID.randomUUID().toString())
                .serviceName(serviceName)
                .ipAddr(ipAddr)
                .port(port)
                .weight(weight)
                .healthy(healthy)
                .online(online)
                .ephemeral(ephemeral)
                .metadata(newMetadataMap)
                .createTime(LocalDateTime.now())
                .build();
    }


    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getGroupName() {
        return groupName;
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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
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
        RegisterInstanceDTO that = (RegisterInstanceDTO) o;
        return Objects.equals(namespaceId, that.namespaceId)
                && Objects.equals(groupName, that.groupName)
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
        return Objects.hash(namespaceId, groupName, serviceName, ipAddr, port, weight, healthy, ephemeral, metadata);
    }

    @Override
    public String toString() {
        return "RegisterInstanceDTO{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", healthy=" + healthy +
                ", online=" + online +
                ", ephemeral=" + ephemeral +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
