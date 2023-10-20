package com.grace.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.common.entity.ConfigVersion;
import com.grace.console.vo.ConfigVersionListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConfigVersionMapper extends BaseMapper<ConfigVersion> {

    /**
     * 分页获取RevisionsConfigListItemVO（并按id（这个id是雪花算法id,是自增的）倒序排序,目的是将最新的历史配置放在前面）
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @param page page
     * @param size size
     * @return {@link List}<{@link ConfigVersionListItemVO}>
     */
    List<ConfigVersionListItemVO> getRevisionsConfigListItemVOByPage(@Param("namespaceId") String namespaceId,
                                                                     @Param("groupName") String groupName,
                                                                     @Param("dataId") String dataId,
                                                                     @Param("page") Integer page,
                                                                     @Param("size") Integer size);

    /**
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @return int
     */
    int getRevisionsConfigTotalCount(@Param("namespaceId") String namespaceId,
                                     @Param("groupName") String groupName,
                                     @Param("dataId") String dataId);


    /**
     * 获取指定的历史配置
     *
     * @param revisionsConfigId 历史配置id
     * @return {@link ConfigVersion}
     */
    ConfigVersion getRevisionsConfig(@Param("revisionsConfigId") Long revisionsConfigId);

}
