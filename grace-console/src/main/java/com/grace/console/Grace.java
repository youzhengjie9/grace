package com.grace.console;

import com.grace.common.executor.NameThreadFactory;
import com.grace.console.core.CheckAllEphemeralInstanceHeartBeatTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * grace
 *
 * @author youzhengjie
 * @date 2023/06/14 10:58:29
 */
@SpringBootApplication
@EnableScheduling //开启定时任务
public class Grace {

    private ScheduledThreadPoolExecutor checkAllEphemeralInstanceHeartBeatThreadPool;

    /**
     * 检查所有临时实例心跳的时间间隔,单位毫秒
     */
    private final Long checkAllEphemeralInstanceheartBeatInterval = 5000L;

    /**
     * 执行检查所有实例的心跳的任务
     */
    @PostConstruct
    public void executeCheckAllInstanceHeartBeatTask(){
        NameThreadFactory threadFactory = new NameThreadFactory("checkHeartBeat-",true);
        // 初始化定时任务线程池
        this.checkAllEphemeralInstanceHeartBeatThreadPool =
                new ScheduledThreadPoolExecutor(1,threadFactory);
        // 定时检查所有临时实例心跳任务（默认每5秒检查一次）
        this.checkAllEphemeralInstanceHeartBeatThreadPool.
                scheduleAtFixedRate(new CheckAllEphemeralInstanceHeartBeatTask(),
                        checkAllEphemeralInstanceheartBeatInterval,checkAllEphemeralInstanceheartBeatInterval, TimeUnit.MILLISECONDS);

    }

    public static void main(String[] args) {
        SpringApplication.run(Grace.class,args);
    }


    /**
     * 解决controller接口接收数组或者集合（@RequestParam("exportConfigIdList") List<String> exportConfigIdList）报如下错误:
     * Invalid character found in the request target [/grace/server/config/exportSelectedConfig?exportConfigIdListJSON[]=7649442153038853&exportConfigIdListJSON[]=7646833820304389 ]. The valid characters are defined in RFC 7230 and RFC 3986
     * <p>
     * 原因是:
     * SpringBoot 2.0.0 以上都采用内置tomcat8.0以上版本，而tomcat8.0以上版本遵从RFC规范添加了对Url的特殊字符的限制，
     * url中只允许包含英文字母(a-zA-Z)、数字(0-9)、-_.~四个特殊字符以及保留字符( ! * ’ ( ) ; : @ & = + $ , / ? # [ ] )
     * (262+10+4+18=84)这84个字符,请求中出现了{}大括号或者[],所以tomcat报错
     *
     * @return {@link ConfigurableServletWebServerFactory}
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }



}