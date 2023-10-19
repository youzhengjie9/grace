package com.grace.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.entity.RevisionsConfig;
import com.grace.console.vo.RevisionsConfigListItemVO;

import java.util.List;

/**
 * revisions config service
 *
 * @author youzhengjie
 * @date 2023/10/19 15:09:52
 */
public interface RevisionsConfigService extends IService<RevisionsConfig> {

    /**
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @param page page
     * @param size size
     * @return {@link List}<{@link RevisionsConfigListItemVO}>
     */
    List<RevisionsConfigListItemVO> getRevisionsConfigListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size);

    /**
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @return int
     */
    int getRevisionsConfigTotalCount(String namespaceId, String groupName, String dataId);





}
