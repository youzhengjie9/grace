package com.grace.common.enums;

/**
 * 配置中心的配置类型枚举
 *
 * @author youzhengjie
 * @date 2023/10/17 22:39:55
 */
public enum ConfigTypeEnum {

    /**
     * yaml类型
     */
    YAML("yaml"),

    /**
     * properties类型
     */
    PROPERTIES("properties"),

    /**
     * JSON类型
     */
    JSON("json"),

    /**
     * text类型
     */
    TEXT("text"),

    /**
     * xml类型
     */
    XML("xml");

    private String type;

    ConfigTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
