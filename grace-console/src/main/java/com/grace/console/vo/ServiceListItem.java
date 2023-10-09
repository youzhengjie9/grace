package com.grace.console.vo;

/**
 * service列表项
 *
 * @author youzhengjie
 * @date 2023/10/08 09:27:19
 */
public class ServiceListItem {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 实例数（包括临时实例和永久实例）
     */
    private int instanceCount;

    /**
     * 健康实例数（包括临时实例和永久实例）
     */
    private int healthyInstanceCount;

    /**
     * 触发保护阈值的标志（true或者false）,判断当前service是否触发保护阈值
     */
    private String triggerProtectThresholdFlag;

    public ServiceListItem() {
    }

    public ServiceListItem(String serviceName, String groupName, int instanceCount, int healthyInstanceCount, String triggerProtectThresholdFlag) {
        this.serviceName = serviceName;
        this.groupName = groupName;
        this.instanceCount = instanceCount;
        this.healthyInstanceCount = healthyInstanceCount;
        this.triggerProtectThresholdFlag = triggerProtectThresholdFlag;
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

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(int instanceCount) {
        this.instanceCount = instanceCount;
    }

    public int getHealthyInstanceCount() {
        return healthyInstanceCount;
    }

    public void setHealthyInstanceCount(int healthyInstanceCount) {
        this.healthyInstanceCount = healthyInstanceCount;
    }

    public String getTriggerProtectThresholdFlag() {
        return triggerProtectThresholdFlag;
    }

    public void setTriggerProtectThresholdFlag(String triggerProtectThresholdFlag) {
        this.triggerProtectThresholdFlag = triggerProtectThresholdFlag;
    }

    @Override
    public String toString() {
        return "ServiceListItem{" +
                "serviceName='" + serviceName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", instanceCount=" + instanceCount +
                ", healthyInstanceCount=" + healthyInstanceCount +
                ", triggerProtectThresholdFlag='" + triggerProtectThresholdFlag + '\'' +
                '}';
    }
}
