package com.wang.service.nettyservice.session;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangyanwei on 2019/6/10.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class UserSessionUtil {

    private static AttributeKey<UserSession> SESSION = AttributeKey.newInstance("session");

    private static final Map<String, Channel> USER_ID_CHANNEL_MAP = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> GROUP_ID_CHANNEL_GROUP_MAP = new ConcurrentHashMap<>();

    public static void bindSession(UserSession session, Channel channel) {
        USER_ID_CHANNEL_MAP.put(session.getUserId(), channel);
        channel.attr(SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            USER_ID_CHANNEL_MAP.remove(getSession(channel).getUserId());
            channel.attr(SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(SESSION);
    }

    public static UserSession getSession(Channel channel) {

        return channel.attr(SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return USER_ID_CHANNEL_MAP.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        GROUP_ID_CHANNEL_GROUP_MAP.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return GROUP_ID_CHANNEL_GROUP_MAP.get(groupId);
    }

    public static void removeChannelGroup(String groupId){
        GROUP_ID_CHANNEL_GROUP_MAP.remove(groupId);
    }
}
