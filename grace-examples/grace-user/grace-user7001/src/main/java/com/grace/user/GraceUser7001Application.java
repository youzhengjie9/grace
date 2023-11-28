package com.grace.user;

import com.grace.client.registry.RegistryService;
import com.grace.common.constant.Constants;
import com.grace.common.entity.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.List;


// 由于没有使用mysql,所以要排除DataSourceAutoConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GraceUser7001Application {

//    private static final Logger log = LoggerFactory.getLogger(GraceUser7001Application.class);
//
//    @Autowired
//    private RegistryService registryService;
//
//    /**
//     * 测试服务发现
//     */
//    @PostConstruct
//    public void serviceDiscovery(){
//
//        // 获取grace-order服务的所有实例（服务发现）
//        List<Instance> allInstance = registryService.getAllInstance(Constants.DEFAULT_NAMESPACE_ID,
//                Constants.DEFAULT_GROUP_NAME, "grace-order");
//        log.info("获取到grace-order服务的所有实例。{}",allInstance);
//    }

    public static void main(String[] args) {
        SpringApplication.run(GraceUser7001Application.class);
    }
}