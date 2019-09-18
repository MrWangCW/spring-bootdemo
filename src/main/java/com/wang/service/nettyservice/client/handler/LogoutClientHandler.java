package com.wang.service.nettyservice.client.handler;

import com.wang.service.nettyservice.protocol.response.LogoutResponsePacket;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登出请求返回
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class LogoutClientHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        if(logoutResponsePacket.isSuccess()){
            UserSessionUtil.unBindSession(ctx.channel());
            System.out.println("退出登录成功");
        }else{
            System.out.println("退出登录失败，原因：" + logoutResponsePacket.getReason());
        }
    }

}
