package com.grace.common.entity.builder;

import com.baomidou.mybatisplus.annotation.TableField;
import com.grace.common.entity.RevisionsConfig;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 历史版本实体类的建造者类
 *
 * @author youzhengjie
 * @date 2023/10/19 15:19:00
 */
public class RevisionsConfigBuilder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * primary key
     */
    private Long id;

    /**
     * 历史配置所属的命名空间id
     */
    private String namespaceId;

    /**
     * 历史配置所属的分组名称
     */
    private String groupName;

    /**
     * dataId。也就是历史配置的名称
     */
    private String dataId;

    /**
     * 历史配置的内容
     */
    private String content;

    /**
     * 历史配置内容的MD5值
     */
    private String md5;

    /**
     * 历史配置的描述
     */
    private String configDesc;

    /**
     * 配置的类型（com.grace.common.enums.ConfigTypeEnum枚举类所定义的类型）
     */
    private String type;

    /**
     * 操作这个配置的用户的id
     */
    private Long operationUserId;

    /**
     * 操作这个配置的用户的ip地址
     */
    private String operationUserIp;

    /**
     * 这个配置被执行了什么操作（比如修改、删除）
     */
    private String operationType;

    /**
     * 操作这个配置的时间
     */
    private LocalDateTime operationTime;

    /**
     * 私有化构造器
     */
    private RevisionsConfigBuilder() {

    }

    public static RevisionsConfigBuilder newBuilder() {
        return new RevisionsConfigBuilder();
    }

    public RevisionsConfigBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public RevisionsConfigBuilder namespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public RevisionsConfigBuilder groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public RevisionsConfigBuilder dataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public RevisionsConfigBuilder content(String content) {
        this.content = content;
        return this;
    }

    public RevisionsConfigBuilder md5(String md5) {
        this.md5 = md5;
        return this;
    }

    public RevisionsConfigBuilder configDesc(String configDesc) {
        this.configDesc = configDesc;
        return this;
    }

    public RevisionsConfigBuilder type(String type) {
        this.type = type;
        return this;
    }

    public RevisionsConfigBuilder operationUserId(Long operationUserId) {
        this.operationUserId = operationUserId;
        return this;
    }

    public RevisionsConfigBuilder operationUserIp(String operationUserIp) {
        this.operationUserIp = operationUserIp;
        return this;
    }

    public RevisionsConfigBuilder operationType(String operationType) {
        this.operationType = operationType;
        return this;
    }

    public RevisionsConfigBuilder operationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
        return this;
    }

    public RevisionsConfig build() {
        return new RevisionsConfig(id, namespaceId, groupName, dataId, content, md5, configDesc, type, operationUserId, operationUserIp, operationType, operationTime);
    }
}
