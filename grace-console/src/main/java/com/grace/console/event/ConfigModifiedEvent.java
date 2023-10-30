package com.grace.console.event;

import org.springframework.context.ApplicationEvent;

/**
 * 定义配置被修改事件
 *
 * @author youzhengjie
 * @date 2023/10/29 19:53:44
 */
public class ConfigModifiedEvent extends ApplicationEvent {

    /**
     * 被修改的配置所在命名空间id
     */
    private final String namespaceId;

    /**
     * 被修改的配置的分组名称
     */
    private final String groupName;

    /**
     * 被修改的配置的dataId
     */
    private final String dataId;

    /**
     * @param source 通常是this
     */
    public ConfigModifiedEvent(Object source, String namespaceId, String groupName, String dataId) {
        super(source);
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.dataId = dataId;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDataId() {
        return dataId;
    }

    @Override
    public String toString() {
        return "ConfigModifiedEvent{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", dataId='" + dataId + '\'' +
                '}';
    }
}
