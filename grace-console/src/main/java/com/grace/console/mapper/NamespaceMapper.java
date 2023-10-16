package com.grace.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.common.entity.Namespace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NamespaceMapper extends BaseMapper<Namespace> {



}
