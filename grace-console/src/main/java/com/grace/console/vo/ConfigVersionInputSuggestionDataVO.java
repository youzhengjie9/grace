package com.grace.console.vo;

import java.util.Set;

/**
 * 前端的配置版本页面的输入框建议的数据
 *
 * @author youzhengjie
 * @date 2023/10/21 18:18:43
 */
public class ConfigVersionInputSuggestionDataVO {

    /**
     * 指定namespaceId下面的所有dataId（从配置版本（sys_config_version）数据库表中查询）
     * <p>
     * 这个数据是前端的配置版本页面的输入框（输入框的建议提示）所需要的
     */
    private Set<String> allDataId;

    /**
     * 指定namespaceId下面的所有groupName（从配置版本（sys_config_version）数据库表中查询）
     * <p>
     * 这个数据是前端的配置版本页面的输入框（输入框的建议提示）所需要的
     */
    private Set<String> allGroupName;

    public ConfigVersionInputSuggestionDataVO() {
    }

    public ConfigVersionInputSuggestionDataVO(Set<String> allDataId, Set<String> allGroupName) {
        this.allDataId = allDataId;
        this.allGroupName = allGroupName;
    }

    public Set<String> getAllDataId() {
        return allDataId;
    }

    public void setAllDataId(Set<String> allDataId) {
        this.allDataId = allDataId;
    }

    public Set<String> getAllGroupName() {
        return allGroupName;
    }

    public void setAllGroupName(Set<String> allGroupName) {
        this.allGroupName = allGroupName;
    }

    @Override
    public String toString() {
        return "ConfigVersionInputSuggestionDataVO{" +
                "allDataId=" + allDataId +
                ", allGroupName=" + allGroupName +
                '}';
    }
}
