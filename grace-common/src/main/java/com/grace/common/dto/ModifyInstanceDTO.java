package com.grace.common.dto;

import com.grace.common.constant.Constants;
import com.grace.common.entity.Instance;
import com.grace.common.entity.builder.InstanceBuilder;
import com.grace.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 修改Instance DTO
 *
 * @author youzhengjie
 * @date 2023/10/05 15:17:46
 */
public class ModifyInstanceDTO implements Serializable {

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
     * 实例id
     */
    private String instanceId;

    /**
     * 该实例的权重(可修改)
     */
    private Double weight;

    /**
     * 实例是否健康(可修改)
     */
    private Boolean healthy;

    /**
     * 是否在线(可修改)
     */
    private Boolean online;

    /**
     * 该实例的元数据(可修改)
     */
    private String metadata;


    public ModifyInstanceDTO() {
    }

    /**
     * 校验InstanceDTO对象的必填属性是否为空,为空则抛出异常
     */
    public void validateRequired(){
        // 如果serviceName为空,则报错
        if (StringUtils.isBlank(serviceName)) {
            throw new RuntimeException("serviceName不能为空");
        }
        // 如果instanceId为空,则报错
        if (StringUtils.isBlank(instanceId)) {
            throw new RuntimeException("instanceId不能为空");
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
        if (online == null) {
            online = Constants.DEFAULT_ONLINE;
        }
        if (weight == null) {
            weight = Constants.DEFAULT_WEIGHT;
        }
    }

    /**
     * 通过RegisterInstanceDTO对象构建实例（Instance）
     *
     * @return {@link Instance}
     */
    public Instance buildInstanceByModifyInstanceDTO() {
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
                .instanceId(instanceId)
                .serviceName(serviceName)
                .weight(weight)
                .healthy(healthy)
                .online(online)
                .metadata(newMetadataMap)
                .build();
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getInstanceId() {
        return instanceId;
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "ModifyInstanceDTO{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", weight=" + weight +
                ", healthy=" + healthy +
                ", online=" + online +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
