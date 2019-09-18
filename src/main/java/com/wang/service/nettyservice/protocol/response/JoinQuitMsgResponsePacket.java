package com.wang.service.nettyservice.protocol.response;


import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;
import com.wang.service.nettyservice.session.UserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 加入退出群组消息
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JoinQuitMsgResponsePacket extends Packet {

    private UserSession userSession;
    /**
     * true 加入  false 退出
     */
    private Boolean joinQuit;

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_QUIT_MESSAGE_RESPONSE;
    }
}
