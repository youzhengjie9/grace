package com.grace.client.config.core;

import com.grace.client.config.ConfigService;
import com.grace.client.config.cache.CacheConfigManager;
import com.grace.client.config.factory.ConfigServiceFactory;
import com.grace.client.config.properties.GracePropertySourceLocatorProperties;
import com.grace.client.exception.GraceClientLoginException;
import com.grace.client.misc.LoginService;
import com.grace.client.misc.factory.LoginServiceFactory;
import com.grace.client.utils.JsonUtils;
import com.grace.client.utils.PropertiesUtils;
import com.grace.client.utils.YamlUtils;
import com.grace.common.dto.UserLoginDTO;
import com.grace.common.entity.Config;
import com.grace.common.enums.ConfigTypeEnum;
import com.grace.common.vo.TokenVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Map;

/**
 * 自定义PropertySourceLocator,用于加载自定义配置文件,将该配置文件的属性加载到environment中
 * <p>
 * 作用是：当SpringBoot项目一启动（此时容器还没有加载成功，所有bean对象都没有创建，此时无法使用@Autowired），
 * 等到application.yml和bootstrap.yml本地配置文件都加载成功后,就会调用该类的locate方法,在该类的locate方法可以加载
 * 自定义配置文件,并将该自定义配置文件的属性变成bean。如果application.yml、bootstrap.yml和
 * PropertySourceLocator加载的自定义配置文件有相同的属性名、不同的属性值,则只有PropertySourceLocator的属性值会生效。
 * (例如：application.yml（age属性=10）,bootstrap.yml(age属性=20),PropertySourceLocator(age属性=30) ,
 * 当springboot容器启动成功后,age属性=30)
 * <p>
 * 配置文件的加载顺序: bootstrap.yml > application.yml > PropertySourceLocator加载的自定义配置文件 （后面加载的配置会替代前面加载的配置）
 * <p>
 * 注意: 该类必须配置在META-INF的spring.factories的org.springframework.cloud.bootstrap.BootstrapConfiguration下,配置到其他地方会导致无法加载
 * @author youzhengjie
 * @date 2023/10/27 08:55:09
 */
public class GracePropertySourceLocator implements PropertySourceLocator {

    private final Logger log = LoggerFactory.getLogger(GracePropertySourceLocator.class);

    /**
     * 由于使用ContextRefresher.refresh方法会重新加载GracePropertySourceLocator类,
     * 但是不会加载application.yml的配置（会加载bootstrap.yml），导致application.yml的配置此时获取不到,
     * 所以要把配置属性存储到这里,防止配置丢失问题。
     */
    private final GracePropertySourceLocatorProperties properties =
            GracePropertySourceLocatorProperties.getSingleton();

    /**
     * 初始化变量
     *
     * @param environment environment
     */
    public void initVariable(ConfigurableEnvironment configurableEnvironment){
        // 因为在这里所有bean都没有创建,不能使用@Autowired注入,但是本地配置文件application.yml和bootstrap.yml却已经加载成功
        // 所以我们可以通过ConfigurableEnvironment对象获取本地配置文件的属性（比如:grace配置中心地址）

        // 如果 consoleAddress 为null
        if(properties.getConsoleAddress() == null) {
            // 从本地配置文件bootstrap.yml(或者application.yml)中获取配置中心地址
            properties.setConsoleAddress(configurableEnvironment.getProperty("grace.console-address"));
        }
        // 如果 namespaceId 为null
        if(properties.getNamespaceId() == null) {
            // 从本地配置文件bootstrap.yml(或者application.yml)中获取该配置所在的的命名空间id
            properties.setNamespaceId(configurableEnvironment.getProperty("grace.config.namespace-id"));
        }
        // 如果 groupName 为null
        if(properties.getGroupName() == null) {
            // 从本地配置文件bootstrap.yml(或者application.yml)中获取该配置所在的的分组名称
            properties.setGroupName(configurableEnvironment.getProperty("grace.config.group-name"));
        }
        // 如果 configType 为null
        if(properties.getConfigType() == null) {
            // 从本地配置文件bootstrap.yml(或者application.yml)中获取该配置的类型
            properties.setConfigType(configurableEnvironment.getProperty("grace.config.config-type",
                    ConfigTypeEnum.class).getType());
        }
        // 如果 configService 为null
        if(properties.getConfigService() == null) {
            // 创建ConfigService对象,用于从配置中心拿到对应的配置内容
            properties.setConfigService(ConfigServiceFactory.createConfigService(properties.getConsoleAddress()));
        }
        // 如果 loginService 为null
        if(properties.getLoginService() == null ){
            // 创建loginService
            properties.setLoginService(LoginServiceFactory.createLoginService(properties.getConsoleAddress()));
        }
        // 如果 springApplicationName 为null
        if(properties.getSpringApplicationName() == null) {
            // 从本地配置文件bootstrap.yml(或者application.yml)中获取spring.application.name
            properties.setSpringApplicationName(configurableEnvironment.getProperty("spring.application.name"));
        }
        // 如果 springProfilesActive 为null
        if(properties.getSpringProfilesActive() == null) {
            // 从本地配置文件bootstrap.yml(或者application.yml)中获取spring.profiles.active
            properties.setSpringProfilesActive(configurableEnvironment.getProperty("spring.profiles.active"));
        }
        // 如果 dataId 为null
        if(properties.getDataId() == null) {
            // 初始化dataId
            initDataId();
        }
    }

