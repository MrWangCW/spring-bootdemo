package com.wang.service.nettyservice.client.handler;

import com.wang.service.nettyservice.protocol.request.LoginRequestPacket;
import com.wang.service.nettyservice.protocol.response.LoginResponsePacket;
import com.wang.service.nettyservice.session.UserSession;
import com.wang.service.nettyservice.session.UserSessionUtil;
import com.wang.service.nettyservice.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * 发送客户端登录请求
 * Created by wangyanwei on 2019/6/5.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class LoginClientHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

   /* @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端开始登录");


        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("flash");
        loginRequestPacket.setPassword("pwd");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket){

     /*   if (loginResponsePacket.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功");
            //保存登录标识
            LoginUtil.markAsLogin(ctx.channel());

        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }*/

        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();

        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + userName + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            UserSessionUtil.bindSession(new UserSession(userId, userName), ctx.channel());
        } else {
            System.out.println("[" + userName + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }

}
