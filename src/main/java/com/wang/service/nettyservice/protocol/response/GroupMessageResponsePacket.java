package com.wang.service.nettyservice.protocol.response;

import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;
import com.wang.service.nettyservice.session.UserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送群消息请求返回
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupMessageResponsePacket extends Packet{

    private String fromGroupId;

    private UserSession fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
