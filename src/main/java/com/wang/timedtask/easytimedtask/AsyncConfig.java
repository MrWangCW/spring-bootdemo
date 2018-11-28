package com.wang.timedtask.easytimedtask;

import com.wang.util.propertiesutil.PropertiesUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步线程池 @Async
 * Configuration：表明该类是一个配置类
 * EnableAsync：开启异步事件的支持
 * Created by wangyanwei on 2018/7/9.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    private String corePoolSize = PropertiesUtil.getPropertyMapByName("corePoolSize");
    private String maxPoolSize = PropertiesUtil.getPropertyMapByName("maxPoolSize");
    private String queueCapacity = PropertiesUtil.getPropertyMapByName("queueCapacity");

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Integer.parseInt(corePoolSize));
        executor.setMaxPoolSize(Integer.parseInt(maxPoolSize));
        executor.setQueueCapacity(Integer.parseInt(queueCapacity));
        executor.initialize();
        return executor;
    }

}
