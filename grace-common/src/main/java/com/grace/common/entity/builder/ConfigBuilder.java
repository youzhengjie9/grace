package com.grace.common.entity.builder;

import com.grace.common.entity.Config;

import java.time.LocalDateTime;


/**
 * Config建造者类
 *
 * @author youzhengjie
 * @date 2023/10/17 20:20:35
 */
public class ConfigBuilder {

    /**
     * 主键。配置的id
     */
    private Long id;

    /**
     * 配置所属的命名空间id（注意: 这个属性一旦放入数据库则不能修改）
     */
    private String namespaceId;

    /**
     * 配置所属的分组名称（注意: 这个属性一旦放入数据库则不能修改）
     */
    private String groupName;

    /**
     * dataId。也就是配置的名称（注意: 这个属性一旦放入数据库则不能修改）
     */
    private String dataId;

    /**
     * 配置的内容
     */
    private String content;

    /**
     * 配置内容的MD5值,用于判断配置文件是否被修改,只要content内容被修改了,那么其生成的MD5值一定是不同的。
     * 生成该MD5值的原理是: 将配置内容content先进行MD5加密,然后再将这个MD5加密后的值转换成16进制字符串。
     * 注意: 可通过com.grace.common.utils.MD5Utils#md5Hex(java.lang.String, java.lang.String)方法生成MD5
     */
    private String md5;

    /**
     * 配置的描述
     */
    private String configDesc;

    /**
     * 配置的类型（com.grace.common.enums.ConfigTypeEnum枚举类所定义的类型）
     */
    private String type;

    /**
     * 创建该配置的用户的id
     */
    private Long createUserId;

    /**
     * 创建该配置的用户的ip地址
     */
    private String createUserIp;

    /**
     * 创建该配置的时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次修改该配置的时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 创建建造者对象
     *
     * @return {@link ConfigBuilder}
     */
    public static ConfigBuilder newBuilder(){
        return new ConfigBuilder();
    }

    public ConfigBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ConfigBuilder namespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public ConfigBuilder groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public ConfigBuilder dataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public ConfigBuilder content(String content) {
        this.content = content;
        return this;
    }

    public ConfigBuilder md5(String md5) {
        this.md5 = md5;
        return this;
    }

    public ConfigBuilder configDesc(String configDesc) {
        this.configDesc = configDesc;
        return this;
    }

    public ConfigBuilder type(String type) {
        this.type = type;
        return this;
    }

    public ConfigBuilder createUserId(Long createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public ConfigBuilder createUserIp(String createUserIp) {
        this.createUserIp = createUserIp;
        return this;
    }

    public ConfigBuilder createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public ConfigBuilder lastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    /**
     * 构建对象
     */
    public Config build() {
        return new Config(id, namespaceId, groupName, dataId, content, md5, configDesc, type, createUserId, createUserIp, createTime, lastUpdateTime);
    }
}