    /**
     * 初始化dataId
     */
    private void initDataId(){
        // grace配置文件的dataId（ dataId的命名格式可以去看com.grace.common.entity.Config类的dataId属性 ）
        String dataId;
        // 如果springProfileActive不为null，说明本地配置文件“配置了”spring.profiles.active
        if(properties.getSpringProfilesActive() != null){
            // 格式为: ${spring.application.name}-${spring.profiles.active}.${grace-config.config-type}
            dataId = properties.getSpringApplicationName() + "-"
                    + properties.getSpringProfilesActive() + "." + properties.getConfigType();
        }
        // 如果springProfileActive为null，说明本地配置文件“没有配置”spring.profiles.active
        else {
            // 格式为: ${spring.application.name}.${grace-config.config-type}
            dataId = properties.getSpringApplicationName() + "." + properties.getConfigType();
        }
        properties.setDataId(dataId);
    }

    /**
     * 加载自定义配置文件,并将该配置文件的属性加载到environment中
     *
     * @return {@link PropertySource}<{@link ?}>
     */
    @Override
    public PropertySource<?> locate(Environment environment) {
//        log.info("GracePropertySourceLocator.locate加载");
        // 将Environment转成ConfigurableEnvironment
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        // 初始化变量
        initVariable(configurableEnvironment);
        try {
            // 构建LoginService
            LoginService loginService = properties.getLoginService();
            // 获取登录grace控制台的用户名
            String username = configurableEnvironment.getProperty("grace.username");
            // 登录grace控制台的密码
            String password = configurableEnvironment.getProperty("grace.password");
            // 登录grace客户端(由于这个类是由BootstrapConfiguration加载,所以容器刚启动就会进行加载,此时就可以进行登录)
            TokenVO tokenVO = loginService.login(new UserLoginDTO(username, password));
            // 保存accessToken到本地
            properties.getTokenStorage().setAccessToken(tokenVO.getAccessToken());
            // 保存refreshToken到本地
            properties.getTokenStorage().setRefreshToken(tokenVO.getRefreshToken());
        }catch (Exception e){
            // grace客户端登录失败异常
            throw new GraceClientLoginException();
        }
        ConfigService configService = properties.getConfigService();
        String namespaceId = properties.getNamespaceId();
        String groupName = properties.getGroupName();
        String dataId = properties.getDataId();
        // 从配置中心获取指定的配置
        Config config = configService.getConfig(namespaceId, groupName, dataId);
        if(config == null){
            throw new RuntimeException("从配置中心获取配置失败,"+
                    String.format("namespaceId=%s,groupName=%s,dataId=%s", namespaceId, groupName, dataId));
        }
        // 配置内容
        String configContent = config.getContent();
        // 配置内容的md5值
        String configContentMd5 = config.getMd5();
        CacheConfigManager cacheConfigManager = CacheConfigManager.getSingleton();
        // 缓存我们 “从配置中心获取并加载”到PropertySource的自定义配置
        cacheConfigManager.putCacheConfig(namespaceId,groupName,dataId,configContent,configContentMd5);
        // 根据不同的配置类型,将配置内容转成Map
        Map<String, Object> configContentMap = configContentToMap(configContent);
        // 将我们自定义的配置加载到Spring的propertySource中
        return new MapPropertySource("my-config", configContentMap);
    }

    /**
     * 根据不同的配置类型,将配置内容转成Map
     *
     * @param configContent 配置内容
     * @return {@link Map}<{@link String},{@link Object}>
     */
    private Map<String,Object> configContentToMap(String configContent){
        Map<String,Object> configContentMap = null;
        String configType = properties.getConfigType();
        // 如果配置内容是 yaml 类型
        if (configType.equalsIgnoreCase(ConfigTypeEnum.YAML.getType())) {
            // 将yaml格式的内容转成Map集合，并将该Map集合的key转成以"."进行分隔的key（例如: server.port）,只有这种格式的key才能让Spring识别的到
            configContentMap = YamlUtils.yamlContentToMap(configContent);
        }
        // 如果配置内容是 properties 类型
        else if(configType.equalsIgnoreCase(ConfigTypeEnum.PROPERTIES.getType())){
            configContentMap = PropertiesUtils.propertiesContentToMap(configContent);
        }
        // 如果配置内容是 json 类型
        else if(configType.equalsIgnoreCase(ConfigTypeEnum.JSON.getType())){
            configContentMap = JsonUtils.jsonContentToMap(configContent);
        }
        // 不支持这种配置类型，直接抛出异常
        else {
            throw new UnsupportedOperationException("不支持这种配置类型:"+configType);
        }
        return configContentMap;
    }

}

