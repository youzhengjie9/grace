package com.grace.console.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配置列表项vo
 *
 * @author youzhengjie
 * @date 2023/10/18 11:15:55
 */
public class ConfigListItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置的id（可以当成是摆设,如果我们想操作config,可以通过namespaceId、groupName、dataId共同作为条件来定位config）
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    private Long id;

    /**
     * 配置所属的命名空间id（注意: 这个属性一旦放入数据库则不能修改）
     */
    private String namespaceId;

    /**
     * 配置所属的分组名称（注意: 这个属性一旦放入数据库则不能修改）
     */
    private String groupName;

    /**
     * dataId。也就是配置的名称（注意: 这个属性一旦放入数据库则不能修改）
     */
    private String dataId;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 最后一次修改该配置的时间
     */
    private LocalDateTime lastUpdateTime;

    public ConfigListItemVO() {
    }

    public ConfigListItemVO(Long id, String namespaceId, String groupName, String dataId, String type, LocalDateTime lastUpdateTime) {
        this.id = id;
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.dataId = dataId;
        this.type = type;
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "ConfigListItemVO{" +
                "id=" + id +
                ", namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", dataId='" + dataId + '\'' +
                ", type='" + type + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
