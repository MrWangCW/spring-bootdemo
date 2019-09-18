package com.wang.service.nettyservice.client.handler;

import com.wang.service.nettyservice.protocol.request.GroupMembersRequestPacket;
import com.wang.service.nettyservice.protocol.response.GroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 群组用户列表请求返回
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class GroupMembersClientHandler extends SimpleChannelInboundHandler<GroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersResponsePacket responsePacket) throws Exception {
        System.out.println("群[" + responsePacket.getGroupId() + "]中的人包括：" + responsePacket.getSessionList());
    }
}
