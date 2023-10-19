package com.grace.console.dto;

import com.grace.common.entity.Config;
import com.grace.common.entity.builder.ConfigBuilder;
import com.grace.common.enums.ConfigTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class PublishConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置所属的命名空间id（注意: 这个属性“不能被修改”。在这里的作用是结合groupName和dataId属性判断该配置是要“新增”还是“修改”）
     */
    private String namespaceId;

    /**
     * 配置所属的分组名称（注意: 这个属性“不能被修改”。在这里的作用是结合namespaceId和dataId属性判断该配置是要“新增”还是“修改”）
     */
    private String groupName;

    /**
     * dataId。也就是配置的名称（注意: 这个属性“不能被修改”。在这里的作用是结合namespaceId和groupName属性判断该配置是要“新增”还是“修改”）
     */
    private String dataId;

    /**
     * 配置的内容
     */
    private String content;

    /**
     * 配置的描述
     */
    private String configDesc;

    /**
     * 配置的类型（com.grace.common.enums.ConfigTypeEnum枚举类所定义的类型）
     */
    private String type;


    public PublishConfigDTO() {
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 校验当前对象的必填属性是否为空,为空则抛出异常
     */
    public void validateRequired(){
        if (StringUtils.isBlank(groupName)) {
            throw new RuntimeException("groupName不能为空");
        }
        if (StringUtils.isBlank(dataId)) {
            throw new RuntimeException("dataId不能为空");
        }
        // 校验当前对象的配置类型是否合法
        checkConfigType();
    }

    /**
     * 校验当前对象的配置类型是否合法，如果不合法则抛出异常
     */
    private void checkConfigType(){
        boolean flag = false;
        // type不能为空
        if (StringUtils.isBlank(type)) {
            throw new RuntimeException("type不能为空");
        }
        // 校验type必须是com.grace.common.enums.ConfigTypeEnum枚举类定义的枚举值
        ConfigTypeEnum[] configTypeEnums = ConfigTypeEnum.values();
        for (ConfigTypeEnum configTypeEnum : configTypeEnums) {
            // 如果type是com.grace.common.enums.ConfigTypeEnum枚举类定义的枚举值,则说明该类型合法
            if(type.equalsIgnoreCase(configTypeEnum.getType())){
                flag = true;
                break;
            }
        }
        // flag=false,则说明type不合法
        if(!flag){
            throw new RuntimeException("type不合法");
        }
    }

    /**
     * 填充默认值（注意: 不会填充必填属性！）
     */
    public void fillDefaultValue() {
        if (StringUtils.isBlank(namespaceId)) {
            namespaceId = "";
        }
        if(StringUtils.isBlank(content)){
            content="";
        }
        if(StringUtils.isBlank(configDesc)){
            configDesc="";
        }
    }

    /**
     * 通过PublishConfigDTO对象构建Config对象
     *
     * @return {@link Config}
     */
    public Config buildConfigByPublishConfigDTO() {

        return ConfigBuilder.newBuilder()
                .namespaceId(namespaceId)
                .groupName(groupName)
                .dataId(dataId)
                .content(content)
                .configDesc(configDesc)
                .type(type)
                .build();
    }


}
