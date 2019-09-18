package com.wang.service.nettyservice.protocol.response;

import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;
import com.wang.service.nettyservice.session.UserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 群组用户列表请求返回
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<UserSession> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
