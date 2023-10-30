package com.grace.client.config.endpoint;

import com.grace.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态刷新配置端点（和org.springframework.cloud.endpoint.RefreshEndpoint的refresh端点效果一样）
 *
 * @author youzhengjie
 * @date 2023/10/30 11:32:13
 */
@RestController
public class DynamicRefreshConfigEndpoint {

    @Autowired
    @Qualifier("legacyContextRefresher")
    private ContextRefresher contextRefresher;

    /**
     * 动态刷新配置。
     * <p>
     * 实际上是调用contextRefresher.refresh()方法进行“动态（不需要重启项目）刷新配置,和刷新所有加了@RefreshScope注解的类”
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping("/dynamic/refresh/config")
    public Result<Boolean> dynamicRefreshConfig(){
        contextRefresher.refresh();
        return Result.ok(true);
    }


}
