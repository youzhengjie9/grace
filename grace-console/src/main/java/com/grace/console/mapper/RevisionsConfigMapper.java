package com.grace.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.common.entity.RevisionsConfig;
import com.grace.console.vo.RevisionsConfigListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RevisionsConfigMapper extends BaseMapper<RevisionsConfig> {

    /**
     *
     * @param namespaceId namespaceId（“精确”搜索）
     * @param groupName   groupName（“精确”搜素）
     * @param dataId      dataId（“精确”搜索）
     * @param page page
     * @param size size
     * @return {@link List}<{@link RevisionsConfigListItemVO}>
     */
    List<RevisionsConfigListItemVO> getRevisionsConfigListItemVOByPage(@Param("namespaceId") String namespaceId,
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


}
