package com.wang.service.nettyservice.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Created by wangyanwei on 2019/6/5.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class LoginUtil {

   private static AttributeKey<Boolean> login = AttributeKey.newInstance("login");

    public static void markAsLogin(Channel channel) {
        channel.attr(login).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(login);

        return loginAttr.get() != null;
    }
}
