package com.grace.console.vo;

import com.grace.common.entity.Instance;

import java.io.Serializable;
import java.util.List;

/**
 * 服务详情vo
 *
 * @author youzhengjie
 * @date 2023/10/12 09:31:03
 */
public class ServiceDetailVO implements Serializable {

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
     * 服务名称
     */
    private String serviceName;

    /**
     * 保护阈值（其实就是最小的健康实例数占比,如果当前service的健康实例数占比<=最小的健康实例数占比,说明需要触发保护阈值）
     * <p>
     * protectThreshold的范围是,最小值为 0,最大值为 1
     * <p>
     * 比如protectThreshold=0.25,相当于protectThreshold（最小的健康实例数占比）为 25%,小于或者等于这个值就会触发保护阈值
     */
    private float protectThreshold;

    /**
     * 存储该service下的所有实例（包括临时实例、永久实例）
     */
    private List<Instance> allInstances;

    /**
     * 元数据
     */
    private String metadata;

    public ServiceDetailVO() {
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

    public float getProtectThreshold() {
        return protectThreshold;
    }

    public void setProtectThreshold(float protectThreshold) {
        this.protectThreshold = protectThreshold;
    }

    public List<Instance> getAllInstances() {
        return allInstances;
    }

    public void setAllInstances(List<Instance> allInstances) {
        this.allInstances = allInstances;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "ServiceDetailVO{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", protectThreshold=" + protectThreshold +
                ", allInstances=" + allInstances +
                ", metadata=" + metadata +
                '}';
    }
}
