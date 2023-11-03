package com.grace.common.dto;

import java.util.Arrays;
import java.util.List;

/**
 * 克隆配置DTO
 *
 * @author youzhengjie
 * @date 2023/11/03 18:35:51
 */
public class CloneConfigDTO {

    /**
     * 目标命名空间(将配置克隆到哪个命名空间)
     */
    private String targetNamespaceId;

    /**
     * 需要克隆的配置的列表
     */
    private List<CloneConfigItem> cloneConfigItemList;

    /**
     * 配置冲突策略
     */
    private String configConflictPolicy;

    public CloneConfigDTO() {
    }

    public String getTargetNamespaceId() {
        return targetNamespaceId;
    }

    public void setTargetNamespaceId(String targetNamespaceId) {
        this.targetNamespaceId = targetNamespaceId;
    }

    public List<CloneConfigItem> getCloneConfigItemList() {
        return cloneConfigItemList;
    }

    public void setCloneConfigItemList(List<CloneConfigItem> cloneConfigItemList) {
        this.cloneConfigItemList = cloneConfigItemList;
    }

    public String getConfigConflictPolicy() {
        return configConflictPolicy;
    }

    public void setConfigConflictPolicy(String configConflictPolicy) {
        this.configConflictPolicy = configConflictPolicy;
    }

    @Override
    public String toString() {
        return "CloneConfigDTO{" +
                "targetNamespaceId='" + targetNamespaceId + '\'' +
                ", cloneConfigItemList=" + cloneConfigItemList +
                ", configConflictPolicy='" + configConflictPolicy + '\'' +
                '}';
    }
}
