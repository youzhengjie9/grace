package com.grace.client.config.properties;

import com.grace.client.config.ConfigService;
import com.grace.client.misc.LoginService;
import com.grace.client.misc.TokenStorage;

/**
 * 由于使用ContextRefresher.refresh方法会重新加载GracePropertySourceLocator类,
 * 但是不会加载application.yml的配置（会加载bootstrap.yml），导致application.yml的配置此时获取不到,
 * 所以要把配置属性存储到这里,防止配置丢失问题。
 *
 * @author youzhengjie
 * @date 2023/10/28 22:19:10
 */
public class GracePropertySourceLocatorProperties {

    private ConfigService configService;

    private LoginService loginService;

    private final TokenStorage tokenStorage = TokenStorage.getSingleton();

    private String consoleAddress;

    private String namespaceId;

    private String groupName;

    private String configType;

    private String springApplicationName;

    private String springProfilesActive;

    private String dataId;

    private static final GracePropertySourceLocatorProperties gracePropertySourceLocatorProperties =
            new GracePropertySourceLocatorProperties();

    /**
     * 单例
     */
    private GracePropertySourceLocatorProperties(){

    }

    public static GracePropertySourceLocatorProperties getSingleton(){
        return gracePropertySourceLocatorProperties;
    }

    public ConfigService getConfigService() {
        return gracePropertySourceLocatorProperties.configService;
    }

    public void setConfigService(ConfigService configService) {
        gracePropertySourceLocatorProperties.configService = configService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public TokenStorage getTokenStorage() {
        return tokenStorage;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public String getConsoleAddress() {
        return gracePropertySourceLocatorProperties.consoleAddress;
    }

    public void setConsoleAddress(String consoleAddress) {
        gracePropertySourceLocatorProperties.consoleAddress = consoleAddress;
    }

    public String getNamespaceId() {
        return gracePropertySourceLocatorProperties.namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        gracePropertySourceLocatorProperties.namespaceId = namespaceId;
    }

    public String getGroupName() {
        return gracePropertySourceLocatorProperties.groupName;
    }

    public void setGroupName(String groupName) {
        gracePropertySourceLocatorProperties.groupName = groupName;
    }

    public String getConfigType() {
        return gracePropertySourceLocatorProperties.configType;
    }

    public void setConfigType(String configType) {
        gracePropertySourceLocatorProperties.configType = configType;
    }

    public String getSpringApplicationName() {
        return gracePropertySourceLocatorProperties.springApplicationName;
    }

    public void setSpringApplicationName(String springApplicationName) {
        gracePropertySourceLocatorProperties.springApplicationName = springApplicationName;
    }

    public String getSpringProfilesActive() {
        return gracePropertySourceLocatorProperties.springProfilesActive;
    }

    public void setSpringProfilesActive(String springProfilesActive) {
        gracePropertySourceLocatorProperties.springProfilesActive = springProfilesActive;
    }

    public String getDataId() {
        return gracePropertySourceLocatorProperties.dataId;
    }

    public void setDataId(String dataId) {
        gracePropertySourceLocatorProperties.dataId = dataId;
    }
}
