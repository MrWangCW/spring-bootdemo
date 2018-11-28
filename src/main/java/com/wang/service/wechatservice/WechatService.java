package com.wang.service.wechatservice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangyanwei on 2018/11/21.
 *
 * @author wangyanwei
 * @version 1.0
 */
public interface WechatService {

    /**
     * 微信验证服务器token
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     */
    Boolean authSign(String signature, String timestamp, String nonce);

    /**
     * 处理用户请求信息
     *
     * @param request 信息流
     * @return
     */
    String processUserMsg(HttpServletRequest request);

}
