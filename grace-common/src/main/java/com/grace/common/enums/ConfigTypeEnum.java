package com.grace.common.enums;

/**
 * 配置中心的配置类型枚举
 *
 * @author youzhengjie
 * @date 2023/10/17 22:39:55
 */
public enum ConfigTypeEnum {

    /**
     * txt类型
     */
    TXT("txt"),

    /**
     * JSON类型
     */
    JSON("json"),

    /**
     * xml类型
     */
    XML("xml"),

    /**
     * yaml类型
     */
    YAML("yaml"),

    /**
     * yml类型
     */
    YML("yml"),

    /**
     * properties类型
     */
    PROPERTIES("properties");

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
