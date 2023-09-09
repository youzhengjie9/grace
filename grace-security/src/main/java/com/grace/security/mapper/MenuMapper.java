package com.grace.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * menu mapper
 *
 * @author youzhengjie
 * @date 2023/09/08 18:08:26
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据userId获取用户所有的权限标识permission（不包括type=0（目录）的权限标识,因为目录的权限标识一直都为null,所以没有任何意义）
     * <p>
     * 作用: 这个方法的返回结果可以直接设置给GraceUser对象中的permissons属性
     *
     * @param userId userId
     * @return {@link List}<{@link String}>
     */
    List<String> getAllPermissionsByUserId(@Param("userId") long userId);


}
