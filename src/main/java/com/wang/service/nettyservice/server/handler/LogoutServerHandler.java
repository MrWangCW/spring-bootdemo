package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.request.LogoutRequestPacket;
import com.wang.service.nettyservice.protocol.response.LogoutResponsePacket;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端登出请求
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class LogoutServerHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutServerHandler INSTANCE = new LogoutServerHandler();

    private LogoutServerHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket logoutRequestPacket) throws Exception {
        UserSessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.writeAndFlush(logoutResponsePacket);
    }
}
