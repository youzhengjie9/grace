package com.grace.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.common.entity.SysInstance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * instance mapper
 *
 * @author youzhengjie
 * @date 2023/07/13 17:17:58
 */
@Mapper
@Repository
public interface InstanceMapper extends BaseMapper<SysInstance> {


}
