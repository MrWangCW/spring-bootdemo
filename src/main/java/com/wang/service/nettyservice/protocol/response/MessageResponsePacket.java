package com.wang.service.nettyservice.protocol.response;

import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送消息请求返回
 * Created by wangyanwei on 2019/6/5.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    public MessageResponsePacket() {
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
