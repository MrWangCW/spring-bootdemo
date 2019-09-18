package com.wang.service.nettyservice.protocol.command;

public interface Command {

    /**
     * 登录请求
     */
    Byte LOGIN_REQUEST = 1;
    /**
     * 登录返回
     */
    Byte LOGIN_RESPONSE = 2;
    /**
     * 发送消息请求
     */
    Byte MESSAGE_REQUEST = 3;
    /**
     * 发送消息返回
     */
    Byte MESSAGE_RESPONSE = 4;
    /**
     * 登出请求
     */
    Byte LOGOUT_REQUEST = 5;
    /**
     * 登出返回
     */
    Byte LOGOUT_RESPONSE = 6;
    /**
     * 创建群聊请求
     */
    Byte CREATE_GROUP_REQUEST = 7;
    /**
     * 创建群聊返回
     */
    Byte CREATE_GROUP_RESPONSE = 8;
    /**
     * 群组用户列表请求
     */
    Byte LIST_GROUP_MEMBERS_REQUEST = 9;
    /**
     * 群组用户列表返回
     */
    Byte LIST_GROUP_MEMBERS_RESPONSE = 10;
    /**
     * 加入群组请求
     */
    Byte JOIN_GROUP_REQUEST = 11;
    /**
     * 加入群组返回
     */
    Byte JOIN_GROUP_RESPONSE = 12;
    /**
     * 退出群组请求
     */
    Byte QUIT_GROUP_REQUEST = 13;
    /**
     * 退出群组返回
     */
    Byte QUIT_GROUP_RESPONSE = 14;
    /**
     * 发送群消息请求
     */
    Byte GROUP_MESSAGE_REQUEST = 15;
    /**
     * 发送群消息请求返回
     */
    Byte GROUP_MESSAGE_RESPONSE = 16;
    /**
     * 加入退出群消息返回
     */
    Byte JOIN_QUIT_MESSAGE_RESPONSE = 17;
    /**
     * 心跳定时器请求
     */
    Byte HEARTBEAT_REQUEST = 18;
    /**
     * 心跳定时器请求返回
     */
    Byte HEARTBEAT_RESPONSE = 19;

}
