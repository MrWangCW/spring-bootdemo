package com.wang.service.nettyservice.protocol.request;

import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;

/**
 * 心跳定时器请求
 * Created by wangyanwei on 2019/6/12.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
