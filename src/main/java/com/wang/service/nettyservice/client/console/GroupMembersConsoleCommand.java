package com.wang.service.nettyservice.client.console;

import com.wang.service.nettyservice.protocol.request.GroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 群组用户列表指令
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class GroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        GroupMembersRequestPacket listGroupMembersRequestPacket = new GroupMembersRequestPacket();

        System.out.print("输入 groupId，获取群成员列表：");
        String groupId = scanner.next();

        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }

}
