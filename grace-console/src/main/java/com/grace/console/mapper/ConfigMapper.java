package com.grace.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.common.entity.Config;
import com.grace.console.vo.ConfigListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ConfigMapper
 *
 * @author youzhengjie
 * @date 2023/10/17 23:38:44
 */
@Mapper
@Repository
public interface ConfigMapper extends BaseMapper<Config> {

    /**
     * 分页获取ConfigListItemVO
     *
     * @param namespaceId namespaceId（精确搜索）
     * @param groupName   groupName（模糊搜素）
     * @param dataId      dataId（模糊搜索）
     * @param page        page
     * @param size        size
     * @return {@link List}<{@link ConfigListItemVO}>
     */
    List<ConfigListItemVO> getConfigListItemVOByPage(@Param("namespaceId") String namespaceId,
                                                     @Param("groupName") String groupName,
                                                     @Param("dataId") String dataId,
                                                     @Param("page") Integer page,
                                                     @Param("size") Integer size,
                                                     @Param("fuzzySearch") Boolean fuzzySearch);

    /**
     * 获取指定命名空间下的配置总数
     *
     * @param namespaceId namespaceId（精确搜索）
     * @return int
     */
    int getConfigTotalCountByNamespaceId(@Param("namespaceId") String namespaceId);

    /**
     * 获取配置总数
     *
     * @param namespaceId namespaceId（精确搜索）
     * @param groupName   groupName（模糊搜素）
     * @param dataId      dataId（模糊搜索）
     * @return int
     */
    int getConfigTotalCount(@Param("namespaceId") String namespaceId,
                            @Param("groupName") String groupName,
                            @Param("dataId") String dataId,
                            @Param("fuzzySearch") Boolean fuzzySearch);


    /**
     * 删除配置
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜索素）
     * @param dataId      dataId（“精确”搜索）
     * @return {@link Boolean}
     */
    int deleteConfig(@Param("namespaceId") String namespaceId,
                     @Param("groupName") String groupName,
                     @Param("dataId") String dataId);

    /**
     * 获取当前配置的版本id
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜索素）
     * @param dataId      dataId（“精确”搜索）
     * @return long
     */
    Long getCurrentVersionId(@Param("namespaceId") String namespaceId,
                             @Param("groupName") String groupName,
                             @Param("dataId") String dataId);


}
