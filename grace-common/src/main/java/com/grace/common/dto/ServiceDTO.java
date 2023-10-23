package com.grace.common.dto;

import com.grace.common.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class ServiceDTO implements Serializable {

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
     * 保护阈值
     */
    private Float protectThreshold;

    /**
     * 元数据（必须为JSON字符串类型）
     */
    private String metadata;

    public ServiceDTO() {
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

    public Float getProtectThreshold() {
        return protectThreshold;
    }

    public void setProtectThreshold(Float protectThreshold) {
        this.protectThreshold = protectThreshold;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    /**
     * 校验ServiceDTO对象的必填属性是否为空,为空则抛出异常
     */
    public void validateRequired(){
        fillDefaultValue();
        if (StringUtils.isBlank(serviceName)) {
            throw new RuntimeException("serviceName不能为空");
        }
    }

    /**
     * 填充默认值（注意: 不会填充必填属性！）
     */
    public void fillDefaultValue() {
        if (StringUtils.isBlank(namespaceId)) {
            namespaceId = Constants.DEFAULT_NAMESPACE_ID;
        }
        if (StringUtils.isBlank(groupName)) {
            groupName = Constants.DEFAULT_GROUP_NAME;
        }
        if (protectThreshold == null) {
            protectThreshold = 0.0F;
        }
        if (StringUtils.isBlank(metadata)) {
            metadata = StringUtils.EMPTY;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        ServiceDTO that = (ServiceDTO) o;
        return Objects.equals(namespaceId, that.namespaceId)
                && Objects.equals(groupName, that.groupName)
                && Objects.equals(serviceName, that.serviceName)
                && Objects.equals(protectThreshold, that.protectThreshold)
                && Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespaceId, groupName, serviceName, protectThreshold, metadata);
    }

    @Override
    public String toString() {
        return "ServiceDTO{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", protectThreshold=" + protectThreshold +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
