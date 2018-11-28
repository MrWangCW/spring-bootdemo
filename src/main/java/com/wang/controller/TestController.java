package com.wang.controller;

import com.wang.domain.User;
import com.wang.rptimpl.wechatrpt.WeChatUtil;
import com.wang.rptimpl.wechatrpt.util.MenuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangyanwei on 2018/7/2.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Resource
    private WeChatUtil weChatUtil;

    @RequestMapping("user1")
    public String getUser1(Map map){
        String accessToken = weChatUtil.getAccessToken();
        System.out.println(accessToken);

        User user = new User();
        user.setUserId(111L);
        user.setUserName("测试1");
        user.setDate(new Date());
        map.put("user",user);
        return "Hello";
    }
    @RequestMapping("user2")
    @ResponseBody
    public User getUser2(){
        User user = new User();
        user.setUserId(222L);
        user.setUserName("测试2");
        user.setDate(new Date());
        return user;
    }
}
