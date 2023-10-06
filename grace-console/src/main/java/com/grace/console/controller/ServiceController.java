package com.grace.console.controller;

import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.Service;
import com.grace.common.utils.Result;
import com.grace.console.service.SvcService;
import com.grace.console.vo.ServiceNameListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * service控制器
 *
 * @author youzhengjie
 * @date 2023/07/07 12:59:58
 */
@RestController
@RequestMapping(path = ParentMappingConstants.SERVICE_CONTROLLER)
public class ServiceController {

    @Autowired
    private SvcService svcService;

    /**
     * 分页获取服务名称列表
     */
    @GetMapping("/getServiceNameList")
    public Result<ServiceNameListVO> getServiceNameList(
            @RequestParam(value = "namespaceId", required = false, defaultValue = Constants.DEFAULT_STRING_NAMESPACE_ID) Long namespaceId,
            @RequestParam(value = "groupName", required = false, defaultValue = Constants.DEFAULT_GROUP_NAME) String groupName,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "8") Integer size) {
        // 对size的大小进行限制,防止一次性获取太多的数据（下面的代码意思是一次“最多”获取500条记录,如果size的值小于500,则size还是原来的值不变）
        size = Math.min(size,500);
        ServiceNameListVO serviceNameListVO = new ServiceNameListVO();
        serviceNameListVO.setServiceNameList(svcService.getAllServiceNameByPage(namespaceId,page,size));
        serviceNameListVO.setTotalCount(svcService.getServiceTotalCount());
        return Result.ok(serviceNameListVO);
    }


}
