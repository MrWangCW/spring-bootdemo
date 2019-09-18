package com.wang.service.nettyservice.protocol.request;

import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;
import com.wang.service.nettyservice.session.UserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 加入群组请求
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    private UserSession userSession;

    @Override
    public Byte getCommand() {

        return Command.JOIN_GROUP_REQUEST;
    }
}
