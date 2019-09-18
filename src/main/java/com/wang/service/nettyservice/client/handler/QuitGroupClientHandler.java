package com.wang.service.nettyservice.client.handler;

import com.wang.service.nettyservice.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class QuitGroupClientHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            System.out.println("退出群聊[" + packet.getGroupId() + "]成功！");
        } else {
            System.out.println("退出群聊[" + packet.getGroupId() + "]失败！");
        }
    }
}
