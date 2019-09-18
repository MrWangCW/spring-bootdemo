package com.wang.service.nettyservice.client.console;

import com.wang.service.nettyservice.protocol.request.JoinGroupRequestPacket;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 加入群组指令
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();

        System.out.print("输入 groupId，加入群聊：");
        String groupId = scanner.next();

        joinGroupRequestPacket.setGroupId(groupId);
        joinGroupRequestPacket.setUserSession(UserSessionUtil.getSession(channel));
        channel.writeAndFlush(joinGroupRequestPacket);
    }

}
