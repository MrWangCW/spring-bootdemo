package com.wang.util.propertiesutil;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 *  配置文件监听器，用来加载自定义配置文件
 * Created by wangyanwei on 2018/7/4.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class PropertiesListener implements ApplicationListener<ApplicationEvent>{

    private String propertyFileName;

    public PropertiesListener(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
         PropertiesUtil.loadAllProperties(propertyFileName);
    }
}
