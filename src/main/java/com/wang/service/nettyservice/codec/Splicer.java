package com.wang.service.nettyservice.codec;

import com.wang.service.nettyservice.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 长度域拆包器
 * Created by wangyanwei on 2019/6/6.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class Splicer extends LengthFieldBasedFrameDecoder {

    //数据包结构
    //魔数  -- 版本号 -- 序列化算法 --   指令 -- 数据长度 --   数据
    //4字节 -- 1字节  --   1字节    --  1字节 --   4字节  --  N字节
    /**
     * 长度域相对整个数据包的偏移量 4+1+1+1=7
     */
    private static final int LENGTH_FIELD_OFFSET = 7;
    /**
     * 长度域的长
     */
    private static final int LENGTH_FIELD_LENGTH = 4;

    /**
     * 第一个参数指的是数据包的最大长度，第二个参数指的是长度域的偏移量，第三个参数指的是长度域的长度
     */
    public Splicer() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    /**
     * 屏蔽非本协议的客户端
     * @param in 数据包的开头
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
