package com.wang.service.nettyservice.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public interface ConsoleCommand {

    /**
     * 控制台指令
     *
     * @param scanner 控制台
     * @param channel channel
     */
    void exec(Scanner scanner, Channel channel);

}
