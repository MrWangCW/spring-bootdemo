package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.request.GroupMessageRequestPacket;
import com.wang.service.nettyservice.protocol.response.GroupMessageResponsePacket;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 发送群消息请求
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
// 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class GroupMessageServerHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageServerHandler INSTANCE = new GroupMessageServerHandler();

    private GroupMessageServerHandler(){

    }

    /**
     * 耗时的操作需要丢到业务线程池中去处理，防止阻塞NIO线程
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) throws Exception {

        executorService.submit(() -> {
            long begin = System.currentTimeMillis();
            // 1.拿到 groupId 构造群聊消息的响应
            String groupId = requestPacket.getToGroupId();
            GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
            responsePacket.setFromGroupId(groupId);
            responsePacket.setMessage(requestPacket.getMessage());
            responsePacket.setFromUser(UserSessionUtil.getSession(ctx.channel()));


            // 2. 拿到群聊对应的 channelGroup，写到每个客户端
            ChannelGroup channelGroup = UserSessionUtil.getChannelGroup(groupId);
            /*
              方法如果在非 NIO 线程（这里，我们其实是在业务线程中调用了该方法）中执行，它是一个异步的操作，调用之后，
              其实是会立即返回的，剩下的所有的操作，都是 Netty 内部有一个任务队列异步执行因此，这里的 writeAndFlush() 执行完毕之后，
              并不能代表相关的逻辑，比如事件传播、编码等逻辑执行完毕，只是表示 Netty 接收了这个任务,writeAndFlush() 方法会返回一个
              ChannelFuture 对象，我们给这个对象添加一个监听器，然后在回调方法里面，我们可以监听这个方法执行的结果，进而再执行其他逻辑，
              最后统计耗时，这样统计出来的耗时才是最准确的
             */
            channelGroup.writeAndFlush(responsePacket).addListener(future -> {
                if (future.isDone()) {

                    long time =  System.currentTimeMillis() - begin;
                    System.out.println("发送群组消息完成，耗时：" + time);
                }
            });
        });
        executorService.shutdown();


    }

}
