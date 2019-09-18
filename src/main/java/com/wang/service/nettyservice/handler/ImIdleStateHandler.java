package com.wang.service.nettyservice.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 空闲检测
 * Created by wangyanwei on 2019/6/12.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class ImIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 6;

    public ImIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.MINUTES);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        System.out.println(READER_IDLE_TIME + "分钟内未读到数据，关闭连接");
        ctx.channel().close();
    }

}
