package com.wang.service.nettyservice.server;

import com.wang.service.nettyservice.codec.PacketCodecHandler;
import com.wang.service.nettyservice.codec.Splicer;
import com.wang.service.nettyservice.handler.ImIdleStateHandler;
import com.wang.service.nettyservice.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Netty服务端
 * Created by wangyanwei on 2019/6/4.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class NettyServer {

    private static final int BEGIN_PORT = 8000;

    public static void main(String[] args) {
        //表示监听端口，accept(阻塞) 新连接的线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        //表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //服务端启动的引导类
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        //创建连接的客户端的属性值key
        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");
        serverBootstrap
                //group给引导类配置两大线程组
                .group(boosGroup, workerGroup)
                //channel指定服务端的IO模型为NIO
                .channel(NioServerSocketChannel.class)
                //attr()方法可以给服务端的 channel，也就是NioServerSocketChannel指定一些自定义属性,可以通过channel.attr()取出这个属性
                //attr指定服务端channel的一个serverName属性，属性值为nettyServer
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                //childAttr可以给每一条连接指定自定义属性，可以通过channel.attr()取出该属性
                .childAttr(clientKey, "clientValue")
                //option给服务端channel设置一些属性
                //SO_BACKLOG 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                //childOption()可以给每条连接设置一些TCP底层相关的属性
                //ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //ChannelOption.TCP_NODELAY表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，
                // 有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                .childOption(ChannelOption.TCP_NODELAY, true)
                //创建ChannelInitializer，定义后续每条连接的数据读写，业务处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println("clientKey：" + ch.attr(clientKey).get());
                        //ch.pipeline() 返回的是和这条连接相关的逻辑处理链，采用了责任链模式
                        ChannelPipeline pipeline = ch.pipeline();
                        //addLast() 方法 添加一个逻辑处理器
                        // 空闲检测
                        pipeline.addLast(new ImIdleStateHandler());
                        //拆包器
                        pipeline.addLast(new Splicer());
//                        pipeline.addLast(new PacketDecoder());
                        //编解码器合并 ，没有改造编解码之前，我们必须调用 ctx.channel().writeAndFlush(), 而经过改造之后，
                        // 我们的编码器（既属于 inBound, 又属于 outBound 类型的 handler）已处于 pipeline 的最前面，因此，可以大胆使用 ctx.writeAndFlush()。
                        pipeline.addLast(PacketCodecHandler.INSTANCE);
                        //登录
                        pipeline.addLast(LoginServerHandler.INSTANCE);
                        //
                        pipeline.addLast(HeartBeatServerHandler.INSTANCE);
                        pipeline.addLast(AuthHandler.INSTANCE);
                        //平行指令合并
                        pipeline.addLast(ImHandler.INSTANCE);

//                        pipeline.addLast(new PacketEncoder());
                    }
                });


        bind(serverBootstrap, BEGIN_PORT);

    }

    /**
     * 自动绑定端口号
     * serverBootstrap.bind(8000);这个方法呢，它是一个异步的方法，调用之后是立即返回的，他的返回值是一个ChannelFuture，
     * 我们可以给这个ChannelFuture添加一个监听器GenericFutureListener，然后我们在GenericFutureListener的operationComplete方法里面，
     * 我们可以监听端口是否绑定成功，接下来是监测端口是否绑定成功的代码片段
     *
     * @param serverBootstrap 进行服务端的启动工作
     * @param port            端口号
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    //调用自身绑定
                    //bind(serverBootstrap, port + 1);
                }
            }
        });
    }

}
