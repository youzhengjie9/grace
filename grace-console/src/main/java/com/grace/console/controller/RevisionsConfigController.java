package com.grace.console.controller;

import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.RevisionsConfig;
import com.grace.common.utils.PageData;
import com.grace.common.utils.Result;
import com.grace.console.service.RevisionsConfigService;
import com.grace.console.vo.RevisionsConfigListItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 历史配置控制器
 *
 * @author youzhengjie
 * @date 2023/10/19 17:22:30
 */
@RestController
@RequestMapping(path = ParentMappingConstants.REVISIONS_CONFIG_CONTROLLER)
public class RevisionsConfigController {

    @Autowired
    private RevisionsConfigService revisionsConfigService;

    /**
     * 获取指定配置的历史配置列表
     *
     * @param namespaceId namespaceId
     * @param groupName groupName（必填）
     * @param dataId dataId（必填）
     * @param page 当前页（最小页是: 1）
     * @param size 每一页的大小（最小值为: 1）
     * @return {@link Result}<{@link PageData}<{@link RevisionsConfigListItemVO}>>
     */
    @GetMapping(path = "/getRevisionsConfigList")
    public Result<PageData<RevisionsConfigListItemVO>> getRevisionsConfigList(
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
        PageData<RevisionsConfigListItemVO> pageData = new PageData<>();
        pageData.setPagedList(revisionsConfigService.getRevisionsConfigListItemVOByPage(namespaceId, groupName, dataId, page, size));
        pageData.setTotalCount(revisionsConfigService.getRevisionsConfigTotalCount(namespaceId, groupName, dataId));
        return Result.ok(pageData);
    }

    /**
     * 获取指定的历史配置（只能通过历史配置id才能去获取）
     *
     * @param revisionsConfigId 历史配置id
     * @return {@link Result}<{@link RevisionsConfig}>
     */
    @GetMapping(path = "/getRevisionsConfig/{revisionsConfigId}")
    public Result<RevisionsConfig> getRevisionsConfig(@PathVariable("revisionsConfigId") Long revisionsConfigId){

        return Result.ok(revisionsConfigService.getRevisionsConfig(revisionsConfigId));
    }

    /**
     * 回滚配置（原理和“发布配置”差不多）
     * <p>
     * 原理是: 通过历史配置（RevisionsConfig）去调用发布配置（com.grace.console.service.ConfigService.publishConfig）方法
     * <p>
     * 通过历史配置（RevisionsConfig）存储的namespaceId、groupName、dataId去找到配置（Config）,
     * 如果找不到该配置（Config为null）则“将历史配置对象构建出配置对象,然后进行发布配置（publishConfig）”,
     * 如果找到了该配置（config不为null）则“进行修改配置操作（也是publishConfig）”
     *
     * @param revisionsConfigId 历史配置id
     * @param request request
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(path = "/rollbackConfig/{revisionsConfigId}")
    public Result<Boolean> rollbackConfig(@PathVariable("revisionsConfigId") Long revisionsConfigId,
                                          HttpServletRequest request){

        return
    }

}
