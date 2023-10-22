package com.grace.console.controller;

import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.ConfigVersion;
import com.grace.common.utils.Result;
import com.grace.console.service.ConfigService;
import com.grace.console.service.ConfigVersionService;
import com.grace.console.utils.ConfigVersionListPageData;
import com.grace.console.vo.ConfigVersionInputSuggestionDataVO;
import com.grace.console.vo.ConfigVersionListItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * 配置版本控制器
 *
 * @author youzhengjie
 * @date 2023/10/19 17:22:30
 */
@RestController
@RequestMapping(path = ParentMappingConstants.CONFIG_VERSION_CONTROLLER)
public class ConfigVersionController {

    @Autowired
    private ConfigService configService;
    @Autowired
    private ConfigVersionService configVersionService;

    /**
     * 获取指定配置的配置版本列表
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName（必填）
     * @param dataId      dataId（必填）
     * @param page        当前页（最小页是: 1）
     * @param size        每一页的大小（最小值为: 1）
     * @return {@link Result}<{@link ConfigVersionListPageData}<{@link ConfigVersionListItemVO}>>
     */
    @GetMapping(path = "/getConfigVersionList")
    public Result<ConfigVersionListPageData> getConfigVersionList(
            @RequestParam(value = "namespaceId", required = false, defaultValue = Constants.DEFAULT_NAMESPACE_ID) String namespaceId,
            @RequestParam(value = "groupName") String groupName,
            @RequestParam(value = "dataId") String dataId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        // 如果 page < 1 ,则要把page恢复成 1 ,因为page的最小值就为 1
        if(page < 1){
            page = 1;
        }
        // 如果 size <= 0 ,则要把size恢复成 1 ,因为size的最小值为 1
        if(size <= 0 ){
            size = 1;
        }
        // 将当前页转成MySQL分页的起始位置！
        page = (page-1)*size;
        // 对size的大小进行限制,防止一次性获取太多的数据（下面的代码意思是一次“最多”获取500条记录,如果size的值小于500,则size还是原来的值不变）
        size = Math.min(size,500);
        // 封装ConfigVersionListPageData
        ConfigVersionListPageData configVersionListPageData = new ConfigVersionListPageData();
        // 分页数据
        configVersionListPageData.setPagedList(configVersionService.getConfigVersionListItemVOByPage(namespaceId, groupName, dataId, page, size));
        configVersionListPageData.setTotalCount(configVersionService.getConfigVersionTotalCount(namespaceId, groupName, dataId));
        // 当前配置的版本id
        configVersionListPageData.setCurrentVersionId(configService.getCurrentVersionId(namespaceId, groupName, dataId));
        return Result.ok(configVersionListPageData);
    }

    /**
     * 获取前端的配置版本页面的输入框建议的数据
     *
     * @param namespaceId namespaceId
     * @return {@link Result}<{@link ConfigVersionInputSuggestionDataVO}>
     */
    @GetMapping("/getConfigVersionInputSuggestionData")
    public Result<ConfigVersionInputSuggestionDataVO> getConfigVersionInputSuggestionData(
            @RequestParam(value = "namespaceId",required = false,defaultValue = Constants.DEFAULT_NAMESPACE_ID) String namespaceId){

        ConfigVersionInputSuggestionDataVO configVersionInputSuggestionDataVO =
                new ConfigVersionInputSuggestionDataVO();
        // 获取配置版本数据库表（sys_config_version）中指定命名空间(namespaceId)下面的所有dataId和groupName,并去重
        Map<String, Set<String>> allDataIdAndGroupNameMap =
                configVersionService.getAllDataIdAndGroupName(namespaceId);
        // 将去重好的dataId和groupName放到configVersionInputSuggestionDataVO中
        configVersionInputSuggestionDataVO.setAllDataIds(allDataIdAndGroupNameMap.get("allDataId"));
        configVersionInputSuggestionDataVO.setAllGroupNames(allDataIdAndGroupNameMap.get("allGroupName"));

        return Result.ok(configVersionInputSuggestionDataVO);
    }

    /**
     * 获取指定的配置版本（只能通过配置版本id才能去获取）
     *
     * @param configVersionId 配置版本id
     * @return {@link Result}<{@link ConfigVersion}>
     */
    @GetMapping(path = "/getConfigVersion")
    public Result<ConfigVersion> getConfigVersion(@RequestParam("configVersionId") Long configVersionId){

        return Result.ok(configVersionService.getConfigVersion(configVersionId));
    }

    /**
     * 回滚配置
     * <p>
     * 原理是:（有（对应的配置存在）就修改、没有（对应的配置不存在）就创建）
     *  通过配置版本（ConfigVersion）存储的namespaceId、groupName、dataId去找到配置（Config）,
     *      1: 如果找不到该配置（Config为null）则“将配置版本对象逆向构建出配置(Config)对象,”,
     *      2: 如果找到了该配置（config不为null）则“进行修改配置操作”
     *
     * @param configVersionId 配置版本id
     * @param request request
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(path = "/rollbackConfig")
    public Result<Boolean> rollbackConfig(@RequestParam("configVersionId") Long configVersionId,
                                          HttpServletRequest request){

        return Result.ok(configVersionService.rollbackConfig(configVersionId, request));
    }

}
