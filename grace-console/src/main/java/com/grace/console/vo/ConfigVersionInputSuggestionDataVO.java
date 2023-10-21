package com.grace.console.vo;

import java.util.Set;

/**
 * 前端的配置版本页面的dataId和groupName输入框建议的数据
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
    private Set<String> allDataIds;

    /**
     * 指定namespaceId下面的所有groupName（从配置版本（sys_config_version）数据库表中查询）
     * <p>
     * 这个数据是前端的配置版本页面的输入框（输入框的建议提示）所需要的
     */
    private Set<String> allGroupNames;

    public ConfigVersionInputSuggestionDataVO() {
    }

    public ConfigVersionInputSuggestionDataVO(Set<String> allDataIds, Set<String> allGroupNames) {
        this.allDataIds = allDataIds;
        this.allGroupNames = allGroupNames;
    }

    public Set<String> getAllDataIds() {
        return allDataIds;
    }

    public void setAllDataIds(Set<String> allDataIds) {
        this.allDataIds = allDataIds;
    }

    public Set<String> getAllGroupNames() {
        return allGroupNames;
    }

    public void setAllGroupNames(Set<String> allGroupNames) {
        this.allGroupNames = allGroupNames;
    }

    @Override
    public String toString() {
        return "ConfigVersionInputSuggestionDataVO{" +
                "allDataIds=" + allDataIds +
                ", allGroupNames=" + allGroupNames +
                '}';
    }
}
