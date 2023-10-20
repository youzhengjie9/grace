package com.grace.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.entity.RevisionsConfig;
import com.grace.console.vo.RevisionsConfigListItemVO;

import javax.servlet.http.HttpServletRequest;
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


    /**
     * 获取指定的历史配置
     *
     * @param revisionsConfigId 历史配置id
     * @return {@link RevisionsConfig}
     */
    RevisionsConfig getRevisionsConfig(Long revisionsConfigId);

    /**
     * 回滚配置（原理和“发布配置”差不多）
     * <p>
     * 原理是: 通过历史配置（RevisionsConfig）去调用发布配置（com.grace.console.service.ConfigService.publishConfig）方法
     *
     * @param revisionsConfigId 历史配置id
     * @param request request
     * @return {@link Boolean}
     */
    Boolean rollbackConfig(Long revisionsConfigId, HttpServletRequest request);

}
