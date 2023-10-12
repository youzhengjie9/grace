package com.grace.console.controller;

import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.Service;
import com.grace.common.utils.Result;
import com.grace.console.core.GroupManager;
import com.grace.console.dto.ServiceDTO;
import com.grace.console.vo.ServiceDetailVO;
import com.grace.console.vo.ServiceListItem;
import com.grace.console.vo.ServiceListVO;
import com.grace.console.vo.ServiceNameListVO;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * service控制器
 *
 * @author youzhengjie
 * @date 2023/07/07 12:59:58
 */
@RestController
@RequestMapping(path = ParentMappingConstants.SERVICE_CONTROLLER)
public class ServiceController {

    private final GroupManager groupManager = GroupManager.getInstance();

    /**
     * 创建service
     *
     * @param serviceDTO serviceDTO
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(path = "/createService")
    public Result<Boolean> createService(@RequestBody ServiceDTO serviceDTO){
        // 校验必填属性
        serviceDTO.validateRequired();
        // 创建service并返回结果
        return Result.ok(groupManager.createServiceByServiceDTO(serviceDTO));
    }
    /**
     * 分页获取服务列表
     *
     * @param namespaceId namespaceId
     * @param hideEmptyService 是否隐藏空服务（也就是说不统计没有instance的service）
     * @param page 当前页（最小页是: 1）
     * @param size 每一页的大小
     * @return {@link Result}<{@link ServiceNameListVO}>
     */
    @GetMapping("/getServiceList")
    public Result<ServiceListVO> getServiceList(
            @RequestParam(value = "namespaceId", required = false, defaultValue = Constants.DEFAULT_NAMESPACE_ID) String namespaceId,
            @RequestParam(value = "hideEmptyService", required = false, defaultValue = "true") boolean hideEmptyService,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        // 如果 page < 1 ,则要把page恢复成 1 ,因为page的最小值就为 1
        if(page < 1){
            page = 1;
        }
        // 如果 size < 0 ,则要把size恢复成 0 ,因为size的最小值为 0
        if(size < 0 ){
            size = 0;
        }
        // 对size的大小进行限制,防止一次性获取太多的数据（下面的代码意思是一次“最多”获取500条记录,如果size的值小于500,则size还是原来的值不变）
        size = Math.min(size,500);
        ServiceListVO serviceListVO = new ServiceListVO();
        // 获取所有符合条件的service(不进行分页)
        Set<Service> services = groupManager.getAllService(namespaceId, hideEmptyService);
        // 把Service集合进行分页
        Set<Service> pagedServices = servicesPage(services, page, size);
        // 设置“分页之前”的service总记录数
        serviceListVO.setTotalCount(services.size());
        List<ServiceListItem> pagedServiceList = new CopyOnWriteArrayList<>();
        // 将Set<Service>类型的pagedServices对象转成List<ServiceListItem>类型的对象
        for (Service pagedService : pagedServices) {
            ServiceListItem serviceListItem = new ServiceListItem();
            serviceListItem.setServiceName(pagedService.getServiceName());
            serviceListItem.setGroupName(pagedService.getGroupName());
            // 实例数
            serviceListItem.setInstanceCount(
                    pagedService.getAllInstance().size()
            );
            // 健康实例数
            serviceListItem.setHealthyInstanceCount(pagedService.getAllHealthyInstanceCount());
            // service触发保护阈值标志（判断当前service是否触发了保护阈值）
            serviceListItem.setTriggerProtectThresholdFlag(
                    pagedService.getServiceTriggerProtectThresholdFlag()? "true" : "false"
            );
            pagedServiceList.add(serviceListItem);
        }
        serviceListVO.setPagedServiceList(pagedServiceList);

        return Result.ok(serviceListVO);
    }


    /**
     * 分页获取服务名称列表
     */
    @GetMapping("/getServiceNameList")
    public Result<ServiceNameListVO> getServiceNameList(
            @RequestParam(value = "namespaceId", required = false, defaultValue = Constants.DEFAULT_NAMESPACE_ID) String namespaceId,
            @RequestParam(value = "groupName", required = false, defaultValue = Constants.DEFAULT_GROUP_NAME) String groupName,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        // 对size的大小进行限制,防止一次性获取太多的数据（下面的代码意思是一次“最多”获取500条记录,如果size的值小于500,则size还是原来的值不变）
        size = Math.min(size,500);

        return Result.ok();
    }

    /**
     * 把Service集合进行分页（按照MySQL的分页思想）
     *
     * @param services services
     * @param page 当前页（最小页是: 1）
     * @param size 每一页的大小
     * @return {@link Collection}<{@link Service}>
     */
    private Set<Service> servicesPage(Set<Service> services, int page, int size) {
        // 如果service集合没有数据
        if(services == null || services.size() == 0){
            // 返回空集合
            return new CopyOnWriteArraySet<>();
        }
        // 如果需要分页的集合的大小 <= 每一页的大小
        if(services.size() <= size){
            // 直接不需要分页,返回原来需要分页的集合即可
            return services;
        }
        // 走到这里就需要分页了
        /*
        如果services.size=130
            page=1、size=50 ---> startIndex = 0 , endIndex= 50
            page=2、size=50 ---> startIndex = 50 , endIndex = 100
            page=3、size=50 ---> startIndex = 100 , endIndex = 150
         */
        // 分页开始的下标（很重要）
        int startIndex = (page-1)*size;
        // 分页结束的下标（很重要）
        int endIndex = startIndex+size;
        // 矫正分页结束的下标。如果services集合的大小 < endIndex,为了防止后面的遍历造成数组越界,所以要将分页结束的下标修改成services集合的大小
        if(services.size() < endIndex){
            endIndex = services.size();
        }
        int cur = 0;
        // 将set集合转成list集合（主要是list集合有索引）
        final List<Service> serviceList = new CopyOnWriteArrayList<>(services);
        // 被分页后的service集合
        final Set<Service> pagedServices = new CopyOnWriteArraySet<>();
        // 在这个范围的数据就是我们分页的结果了,只需要将这些数据保存起来即可
        for (int i = startIndex; i < endIndex; i++) {
            pagedServices.add(serviceList.get(i));
        }
        return pagedServices;
    }


    @DeleteMapping("/deleteService")
    public Result<String> deleteService(
            @RequestParam(value = "namespaceId",required = false, defaultValue = Constants.DEFAULT_NAMESPACE_ID) String namespaceId,
            @RequestParam(value = "groupName", required = false, defaultValue = Constants.DEFAULT_GROUP_NAME) String groupName,
            @RequestParam(value = "serviceName") String serviceName){


        return Result.ok();
    }

    @GetMapping("/getServiceDetail")
    public Result<ServiceDetailVO> getServiceDetail(
            @RequestParam(value = "namespaceId",required = false, defaultValue = Constants.DEFAULT_NAMESPACE_ID) String namespaceId,
            @RequestParam(value = "groupName", required = false, defaultValue = Constants.DEFAULT_GROUP_NAME) String groupName,
            @RequestParam(value = "serviceName") String serviceName){

        return Result.ok(groupManager.getServiceDetail(namespaceId, groupName, serviceName));
    }







}
