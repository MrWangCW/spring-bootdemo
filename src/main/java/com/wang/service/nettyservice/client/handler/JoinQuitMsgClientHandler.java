package com.wang.service.nettyservice.client.handler;

import com.wang.service.nettyservice.protocol.response.JoinQuitMsgResponsePacket;
import com.wang.service.nettyservice.session.UserSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 加入退出群组消息
 * Created by wangyanwei on 2019/6/11.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class JoinQuitMsgClientHandler extends SimpleChannelInboundHandler<JoinQuitMsgResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinQuitMsgResponsePacket responsePacket) throws Exception {
        UserSession userSession = responsePacket.getUserSession();
        Boolean joinQuit = responsePacket.getJoinQuit();
        String groupId = responsePacket.getGroupId();
        if(joinQuit){
            System.out.println(userSession.getUserName()+ " 加入群聊 " + groupId);
        }else{
            System.out.println(userSession.getUserName()+ " 退出群聊 " + groupId);
        }
    }
}
