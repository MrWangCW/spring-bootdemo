package com.wang.controller.wechatcontroller;

import com.wang.service.wechatservice.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by wangyanwei on 2018/11/20.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Controller
@RequestMapping("weChatAccounts")
public class WeChatAccountsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private WechatService wechatService;

    /**
     * Get请求，微信服务器验证token
     * @param request
     * @return
     */
    @GetMapping("auth")
    @ResponseBody
    public String authSign(HttpServletRequest request) {
        //签名
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");
        logger.info("微信校验服务器token，signature：" + signature + "，timestamp：" + timestamp
                + "，nonce：" + nonce + "，echostr：" + echostr);
        Boolean authResult = wechatService.authSign(signature, timestamp, nonce);
        //验证成功时，原样返回随机字符串
        return authResult ? echostr : null;
    }

    /**
     * Post请求，处理微信服务器发送的用户请求的信息
     * @param request
     * @return
     */
    @PostMapping("auth")
    @ResponseBody
    public String processUserMsg(HttpServletRequest request) {
        logger.info("---处理用户请求开始---");
        return wechatService.processUserMsg(request);
    }

}
