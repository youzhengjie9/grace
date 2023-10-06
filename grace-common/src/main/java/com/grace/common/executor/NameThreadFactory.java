package com.grace.common.executor;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 能够指定名称的线程工厂
 *
 * @author youzhengjie
 * @date 2023/10/06 17:21:26
 */
public class NameThreadFactory implements ThreadFactory {

    /**
     * 自增id
     */
    private final AtomicInteger id = new AtomicInteger(0);

    /**
     * 线程名称前缀
     */
    private final String threadNamePrefix;


    /**
     * 是否为守护线程
     */
    private boolean isDaemon;

    public NameThreadFactory(String threadNamePrefix,boolean isDaemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.isDaemon=isDaemon;
    }

    @Override
    public Thread newThread(@NotNull Runnable runnable) {
        // 真正的线程名称
        String threadName = threadNamePrefix + id.getAndIncrement();
        Thread thread = new Thread(runnable, threadName);
        // 守护线程
        thread.setDaemon(isDaemon);
        return thread;
    }
}
