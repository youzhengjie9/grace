package com.grace.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.entity.Config;
import com.grace.console.vo.ConfigListItemVO;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ConfigService接口
 *
 * @author youzhengjie
 * @date 2023/10/17 23:40:14
 */
public interface ConfigService extends IService<Config> {

    /**
     * 发布配置(如果不存在该配置则“新增配置”,如果存在该配置则“修改配置”)
     *
     * @param config  publishConfigDTO
     * @param request request
     * @return {@link Boolean}
     */
    Boolean publishConfig(Config config, HttpServletRequest request);


    /**
     * 获取配置
     *
     * @param namespaceId namespaceId
     * @param groupName groupName
     * @param dataId dataId
     * @return {@link Config}
     */
    Config getConfig(String namespaceId,String groupName,String dataId);


    /**
     * 分页获取ConfigListItemVO
     *
     * @param namespaceId namespaceId（精确搜索）
     * @param groupName   groupName（模糊搜素）
     * @param dataId      dataId（模糊搜索）
     * @param page page
     * @param size size
     * @return {@link List}<{@link ConfigListItemVO}>
     */
    List<ConfigListItemVO> getConfigListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size);

    /**
     * 获取配置总数
     *
     * @param namespaceId namespaceId（精确搜索）
     * @param groupName groupName（模糊搜素）
     * @param dataId dataId（模糊搜索）
     * @return int
     */
    int getConfigTotalCount(String namespaceId, String groupName, String dataId);

    /**
     * 删除配置
     *
     * @param namespaceId
     * @param groupName
     * @param dataId
     * @return {@link Boolean}
     */
    Boolean deleteConfig(String namespaceId, String groupName, String dataId);


}
