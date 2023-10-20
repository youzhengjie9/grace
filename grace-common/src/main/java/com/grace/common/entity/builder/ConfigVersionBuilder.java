package com.grace.common.entity.builder;

import com.grace.common.entity.Config;
import com.grace.common.entity.ConfigVersion;
import com.grace.common.enums.ConfigOperationTypeEnum;
import com.grace.common.utils.IpUtils;
import com.grace.common.utils.SnowId;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配置版本的实体类的建造者类
 *
 * @author youzhengjie
 * @date 2023/10/19 15:19:00
 */
public class ConfigVersionBuilder implements Serializable {

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
     * 这个配置被执行了什么操作（比如新增、修改、删除）
     */
    private String operationType;

    /**
     * 操作这个配置的时间
     */
    private LocalDateTime operationTime;

    /**
     * 私有化构造器
     */
    private ConfigVersionBuilder() {

    }

    public static ConfigVersionBuilder newBuilder() {
        return new ConfigVersionBuilder();
    }

    public ConfigVersionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ConfigVersionBuilder namespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public ConfigVersionBuilder groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public ConfigVersionBuilder dataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public ConfigVersionBuilder content(String content) {
        this.content = content;
        return this;
    }

    public ConfigVersionBuilder md5(String md5) {
        this.md5 = md5;
        return this;
    }

    public ConfigVersionBuilder configDesc(String configDesc) {
        this.configDesc = configDesc;
        return this;
    }

    public ConfigVersionBuilder type(String type) {
        this.type = type;
        return this;
    }

    public ConfigVersionBuilder operationUserId(Long operationUserId) {
        this.operationUserId = operationUserId;
        return this;
    }

    public ConfigVersionBuilder operationUserIp(String operationUserIp) {
        this.operationUserIp = operationUserIp;
        return this;
    }

    public ConfigVersionBuilder operationType(String operationType) {
        this.operationType = operationType;
        return this;
    }

    public ConfigVersionBuilder operationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
        return this;
    }

    public ConfigVersion build() {
        return new ConfigVersion(id, namespaceId, groupName, dataId, content, md5, configDesc, type, operationUserId, operationUserIp, operationType, operationTime);
    }

    /**
     * 通过指定的参数直接构建ConfigVersion对象
     *
     * @param config                  config
     * @param versionId 配置版本id
     * @param configOperationTypeEnum configOperationTypeEnum
     * @param request                 request
     * @return {@link ConfigVersion}
     */
    public static ConfigVersion directBuild(Config config,Long versionId,ConfigOperationTypeEnum configOperationTypeEnum, HttpServletRequest request) {
        return ConfigVersionBuilder.newBuilder()
                .id(versionId)
                .namespaceId(config.getNamespaceId())
                .groupName(config.getGroupName())
                .dataId(config.getDataId())
                .content(config.getContent())
                .md5(config.getMd5())
                .configDesc(config.getConfigDesc())
                .type(config.getType())
                // TODO: 2023/10/21 operation暂未实现,这里直接写死
                .operationUserId(123456L)
                .operationUserIp(IpUtils.getIpAddrByHttpServletRequest(request))
                // 设置这个配置被执行了什么操作
                .operationType(configOperationTypeEnum.getOperationType())
                .operationTime(LocalDateTime.now())
                .build();
    }

}
