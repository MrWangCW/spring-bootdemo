package com.wang.service.nettyservice.client.console;

import com.wang.service.nettyservice.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 发送群组消息指令
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class SendToGroupConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个群组(格式，群组ID 消息)：");

        String toGroupId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));
    }

}
