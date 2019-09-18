package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.request.JoinGroupRequestPacket;
import com.wang.service.nettyservice.protocol.response.JoinGroupResponsePacket;
import com.wang.service.nettyservice.protocol.response.JoinQuitMsgResponsePacket;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 加群请求
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class JoinGroupServerHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupServerHandler INSTANCE = new JoinGroupServerHandler();

    private JoinGroupServerHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) throws Exception {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = UserSessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());

        //发送群组加群消息
        JoinQuitMsgResponsePacket joinQuitMsgResponsePacket = new JoinQuitMsgResponsePacket();
        joinQuitMsgResponsePacket.setGroupId(groupId);
        joinQuitMsgResponsePacket.setJoinQuit(true);
        joinQuitMsgResponsePacket.setUserSession(UserSessionUtil.getSession(ctx.channel()));
        channelGroup.writeAndFlush(joinQuitMsgResponsePacket);

        // 2. 构造加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();

        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        ctx.writeAndFlush(responsePacket);


    }
}
