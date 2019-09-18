package com.wang.service.nettyservice.server.handler;

import com.wang.service.nettyservice.protocol.request.GroupMembersRequestPacket;
import com.wang.service.nettyservice.protocol.response.GroupMembersResponsePacket;
import com.wang.service.nettyservice.session.UserSession;
import com.wang.service.nettyservice.session.UserSessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 群组用户列表请求
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
@ChannelHandler.Sharable
public class GroupMembersServerHandler extends SimpleChannelInboundHandler<GroupMembersRequestPacket> {

    public static final GroupMembersServerHandler INSTANCE = new GroupMembersServerHandler();

    private GroupMembersServerHandler(){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestPacket requestPacket) throws Exception {
        // 1. 获取群的 ChannelGroup
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = UserSessionUtil.getChannelGroup(groupId);

        // 2. 遍历群成员的 channel，对应的 session，构造群成员的信息
        List<UserSession> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            UserSession session = UserSessionUtil.getSession(channel);
            sessionList.add(session);
        }

        // 3. 构建获取成员列表响应写回到客户端
        GroupMembersResponsePacket responsePacket = new GroupMembersResponsePacket();

        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        ctx.writeAndFlush(responsePacket);
    }
}
