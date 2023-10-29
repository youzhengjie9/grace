package com.grace.client.config.core;

import com.grace.client.config.ConfigService;
import com.grace.client.config.cache.CacheConfigManager;
import com.grace.client.config.cache.entity.CacheConfig;
import com.grace.client.config.factory.ConfigServiceFactory;
import com.grace.client.properties.GraceConfigProperties;
import com.grace.common.entity.Config;
import com.grace.common.executor.NameThreadFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author youzhengjie
 * @date 2023/10/29 00:36:59
 */
public class GraceConfigApplicationRunner implements ApplicationRunner, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(GraceConfigApplicationRunner.class);
    @Autowired
    private GraceConfigProperties graceConfigProperties;

    private ConfigService configService;

    private Environment environment;

    private final CacheConfigManager cacheConfigManager = CacheConfigManager.getSingleton();

    @Autowired
    @Qualifier("legacyContextRefresher")
    private ContextRefresher contextRefresher;

    /**
     * 定时从配置中心拉取配置（并刷新当前的配置）的线程池(pull模式)
     */
    private ScheduledThreadPoolExecutor scheduledPullConfigThreadPool;

    /**
     * 从配置中心拉取配置（并刷新当前的配置）的定时任务线程池的线程数量
     */
    private static final int PULL_CONFIG_THREAD_POOL_COUNT = 1;

    /**
     * 从配置中心拉取配置（并刷新当前的配置）的定时任务线程池中的线程名称前缀
     */
    private static final String PULL_CONFIG_THREAD_POOL_NAME_PREFIX = "pullConfig-";

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.environment = environment;
        // 初始化ConfigService
        this.configService = ConfigServiceFactory.
                createConfigService(graceConfigProperties.getConsoleAddress());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // “守护线程”的线程工厂
        NameThreadFactory threadFactory =
                new NameThreadFactory(PULL_CONFIG_THREAD_POOL_NAME_PREFIX, true);
        // 初始化线程池
        this.scheduledPullConfigThreadPool =
                new ScheduledThreadPoolExecutor(PULL_CONFIG_THREAD_POOL_COUNT,threadFactory);
        // 开始定时从配置中心拉取配置（并刷新当前的配置）,默认每5秒就去配置中心拉取一次配置
        scheduledPullConfigThreadPool.
                scheduleAtFixedRate(new PullConfigTask(),
                        5000,
                        5000, TimeUnit.MILLISECONDS);
    }

    /**
     * 拉模式。从配置中心拉取配置（并刷新当前的配置）的任务
     *
     * @author youzhengjie
     * @date 2023-10-29 00:36:55
     */
    class PullConfigTask implements Runnable{

        @Override
        public void run() {
            try {
                String namespaceId = graceConfigProperties.getNamespaceId();
                String groupName = graceConfigProperties.getGroupName();
                String dataId = getDataId();
                // 从配置中心上面拉取指定的配置(最新的配置)
                Config latestConfig = configService.getConfig(namespaceId, groupName, dataId);
                // 获取指定缓存的配置（也可以说该配置是“旧配置”）
                CacheConfig cacheConfig =
                        cacheConfigManager.getCacheConfig(namespaceId, groupName, dataId);
                // 获取最新配置的md5
                String latestConfigMd5 = latestConfig.getMd5();
                // 获取缓存配置的md5值
                String cacheConfigMd5 = cacheConfig.getMd5();
                // 如果从配置中心拉取的最新配置的md5和缓存配置的md5不一样
                if(!latestConfigMd5.equals(cacheConfigMd5)){
                    log.info("拉模式。检测到当前使用的配置中心的配置发生修改,准备进行刷新配置的操作.");
                    // 则调用contextRefresher.refresh()方法进行“动态（不需要重启项目）刷新配置,
                    // 和刷新所有加了@RefreshScope注解的类”
                    contextRefresher.refresh();
                }
                // 如果从配置中心拉取的最新配置的md5和缓存配置的md5一样,则不刷新配置
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 获取dataId
         *
         * @return {@link String}
         */
        private String getDataId(){
            // grace配置文件的dataId（ dataId的命名格式可以去看com.grace.common.entity.Config类的dataId属性 ）
            String dataId;
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
            String springApplicationName = configurableEnvironment.getProperty("spring.application.name");
            String springProfilesActive = configurableEnvironment.getProperty("spring.profiles.active");
            // 如果springProfileActive不为null，说明本地配置文件“配置了”spring.profiles.active
            if(springProfilesActive != null){
                // 格式为: ${spring.application.name}-${spring.profiles.active}.${grace-config.config-type}
                dataId = springApplicationName + "-"
                        + springProfilesActive + "." + graceConfigProperties.getConfigType().getType();
            }
            // 如果springProfileActive为null，说明本地配置文件“没有配置”spring.profiles.active
            else {
                // 格式为: ${spring.application.name}.${grace-config.config-type}
                dataId = springApplicationName + "." + graceConfigProperties.getConfigType().getType();
            }
            return dataId;
        }


    }

}
