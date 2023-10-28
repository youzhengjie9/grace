package com.grace.client.registry.core;

import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.registry.RegistryService;
import com.grace.client.registry.factory.RegistryServiceFactory;
import com.grace.common.constant.Constants;
import com.grace.common.dto.HeartBeat;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.executor.NameThreadFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 当SpringBoot容器启动完成之后,就执行一些有关注册中心的代码（比如: 自动进行注册实例）
 *
 * @author youzhengjie
 * @date 2023/10/23 21:29:27
 */
public class GraceRegistryApplicationRunner implements ApplicationRunner, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(GraceRegistryApplicationRunner.class);
    @Autowired
    private GraceRegistryProperties graceRegistryProperties;

    private RegistryService registryService;

    private Environment environment;

    /**
     * 定时发送心跳请求的线程池
     */
    private ScheduledThreadPoolExecutor scheduledSendHeartBeatThreadPool;

    /**
     * 发送心跳请求的定时任务线程池的线程数量
     */
    private static final int SEND_HEART_BEAT_THREAD_POOL_COUNT = 1;

    /**
     * 发送心跳请求的定时任务线程池中的线程名称前缀
     */
    private static final String SEND_HEART_BEAT_THREAD_POOL_NAME_PREFIX = "sendHeartBeat-";
    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.environment = environment;
        // 初始化RegistryService对象
        this.registryService = RegistryServiceFactory.
                createRegistryService(graceRegistryProperties.getConsoleAddress());

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 自动注册实例
            Boolean registerInstanceResult = registerInstance();
            // 如果注册实例成功,并且该实例是临时实例,则需要定时发送心跳请求
            if(registerInstanceResult && graceRegistryProperties.getEphemeral()){
                // “守护线程”的线程工厂
                NameThreadFactory threadFactory =
                        new NameThreadFactory(SEND_HEART_BEAT_THREAD_POOL_NAME_PREFIX, true);
                this.scheduledSendHeartBeatThreadPool =
                        new ScheduledThreadPoolExecutor(SEND_HEART_BEAT_THREAD_POOL_COUNT,threadFactory);
                // 创建心跳
                HeartBeat heartBeat = new HeartBeat();
                heartBeat.setNamespaceId(graceRegistryProperties.getNamespaceId());
                heartBeat.setGroupName(graceRegistryProperties.getGroupName());
                heartBeat.setServiceName(graceRegistryProperties.getServiceName());
                heartBeat.setIpAddr(InetAddress.getLocalHost().getHostAddress());
                heartBeat.setPort(Integer.parseInt(environment.getProperty("server.port", String.valueOf(Constants.DEFAULT_CLIENT_PORT))));
                // 开始定时发送心跳请求,当springboot启动后5秒才发送第一个心跳请求,之后每5秒发送一个心跳请求
                scheduledSendHeartBeatThreadPool.
                        scheduleAtFixedRate(new SendHeartBeatTask(heartBeat),
                                graceRegistryProperties.getHeartBeatInterval(),
                                graceRegistryProperties.getHeartBeatInterval(), TimeUnit.MILLISECONDS);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 注册实例
     *
     * @return {@link Boolean}
     */
    private Boolean registerInstance() throws UnknownHostException {
        // 创建一个注册实例dto对象
        RegisterInstanceDTO registerInstanceDTO = new RegisterInstanceDTO();
        registerInstanceDTO.setNamespaceId(graceRegistryProperties.getNamespaceId());
        registerInstanceDTO.setGroupName(graceRegistryProperties.getGroupName());
        registerInstanceDTO.setServiceName(graceRegistryProperties.getServiceName());
        // 获取当前应用的ip
        registerInstanceDTO.setIpAddr(InetAddress.getLocalHost().getHostAddress());
        // 获取当前应用的端口
        registerInstanceDTO.setPort(Integer.parseInt(environment.getProperty("server.port", String.valueOf(Constants.DEFAULT_CLIENT_PORT))));
        registerInstanceDTO.setWeight(graceRegistryProperties.getWeight());
        registerInstanceDTO.setHealthy(Constants.DEFAULT_HEALTHY);
        registerInstanceDTO.setOnline(Constants.DEFAULT_ONLINE);
        registerInstanceDTO.setEphemeral(graceRegistryProperties.getEphemeral());
        registerInstanceDTO.setMetadata("");
        // 注册实例
        Boolean registerInstanceResult = registryService.registerInstance(registerInstanceDTO);
        log.info("注册实例的信息: {}",registerInstanceDTO);
        log.info("注册实例是否成功: {}",registerInstanceResult);
        return registerInstanceResult;
    }

    /**
     * 发送心跳请求的任务(每执行一次就会往服务端发送一个心跳)
     *
     * @author youzhengjie
     * @date 2023/10/24 23:32:33
     */
    class SendHeartBeatTask implements Runnable{

        /**
         * 心跳对象（只需要把这个心跳对象发送给服务端就行了）
         */
        private final HeartBeat heartBeat;

        public SendHeartBeatTask(HeartBeat heartBeat) {
            this.heartBeat = heartBeat;
        }

        @Override
        public void run() {
            try {
                // 发送一次心跳请求
                Boolean sendHeartBeatResult = registryService.sendHeartBeat(heartBeat);
                log.info("发送一次心跳请求。{}",heartBeat);
                log.info("心跳请求是否发送成功: {}",sendHeartBeatResult);
                // 如果发送心跳请求失败,则再发送一次（重试操作）
                if(!sendHeartBeatResult){
                    // 重新发送一次心跳请求
                    Boolean retrySendHeartBeatResult = registryService.sendHeartBeat(heartBeat);
                    log.info("第2次心跳请求是否发送成功: {}",sendHeartBeatResult);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }



}
