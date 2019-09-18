package com.wang.service.nettyservice.protocol.response;

import com.wang.service.nettyservice.protocol.Packet;
import com.wang.service.nettyservice.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登出请求返回
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    public LogoutResponsePacket() {
    }

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }

}
