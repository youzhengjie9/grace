package com.grace.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.common.entity.Namespace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 命名空间mapper
 *
 * @author youzhengjie
 * @date 2023/07/02 17:25:03
 */
@Mapper
@Repository
public interface NamespaceMapper extends BaseMapper<Namespace> {




}
