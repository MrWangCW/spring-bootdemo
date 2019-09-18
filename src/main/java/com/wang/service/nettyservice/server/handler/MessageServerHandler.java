package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.request.MessageRequestPacket;
import com.wang.service.nettyservice.protocol.response.MessageResponsePacket;
import com.wang.service.nettyservice.session.UserSession;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 接收客户端发来的消息
 * Created by wangyanwei on 2019/6/6.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class MessageServerHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageServerHandler INSTANCE = new MessageServerHandler();

    private MessageServerHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket){
        // 客户端发来消息
        /*MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

        ctx.channel().writeAndFlush(messageResponsePacket);*/

        // 1.拿到消息发送方的会话信息
        UserSession session = UserSessionUtil.getSession(ctx.channel());

        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 3.拿到消息接收方的 channel
        Channel toUserChannel = UserSessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4.将消息发送给消息接收方
        if (toUserChannel != null && UserSessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }

    }

}
