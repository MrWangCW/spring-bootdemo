package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.session.UserSessionUtil;
import com.wang.service.nettyservice.util.LoginUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 使用 channelHandler 的热插拔实现客户端身份校验
 * Created by wangyanwei on 2019/6/10.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler(){

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*if (!LoginUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            //已判断权限，移除此逻辑
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }*/
        if (!UserSessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            //已判断权限，移除此逻辑
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {

        if (UserSessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }

}
