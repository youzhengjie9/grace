package com.grace.common.entity.builder;

import cn.hutool.core.bean.BeanUtil;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service建造者类
 *
 * @author youzhengjie
 * @date 2023/10/06 00:13:56
 */
public class ServiceBuilder {

    /**
     * 命名空间id
     */
    private String namespaceId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 该service下的所有实例
     */
    private List<Instance> instances = new ArrayList<>();

    /**
     * 元数据
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建建造者对象
     *
     * @return {@link ServiceBuilder}
     */
    public static ServiceBuilder newBuilder(){
        return new ServiceBuilder();
    }

    public ServiceBuilder namespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public ServiceBuilder serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public ServiceBuilder groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public ServiceBuilder instances(List<Instance> instances) {
        this.instances = instances;
        return this;
    }

    public ServiceBuilder metadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public ServiceBuilder createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 构建对象
     */
    public Service build() {
        return BeanUtil.copyProperties(this, Service.class);
    }


}