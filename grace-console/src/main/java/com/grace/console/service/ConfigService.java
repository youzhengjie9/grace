package com.grace.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.entity.Config;
import com.grace.console.dto.PublishConfigDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

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
     * @param publishConfigDTO publishConfigDTO
     * @param request request
     * @return {@link Boolean}
     */
    Boolean publishConfig(PublishConfigDTO publishConfigDTO, HttpServletRequest request);


    /**
     * 获取配置
     *
     * @param namespaceId namespaceId
     * @param groupName groupName
     * @param dataId dataId
     * @return {@link Config}
     */
    Config getConfig(String namespaceId,String groupName,String dataId);


}
