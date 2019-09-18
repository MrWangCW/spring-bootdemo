package com.wang.service.nettyservice.client.console;

import com.wang.service.nettyservice.protocol.request.CreateGroupRequestPacket;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 创建群组指令
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLICER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");
        String userIds = scanner.next();
        //拼接上自己的用户ID
        userIds += "," + UserSessionUtil.getSession(channel).getUserId();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLICER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }

}
