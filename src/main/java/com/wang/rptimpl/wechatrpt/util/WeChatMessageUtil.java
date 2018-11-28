package com.wang.rptimpl.wechatrpt.util;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回复消息构造
 * Created by wangyanwei on 2018/11/21.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Component("weChatMessageUtil")
public class WeChatMessageUtil {

    private static Logger logger = LoggerFactory.getLogger(WeChatMessageUtil.class);

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return map
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            logger.info("参数：" + e.getName() + "：" + e.getText());
            map.put(e.getName(), e.getText());
        }

        // 释放资源
        inputStream.close();
        return map;
    }

    /**
     * 构造文本消息
     *
     * @param fromUserName 发送方帐号 开发者微信号
     * @param toUserName   接收方帐号（用户OpenID）
     * @param content      内容
     * @return
     */
    public static String buildTextMessage(String fromUserName, String toUserName, String content) {

        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>",
                toUserName, fromUserName, getUtcTime(), content);
    }


    /**
     * 构造图片消息
     *
     * @param fromUserName 发送方帐号 开发者微信号
     * @param toUserName   接收方帐号（用户OpenID）
     * @param mediaId      图片消息媒体id
     * @return
     */
    public static String buildImageMessage(String fromUserName, String toUserName, String mediaId) {

        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[image]]></MsgType>" +
                        "<Image><MediaId><![CDATA[%s]]></MediaId></Image>" +
                        "</xml>",
                toUserName, fromUserName, getUtcTime(), mediaId);
    }

    /**
     * 构造语音消息
     *
     * @param fromUserName 发送方帐号 开发者微信号
     * @param toUserName   接收方帐号（用户OpenID）
     * @param mediaId      图片消息媒体id
     * @return
     */
    public static String buildVoiceMessage(String fromUserName, String toUserName, String mediaId) {

        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[voice]]></MsgType>" +
                        "<Voice><MediaId><![CDATA[%s]]></MediaId></Voice>" +
                        "</xml>",
                toUserName, fromUserName, getUtcTime(), mediaId);
    }

    /**
     * 构造视频消息
     *
     * @param fromUserName 发送方帐号 开发者微信号
     * @param toUserName   接收方帐号（用户OpenID）
     * @param title        视频消息的标题  非必须项
     * @param description  视频消息的描述  非必须项
     * @param mediaId      视频消息媒体id
     * @return
     */
    public static String buildVideoMessage(String fromUserName, String toUserName, String title, String description, String mediaId) {
        //String title = "客官发过来的视频哟~~";
        //String description = "客官您呐,现在肯定很开心,对不啦 嘻嘻��";
        /*返回用户发过来的视频*/
        //String media_id = map.get("MediaId");
        //String media_id = "hTl1of-w78xO-0cPnF_Wax1QrTwhnFpG1WBkAWEYRr9Hfwxw8DYKPYFX-22hAwSs";
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[video]]></MsgType>" +
                        "<Video>" +
                        "   <MediaId><![CDATA[%s]]></MediaId>" +
                        "   <Title><![CDATA[%s]]></Title>" +
                        "   <Description><![CDATA[%s]]></Description>" +
                        "</Video>" +
                        "</xml>",
                toUserName, fromUserName, getUtcTime(), mediaId, title, description);
    }

    /**
     * 构造音乐消息
     *
     * @param fromUserName 发送方帐号 开发者微信号
     * @param toUserName   接收方帐号（用户OpenID）
     * @param title        音乐标题  非必须项
     * @param description  音乐描述  非必须项
     * @param musicUrl     音乐链接  非必须项
     * @param hqMusicUrl   高质量音乐链接，WIFI环境优先使用该链接播放音乐  非必须项
     * @return
     */
    public static String buildMusicMessage(String fromUserName, String toUserName, String title, String description, String musicUrl, String hqMusicUrl,String thumbMediaId) {

        //String title = "亲爱的路人";
        //String description = "多听音乐 心情棒棒 嘻嘻��";
        //String hqMusicUrl = "http://www.kugou.com/song/20qzz4f.html?frombaidu#hash=20C16B9CCCCF851D1D23AF52DD963986&album_id=0";
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[music]]></MsgType>" +
                        "<Music>" +
                            "<Title><![CDATA[%s]]></Title>" +
                            "<Description><![CDATA[%s]]></Description>" +
                            "<MusicUrl>< ![CDATA[%s] ]></MusicUrl>" +
                            "<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>" +
                            "<ThumbMediaId>< ![CDATA[%s] ]></ThumbMediaId>" +
                        "</Music>" +
                        "</xml>",
                toUserName, fromUserName, getUtcTime(), title, description, musicUrl, hqMusicUrl,thumbMediaId);
    }

    /**
     * 返回图文消息
     *
     * @param fromUserName 发送方帐号 开发者微信号
     * @param toUserName   接收方帐号（用户OpenID）
     *                     当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；
     *                     其余场景最多可回复8条图文消息
     * @return
     */
    public static String buildNewsMessage(String fromUserName, String toUserName) {

        String accessToken = "15_kOJbj7FiZ6GoG9I_hBl2SI-IbMBHWs0eKkdXKOzLlbWtRgdk4DUlP2b_DgvcFTxJhcEU0VNP3H-WTEpzv4zzr_dTrHKdv1xfRqWTsBhibpODOL3gDLYhbCg1aHy7UKNme_7Xlkm49WhUINSEVSFbABABYL";
        String filePath;
        File file;
        String type = "IMAGE";
        JSONObject jsonObject;


        String title1 = "HAP审计的实现和使用";
        String description1 = "由于HAP框架用的是Spring+SpringMVC+Mybatis，其自用户对的审计。";
        filePath = "F:\\迅雷下载\\222.jpg";
        file = new File(filePath);
        jsonObject = UploadMediaUtil.uploadMedia(file, accessToken, type, 3);
        String picUrl1 = jsonObject.getString("url");
        String textUrl1 = "http://blog.csdn.net/a1786223749/article/details/78330890";

        String title2 = "这是标题";
        String description2 = "这是图文消息描述";
        filePath = "F:\\迅雷下载\\baozi.jpg";
        file = new File(filePath);

        jsonObject = UploadMediaUtil.uploadMedia(file, accessToken, type, 3);
        String picUrl2 = jsonObject.getString("url");
        String textUrl2 = "http://www.baidu.com";
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[news]]></MsgType>" +
                        "<ArticleCount>2</ArticleCount>" + //图文消息个数，限制为8条以内
                        "<Articles>" + //多条图文消息信息，默认第一个item为大图
                            "<item>" +
                                "<Title><![CDATA[%s]]></Title> " +
                                "<Description><![CDATA[%s]]></Description>" +
                                "<PicUrl><![CDATA[%s]]></PicUrl>" + //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
                                "<Url><![CDATA[%s]]></Url>" + //点击图文消息跳转链接
                            "</item>" +
                            "<item>" +
                                "<Title><![CDATA[%s]]></Title>" +
                                "<Description><![CDATA[%s]]></Description>" +
                                "<PicUrl><![CDATA[%s]]]></PicUrl>" +
                                "<Url><![CDATA[%s]]]></Url>" +
                            "</item>" +
                        "</Articles>" +
                        "</xml>",
                toUserName, fromUserName, getUtcTime(),
                title1, description1, picUrl1, textUrl1,
                title2, description2, picUrl2, textUrl2
        );
    }


    /**
     * 发送消息时间（整型）
     *
     * @return
     */
    private static String getUtcTime() {
        long dd = System.currentTimeMillis();
        return String.valueOf(dd);
    }

}
