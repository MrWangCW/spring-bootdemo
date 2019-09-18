package com.wang.service.nettyservice.client.handler;

import com.wang.service.nettyservice.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 加入群组请求返回
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class JoinGroupClientHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            System.out.println("加入群[" + packet.getGroupId() + "]成功!");
        } else {
            System.err.println("加入群[" + packet.getGroupId() + "]失败，原因为：" + packet.getReason());
        }
    }
}
