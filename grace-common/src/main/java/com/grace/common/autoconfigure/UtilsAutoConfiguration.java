package com.grace.common.autoconfigure;

import com.grace.common.utils.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 工具类的自动配置类
 *
 * @author youzhengjie
 * @date 2023/06/16 00:25:27
 */
@Configuration
@Import({
        BrowserUtils.class,
        IpToAddressUtils.class,
        IpUtils.class,
        SpringUtils.class,
        EasyExcelUtils.class
})
public class UtilsAutoConfiguration {



}
