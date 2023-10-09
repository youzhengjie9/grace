package com.grace.common.entity.builder;

import cn.hutool.core.bean.BeanUtil;
import com.grace.common.entity.Instance;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Instance建造者类
 *
 * @author youzhengjie
 * @date 2023/10/05 17:11:56
 */
public class InstanceBuilder {

    /**
     * 主键,服务实例id
     */
    private String instanceId;

    /**
     * 该实例所属的服务名
     */
    private String serviceName;

    /**
     * 该实例的ip地址
     */
    private String ipAddr;

    /**
     * 该实例的端口号
     */
    private int port;

    /**
     * 该实例的权重
     */
    private double weight = 1.00;

    /**
     * 实例是否健康
     */
    private boolean healthy = true;

    /**
     * 是否为临时实例
     */
    private boolean ephemeral = true;

    /**
     * 该实例的元数据
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建建造者对象
     *
     * @return {@link InstanceBuilder}
     */
    public static InstanceBuilder newBuilder() {
        return new InstanceBuilder();
    }

    public InstanceBuilder instanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public InstanceBuilder serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public InstanceBuilder ipAddr(String ipAddr) {
        this.ipAddr = ipAddr;
        return this;
    }

    public InstanceBuilder port(int port) {
        this.port = port;
        return this;
    }

    public InstanceBuilder weight(double weight) {
        this.weight = weight;
        return this;
    }

    public InstanceBuilder healthy(boolean healthy) {
        this.healthy = healthy;
        return this;
    }

    public InstanceBuilder ephemeral(boolean ephemeral) {
        this.ephemeral = ephemeral;
        return this;
    }

    public InstanceBuilder metadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public InstanceBuilder createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 构建对象
     */
    public Instance build() {
        return new Instance(instanceId, serviceName, ipAddr, port, weight, healthy, ephemeral, metadata, createTime);
    }

}