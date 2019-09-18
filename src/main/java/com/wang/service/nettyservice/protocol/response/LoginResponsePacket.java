package com.wang.service.nettyservice.protocol.response;

import com.wang.service.nettyservice.protocol.command.Command;
import com.wang.service.nettyservice.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录请求返回
 * Created by wangyanwei on 2019/6/5.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    public LoginResponsePacket() {
    }

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
