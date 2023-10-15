//package com.grace.console.core;
//
//import com.grace.common.entity.Service;
//import com.grace.common.executor.NameThreadFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.PostConstruct;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * service管理器（用于管理service）
// *
// * @author youzhengjie
// * @date 2023-10-06 15:23:45
// */
//public class ServiceManager {
//
//    private static final Logger log = LoggerFactory.getLogger(ServiceManager.class);
//
//    private static final ServiceManager INSTANCE = new ServiceManager();
//
//    /**
//     * 这里体现了一个命名空间可以有多个分组、一个分组可以有多个服务、一个服务可以有多个实例（分为临时实例和永久实例）
//     */
//    private final Map<String, Map<String, Service>> serviceMap;
//
//    /**
//     * 是否开启定时清理空服务功能
//     */
//    private static boolean enableScheduledCleanEmptyService;
//
//    /**
//     * 定时清理空服务线程名称前缀
//     */
//    private static final String SCHEDULED_CLEAN_EMPTY_SERVICE_THREAD_NAME_PREFIX = "scheduledCleanEmptyServiceThread";
//
//    /**
//     * 注意: 只有enableScheduledCleanEmptyService为true时才生效。
//     * <p>
//     * 指定过了多少毫秒之后才会“第一次触发”清理空服务的定时任务（单位: 毫秒）
//     * 也就是ScheduledExecutorService类的scheduleAtFixedRate方法的initialDelay属性。
//     */
//    private static int scheduledCleanEmptyServiceInitialDelay;
//
//    /**
//     * 注意: 只有enableScheduledCleanEmptyService为true时才生效。
//     * <p>
//     * 指定每隔多少毫秒就触发一次（“前提是定时任务不是第一次触发”）清理空服务的定时任务（单位: 毫秒）
//     * 也就是ScheduledExecutorService类的scheduleAtFixedRate方法的period属性。
//     */
//    private static int scheduledCleanEmptyServicePeriod;
//
//
//    private ServiceManager(){
//        // 初始化默认容量为1<<2（也就是为 4）
//        serviceMap = new ConcurrentHashMap<>(1 << 2);
//        enableScheduledCleanEmptyService = true;
//        scheduledCleanEmptyServiceInitialDelay = 60000;
//        scheduledCleanEmptyServicePeriod=20000;
//    }
//
//    public static ServiceManager getGroupManagerSingleton() {
//        return INSTANCE;
//    }
//
//    static {
//        // 初始化ServiceManager类
//        init();
//    }
//
//    /**
//     * 初始化ServiceManager类
//     */
//    public static void init() {
//        // 如果开启了定时清理空服务功能
//        if(enableScheduledCleanEmptyService){
//            log.info("启动定时清理空服务功能, initialDelay : {} ms, period : {} ms",
//                    scheduledCleanEmptyServiceInitialDelay, scheduledCleanEmptyServicePeriod);
//            // 创建“后台”定时清理空服务线程
//            ScheduledExecutorService scheduledCleanEmptyServiceThread =
//                    Executors.newScheduledThreadPool(1,
//                            new NameThreadFactory(SCHEDULED_CLEAN_EMPTY_SERVICE_THREAD_NAME_PREFIX,
//                                    true));
//            // 定时清理空服务
//            scheduledCleanEmptyServiceThread.scheduleAtFixedRate(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("执行清理空服务任务");
//                }
//
//            },scheduledCleanEmptyServiceInitialDelay,scheduledCleanEmptyServicePeriod, TimeUnit.MILLISECONDS);
//        }
//    }
//
//    public Set<String> getAllServiceNames(String namespaceId) {
//        return serviceMap.get(namespaceId).keySet();
//    }
//
//
//
//
//
//}
