package com.grace.common.dto;

/**
 * 需要克隆的配置
 *
 * @author youzhengjie
 * @date 2023/11/03 18:36:00
 */
public class CloneConfigItem {

    /**
     * 需要克隆的配置的id
     */
    private Long id;

    /**
     * 将配置克隆到哪个分组上（相当于重命名）
     */
    private String groupName;

    /**
     * 新配置（克隆后的配置）的dataId（相当于重命名）
     */
    private String dataId;

    public CloneConfigItem() {
    }

    public CloneConfigItem(Long id, String groupName, String dataId) {
        this.id = id;
        this.groupName = groupName;
        this.dataId = dataId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "CloneConfigItem{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", dataId='" + dataId + '\'' +
                '}';
    }
}
