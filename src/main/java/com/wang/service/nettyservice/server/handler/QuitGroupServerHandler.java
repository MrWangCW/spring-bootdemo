package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.request.QuitGroupRequestPacket;
import com.wang.service.nettyservice.protocol.response.JoinQuitMsgResponsePacket;
import com.wang.service.nettyservice.protocol.response.QuitGroupResponsePacket;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 退出群组请求
 *
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class QuitGroupServerHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupServerHandler INSTANCE = new QuitGroupServerHandler();

    private QuitGroupServerHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) throws Exception {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 移除
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = UserSessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        if(channelGroup.isEmpty()){
            //群组为空移除该群组信息
            UserSessionUtil.removeChannelGroup(groupId);
        }else{
            //发送群组退群消息
            JoinQuitMsgResponsePacket joinQuitMsgResponsePacket = new JoinQuitMsgResponsePacket();
            joinQuitMsgResponsePacket.setGroupId(groupId);
            joinQuitMsgResponsePacket.setJoinQuit(false);
            joinQuitMsgResponsePacket.setUserSession(UserSessionUtil.getSession(ctx.channel()));
            channelGroup.writeAndFlush(joinQuitMsgResponsePacket);
        }

        // 2. 构造退群响应发送给客户端
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();

        responsePacket.setGroupId(requestPacket.getGroupId());
        responsePacket.setSuccess(true);
        ctx.writeAndFlush(responsePacket);
    }
}
