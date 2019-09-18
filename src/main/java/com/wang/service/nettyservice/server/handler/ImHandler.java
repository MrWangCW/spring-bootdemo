package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyanwei on 2019/6/12.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class ImHandler extends SimpleChannelInboundHandler<Packet> {

    public static final ImHandler INSTANCE = new ImHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap = new HashMap<>();

    private ImHandler() {
        //登出
        handlerMap.put(Command.LOGOUT_REQUEST, LogoutServerHandler.INSTANCE);
        //私聊消息
        handlerMap.put(Command.MESSAGE_REQUEST, MessageServerHandler.INSTANCE);
        //创建群组
        handlerMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupServerHandler.INSTANCE);
        //加入群组
        handlerMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupServerHandler.INSTANCE);
        //退出群组
        handlerMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupServerHandler.INSTANCE);
        //群组成员
        handlerMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, GroupMembersServerHandler.INSTANCE);
        //群组消息
        handlerMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageServerHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }

}
