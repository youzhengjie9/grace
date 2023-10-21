package com.grace.console.utils;

import com.grace.common.utils.PageData;
import com.grace.console.vo.ConfigVersionListItemVO;

import java.util.List;
import java.util.Set;

/**
 * 配置版本列表分页数据,专门给ConfigVersionController接口的getConfigVersionList方法使用。
 * <p>
 * 这个类是PageData类的扩展
 */
public class ConfigVersionListPageData extends PageData<com.grace.console.vo.ConfigVersionListItemVO> {

    /**
     * 当前配置的版本id
     */
    private Long currentVersionId;

    public Long getCurrentVersionId() {
        return currentVersionId;
    }

    public void setCurrentVersionId(Long currentVersionId) {
        this.currentVersionId = currentVersionId;
    }

    @Override
    public String toString() {
        return "ConfigVersionListPageData{" +
                "currentVersionId=" + currentVersionId +
                '}';
    }
}
