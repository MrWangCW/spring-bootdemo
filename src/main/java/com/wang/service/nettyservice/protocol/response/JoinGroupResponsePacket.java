package com.wang.service.nettyservice.protocol.response;

import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 加入群组请求返回
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JoinGroupResponsePacket extends Packet{

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return Command.JOIN_GROUP_RESPONSE;
    }
}
