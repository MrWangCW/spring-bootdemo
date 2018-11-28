package com.wang.service.wechatservice;

import com.wang.domain.wechat.EventType;
import com.wang.domain.wechat.MsgType;
import com.wang.rptimpl.wechatrpt.util.WeChatMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by wangyanwei on 2018/11/21.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WechatServiceImpl implements WechatService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 自定义token, 用作生成签名,从而验证安全性
     */
    private final String TOKEN = "wang";

    @Resource
    private WeChatMessageUtil weChatMessageUtil;

    @Override
    public Boolean authSign(String signature, String timestamp, String nonce) {

        /**
         * 将token、timestamp、nonce三个参数进行字典序排序
         * 并拼接为一个字符串
         */
        String[] strArray = {TOKEN, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        /**
         * 字符串进行shal加密
         */
        String mySignature = shal(sb.toString());

        /**
         * 校验微信服务器传递过来的签名 和  加密后的字符串是否一致, 若一致则签名通过
         */
        if (!"".equals(signature) && !"".equals(mySignature) && signature.equals(mySignature)) {
            logger.info("微信校验服务器token成功");
            return Boolean.TRUE;
        } else {
            logger.info("微信校验服务器token失败");
            return Boolean.FALSE;
        }
    }

    /**
     * 字符串进行shal加密
     *
     * @param str
     * @return
     */
    public String shal(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();

            StringBuilder hexString = new StringBuilder();
            // 字节数组转换为 十六进制 数
            for (byte aMessageDigest : messageDigest) {
                String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String processUserMsg(HttpServletRequest request) {

        try {
            //接收到的信息Map
            Map<String, String> reqMap = WeChatMessageUtil.parseXml(request);

            String result;
            String msgType = reqMap.get("MsgType").toUpperCase();

            String createTime = reqMap.get("CreateTime");
            String msgId = reqMap.get("MsgId");
            //发送方帐号 为用户openId
            String fromUserName = reqMap.get("FromUserName");
            //接收方账号 开发者微信号
            String toUserName = reqMap.get("ToUserName");
            logger.info("处理用户信息，消息类型msgType：" + msgType + "，发送方帐号fromUserName：" + fromUserName
                    + "，开发者微信号toUserName：" + toUserName + "，发送时间createTime：" + createTime + "，消息ID,msgId：" + msgId);

            if (MsgType.TEXT.toString().equals(msgType)) {
                String content = reqMap.get("Content");
                logger.info("处理用户文本信息，内容content：" + content);
                if(content.contains("文本")){
                    result = WeChatMessageUtil.buildNewsMessage(toUserName,fromUserName);
                }else{
                    result = WeChatMessageUtil.buildTextMessage(toUserName, fromUserName, "我是贱贱的小卓卓\uD83D\uDE0F\uD83D\uDE0F\n请问客官想要什么服务?");
                }


            } else if (MsgType.IMAGE.toString().equals(msgType)) {
                String picUrl = reqMap.get("PicUrl");
                String mediaId = reqMap.get("MediaId");
                logger.info("处理用户图片信息，图片链接picUrl：" + picUrl + "，图片消息媒体id：" + mediaId);
                result = WeChatMessageUtil.buildImageMessage(toUserName, fromUserName, mediaId);
            }else if (MsgType.VOICE.toString().equals(msgType)) {
                String format = reqMap.get("Format");
                String mediaId = reqMap.get("MediaId");
                logger.info("处理用户语音信息，语音格式format：" + format + "，语音消息媒体id：" + mediaId);
                result = WeChatMessageUtil.buildVoiceMessage(toUserName, fromUserName, mediaId);
            } else if (MsgType.EVENT.toString().equals(msgType)) {
                String event = reqMap.get("Event").toUpperCase();
                logger.info("处理用户事件类型，类型event：" + event);
                if (EventType.SUBSCRIBE.toString().equals(event)) {
                    logger.info("--处理用户订阅事件类型--");
                    result = WeChatMessageUtil.buildTextMessage(toUserName, fromUserName, "***欢迎订阅409逗比公众号***\n小卓卓给您跪拜请安");

                } else if (EventType.UNSUBSCRIBE.toString().equals(event)) {
                    logger.info("--处理用户取消订阅事件类型--");

                    result = "success";

                } else {
                    result = WeChatMessageUtil.buildTextMessage(toUserName, fromUserName, "我是贱贱的小卓卓\uD83D\uDE0F\uD83D\uDE0F\n请问客官想要什么服务?");
                }
            } else {
                logger.info("未知类型，回复关键词");
                result = WeChatMessageUtil.buildTextMessage(toUserName, fromUserName, "我是贱贱的小卓卓\uD83D\uDE0F\uD83D\uDE0F\n请问客官想要什么服务?");
            }
            logger.info("处理用户信息，返回的信息：" + result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
