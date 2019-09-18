package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.request.LoginRequestPacket;
import com.wang.service.nettyservice.protocol.response.LoginResponsePacket;
import com.wang.service.nettyservice.session.UserSession;
import com.wang.service.nettyservice.session.UserSessionUtil;
import com.wang.service.nettyservice.util.LoginUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * 接收客户端登录请求
 * Created by wangyanwei on 2019/6/5.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class LoginServerHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginServerHandler INSTANCE = new LoginServerHandler();

    private LoginServerHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        System.out.println(new Date() + ": 客户端开始登录……");
        // 登录流程

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        String userName = loginRequestPacket.getUserName();
        loginResponsePacket.setUserName(userName);
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            //添加登录标志
//            LoginUtil.markAsLogin(ctx.channel());
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            UserSessionUtil.bindSession(new UserSession(userId, userName), ctx.channel());
            System.out.println(new Date() + "：" + userName + "，登录成功!");
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + "：" + userName + "，登录失败!");
        }
        // 登录响应
        ctx.writeAndFlush(loginResponsePacket);

    }

    /**
     * 获取随机用户UUID
     */
    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    /**
     * 校验登录密码
     */
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    /**
     * 断开连接 移除该用户session
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        UserSessionUtil.unBindSession(ctx.channel());
    }
}