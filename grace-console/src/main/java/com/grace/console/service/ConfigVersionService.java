package com.grace.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.entity.ConfigVersion;
import com.grace.console.vo.ConfigVersionListItemVO;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 配置版本service
 *
 * @author youzhengjie
 * @date 2023/10/19 15:09:52
 */
public interface ConfigVersionService extends IService<ConfigVersion> {

    /**
     * 页获取某个配置的所有配置版本列表,并将最新的配置版本放在前面（按雪花算法生成的主键id倒序排序）
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @param page page
     * @param size size
     * @return {@link List}<{@link ConfigVersionListItemVO}>
     */
    List<ConfigVersionListItemVO> getConfigVersionListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size);

    /**
     * 获取某个配置的所有配置版本总数
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @return int
     */
    int getConfigVersionTotalCount(String namespaceId, String groupName, String dataId);


    /**
     * 获取指定的配置版本
     *
     * @param configVersionId 配置版本id
     * @return {@link ConfigVersion}
     */
    ConfigVersion getConfigVersion(Long configVersionId);

    /**
     * 获取配置版本数据库表（sys_config_version）中指定命名空间(namespaceId)下面的所有dataId和groupName,并去重
     *
     * @param namespaceId namespaceId
     * @return {@link List}<{@link ConfigVersion}>
     */
    Map<String, Set<String>> getAllDataIdAndGroupName(String namespaceId);

    /**
     * 回滚配置
     *
     * @param configVersionId 配置版本id
     * @param request request
     * @return {@link Boolean}
     */
    Boolean rollbackConfig(Long configVersionId, HttpServletRequest request);

}
