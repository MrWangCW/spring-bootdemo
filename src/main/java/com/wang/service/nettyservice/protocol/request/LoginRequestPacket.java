package com.wang.service.nettyservice.protocol.request;


import com.wang.service.nettyservice.protocol.command.Command;
import com.wang.service.nettyservice.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录请求
 * Created by wangyanwei on 2019/6/5.
 *
 * @author wangyanwei
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String userName;

    private String password;

    public LoginRequestPacket() {
    }

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
