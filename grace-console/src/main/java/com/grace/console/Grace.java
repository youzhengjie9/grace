package com.grace.console;

import com.grace.common.executor.NameThreadFactory;
import com.grace.console.core.CheckAllEphemeralInstanceHeartBeatTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

}