package com.wang.service.nettyservice.client;

import com.wang.service.nettyservice.client.console.ConsoleCommand;
import com.wang.service.nettyservice.client.console.ConsoleCommandManager;
import com.wang.service.nettyservice.client.handler.*;
import com.wang.service.nettyservice.codec.PacketDecoder;
import com.wang.service.nettyservice.codec.PacketEncoder;
import com.wang.service.nettyservice.codec.Splicer;
import com.wang.service.nettyservice.handler.ImIdleStateHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Netty客户端
 * Created by wangyanwei on 2019/6/4.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class NettyClient {

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //客户端启动的引导类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 绑定自定义属性到 channel
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                // 设置TCP底层属性
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 3.IO 定义连接的业务处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 空闲检测
                        pipeline.addLast(new ImIdleStateHandler());
                        //拆包器
                        pipeline.addLast(new Splicer());
                        //解码
                        pipeline.addLast(new PacketDecoder());
                        //登录响应处理器
                        pipeline.addLast(new LoginClientHandler());
                        //登出响应处理器
                        pipeline.addLast(new LogoutClientHandler());
                        //收消息处理器
                        pipeline.addLast(new MessageClientHandler());
                        //群建群组处理器
                        pipeline.addLast(new CreateGroupClientHandler());
                        //查询群组人员处理器
                        pipeline.addLast(new GroupMembersClientHandler());
                        //加入群组处理器
                        pipeline.addLast(new JoinGroupClientHandler());
                        //退出群组处理器
                        pipeline.addLast(new QuitGroupClientHandler());
                        //群组消息处理器
                        pipeline.addLast(new GroupMessageClientHandler());
                        //加入退出群组消息处理器
                        pipeline.addLast(new JoinQuitMsgClientHandler());
                        // 心跳定时器
                        pipeline.addLast(new HeartBeatTimerHandler());
                        //编码
                        pipeline.addLast(new PacketEncoder());

                    }
                });
        // 4.建立连接
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }


    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                sendMessageThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    private static void sendMessageThread(Channel channel) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Scanner sc = new Scanner(System.in);
        ConsoleCommand consoleCommandManager = new ConsoleCommandManager();

        executorService.submit(() -> {
            while (!Thread.interrupted()) {
                consoleCommandManager.exec(sc, channel);
            }
        });
        executorService.shutdown();
    }

}
