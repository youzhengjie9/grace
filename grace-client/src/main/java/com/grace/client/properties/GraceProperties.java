package com.grace.client.properties;

import com.grace.common.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * grace属性
 *
 * @author youzhengjie
 * @date 2023/06/16 10:05:17
 */
@ConfigurationProperties(prefix = "grace")
public class GraceProperties {

    /**
     * grace控制台（服务端）的ip地址（格式例如:127.0.0.1:8848）
     */
    private String consoleAddress = Constants.DEFAULT_CONSOLE_ADDR;

    /**
     * 登录grace控制台的用户名
     */
    private String username = Constants.DEFAULT_USERNAME;

    /**
     * 登录grace控制台的密码
     */
    private String password = Constants.DEFAULT_PASSWORD;

    public GraceProperties() {
    }

    public String getConsoleAddress() {
        return consoleAddress;
    }

    public void setConsoleAddress(String consoleAddress) {
        this.consoleAddress = consoleAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "GraceProperties{" +
                "consoleAddress='" + consoleAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
