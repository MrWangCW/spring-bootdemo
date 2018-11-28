package com.wang.util.propertiesutil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * redis配置
 * Created by wangyanwei on 2018/6/30.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
@PropertySource("classpath:connect-redis.properties")
public class RedisDto {

    private String nodes;
    private String timeout;
    private String maxattempt;
    private String minidle;
    private String maxidle;
    private String maxactive;
    private String maxwait;
    private String testOnBorrow;
    private String host;
    private String port;

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getMaxattempt() {
        return maxattempt;
    }

    public void setMaxattempt(String maxattempt) {
        this.maxattempt = maxattempt;
    }

    public String getMinidle() {
        return minidle;
    }

    public void setMinidle(String minidle) {
        this.minidle = minidle;
    }

    public String getMaxidle() {
        return maxidle;
    }

    public void setMaxidle(String maxidle) {
        this.maxidle = maxidle;
    }

    public String getMaxactive() {
        return maxactive;
    }

    public void setMaxactive(String maxactive) {
        this.maxactive = maxactive;
    }

    public String getMaxwait() {
        return maxwait;
    }

    public void setMaxwait(String maxwait) {
        this.maxwait = maxwait;
    }

    public String getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(String testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
