package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.request.HeartBeatRequestPacket;
import com.wang.service.nettyservice.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 心跳定时器请求返回
 * Created by wangyanwei on 2019/6/12.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class HeartBeatServerHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatServerHandler INSTANCE = new HeartBeatServerHandler();

    private HeartBeatServerHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
