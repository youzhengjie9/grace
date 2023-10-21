package com.grace.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.common.entity.ConfigVersion;
import com.grace.console.vo.ConfigVersionListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 配置版本Mapper
 *
 * @author youzhengjie
 * @date 2023/10/21 14:44:09
 */
@Mapper
@Repository
public interface ConfigVersionMapper extends BaseMapper<ConfigVersion> {

    /**
     * 分页获取某个配置的所有配置版本列表,并将最新的配置版本放在前面（按雪花算法生成的主键id倒序排序）
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @param page page
     * @param size size
     * @return {@link List}<{@link ConfigVersionListItemVO}>
     */
    List<ConfigVersionListItemVO> getConfigVersionListItemVOByPage(@Param("namespaceId") String namespaceId,
                                                                   @Param("groupName") String groupName,
                                                                   @Param("dataId") String dataId,
                                                                   @Param("page") Integer page,
                                                                   @Param("size") Integer size);

    /**
     * 获取某个配置的所有配置版本总数
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @return int
     */
    int getConfigVersionTotalCount(@Param("namespaceId") String namespaceId,
                                   @Param("groupName") String groupName,
                                   @Param("dataId") String dataId);


    /**
     * 获取指定的配置版本
     *
     * @param configVersionId 配置版本id
     * @return {@link ConfigVersion}
     */
    ConfigVersion getConfigVersion(@Param("configVersionId") Long configVersionId);


    /**
     * 获取配置版本数据库表（sys_config_version）中指定命名空间(namespaceId)下面的所有dataId和groupName
     *
     * @param namespaceId namespaceId
     * @return {@link List}<{@link ConfigVersion}>
     */
    List<ConfigVersion> getAllDataIdAndGroupName(@Param("namespaceId") String namespaceId);


}
