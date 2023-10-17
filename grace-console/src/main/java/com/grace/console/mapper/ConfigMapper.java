package com.grace.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.common.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ConfigMapper
 *
 * @author youzhengjie
 * @date 2023/10/17 23:38:44
 */
@Mapper
@Repository
public interface ConfigMapper extends BaseMapper<Config> {



}
