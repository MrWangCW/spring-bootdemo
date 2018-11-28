package com.wang.rptimpl.wechatrpt.util;

import com.alibaba.fastjson.JSONObject;
import com.wang.domain.wechat.AllMsgType;
import com.wang.util.HttpClientUtil;
import com.wang.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 群发消息
 * Created by wangyanwei on 2018/11/26.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class SendAllMessageUtil {

    /**
     * 群发图文消息的过程如下：
     * <p>
     * 1、首先，预先将图文消息中需要用到的图片，使用上传图文消息内图片接口，上传成功并获得图片 URL；
     * 2、上传图文消息素材，需要用到图片时，请使用上一步获取的图片 URL；
     * 3、使用对用户标签的群发，或对 OpenID 列表的群发，将图文消息群发出去，群发时微信会进行原创校验，并返回群发操作结果；
     * 4、在上述过程中，如果需要，还可以预览图文消息、查询群发状态，或删除已群发的消息等。
     * <p>
     * 群发图片、文本等其他消息类型的过程如下：
     * <p>
     * 1、如果是群发文本消息，则直接根据下面的接口说明进行群发即可；
     * 2、如果是群发图片、视频等消息，则需要预先通过素材管理接口准备好 mediaID。
     */
    private static Logger logger = LoggerFactory.getLogger(WeChatMessageUtil.class);

    /**
     * 上传图文消息素材 POST，https协议
     */
    private static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=%s";

    /**
     * 根据标签进行群发【订阅号与服务号认证后均可用】 POST，https协议
     */
    private static final String SEND_ALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=%s";

    /**
     * 预览接口 发送消息给指定用户预览【订阅号与服务号认证后均可用】 POST，https协议
     */
    private static final String PREVIEW = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=%s";

    /**
     * 删除群发【订阅号与服务号认证后均可用】 POST，https协议
     */
    private static final String DELETE = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=%s";

    /**
     * 查询群发消息发送状态【订阅号与服务号认证后均可用】 POST，https协议
     */
    private static final String GETMSG_STATE = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=%s";

    /**
     * 上传图文消息素材
     *
     * @param accessToken  access_token
     * @param articlesJson 图文消息Json数据
     * @return {"type":"news","media_id":"xxx","created_at":1391857799}
     */
    public static JSONObject uploadNews(String accessToken, String articlesJson) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(articlesJson)) {
            logger.info("上传图文消息素材,参数错误，accessToken" + accessToken + ",articlesJson" + articlesJson);
            return null;
        }
        String url = String.format(UPLOAD_NEWS, accessToken);
        logger.info("上传图文消息素材开始，url：" + url);
        String result = HttpClientUtil.doPostJson(url, articlesJson);
        logger.info("上传图文消息素材，返回结果：" + result);
        return JSONObject.parseObject(result);
    }

    /**
     * 构造群发消息并发送  一天只能发送一条
     *
     * @param accessToken access_token
     * @param title       消息的标题 可为空
     * @param description 消息的描述 可为空
     * @param isToAll     用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，
     *                    选择false可根据tag_id发送给指定群组的用户
     * @param tagId       群发到的标签的tag_id，参见用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id
     * @param mediaId     用于群发的消息的media_id  上传图文消息素材所得到的media_id
     * @param allMsgType  群发消息的类型
     * @return {"errcode":0,"errmsg":"send job submission success","msg_id":34182,"msg_data_id": 206227730}
     */
    public static JSONObject buildSendAllMessage(String accessToken, String title, String description, Boolean isToAll, String tagId, String mediaId, AllMsgType allMsgType) {
        Map<String, Object> msgMap = new HashMap<>(5);
        //用于设定图文消息的接收者Map
        Map<String, Object> filterMap = new HashMap<>(2);
        filterMap.put("is_to_all", isToAll);
        if (!isToAll) {
            filterMap.put("tag_id", tagId);
        }
        msgMap.put("filter", filterMap);

        //群发消息类型Map
        Map<String, String> allMsgTypeMap = new HashMap<>(1);
        if (AllMsgType.text.equals(allMsgType)) {
            allMsgTypeMap.put("content", mediaId);
        } else if (AllMsgType.wxcard.equals(allMsgType)) {
            allMsgTypeMap.put("card_id", mediaId);
        } else {
            allMsgTypeMap.put("media_id", mediaId);
        }

        msgMap.put(allMsgType.toString(), allMsgTypeMap);

        msgMap.put("msgtype", allMsgType.toString());

        if (StringUtils.isNotBlank(title)) {
            msgMap.put("title", title);
        }
        if (StringUtils.isNotBlank(description)) {
            msgMap.put("description", description);
        }
        String msgJson = JSONObject.toJSONString(msgMap);
        logger.info("构造群发消息:" + msgJson);
        String url = String.format(SEND_ALL, accessToken);
        logger.info("群发消息，url：" + url);
        String result = HttpClientUtil.doPostJson(url, msgJson);
        logger.info("群发消息，返回结果：" + result);
        return JSONObject.parseObject(result);
    }


    /**
     * 构造消息预览并发送  一天只能发送100条
     *
     * @param accessToken access_token
     * @param touser      接收消息用户对应该公众号的openid
     * @param mediaId     用于群发的消息的media_id  上传图文消息素材所得到的media_id
     * @param allMsgType  群发消息的类型
     * @return {"errcode":0,"errmsg":"preview success","msg_id":34182}
     */
    public static JSONObject buildPreviewMessage(String accessToken, String touser, String mediaId, AllMsgType allMsgType) {
        Map<String, Object> msgMap = new HashMap<>(5);
        //用于设定图文消息的接收者Map
        msgMap.put("touser", touser);

        //群发消息类型Map
        Map<String, String> allMsgTypeMap = new HashMap<>(1);
        if (AllMsgType.text.equals(allMsgType)) {
            allMsgTypeMap.put("content", mediaId);
        } else if (AllMsgType.wxcard.equals(allMsgType)) {
            allMsgTypeMap.put("card_id", mediaId);
        } else {
            allMsgTypeMap.put("media_id", mediaId);
        }

        msgMap.put(allMsgType.toString(), allMsgTypeMap);

        msgMap.put("msgtype", allMsgType.toString());

        String msgJson = JSONObject.toJSONString(msgMap);
        logger.info("构造消息预览:" + msgJson);
        String url = String.format(PREVIEW, accessToken);
        logger.info("消息预览，url：" + url);
        String result = HttpClientUtil.doPostJson(url, msgJson);
        logger.info("消息预览，返回结果：" + result);
        return JSONObject.parseObject(result);
    }

    /**
     * 删除群发消息
     *
     * @param accessToken access_token
     * @param msgId       发送出去的消息ID
     * @param articleIdx  要删除的文章在图文消息中的位置，第一篇编号为1，该字段不填或填0会删除全部文章
     * @return {"errcode":0,"errmsg":"ok"}
     */
    public static JSONObject deleteMessage(String accessToken, String msgId, String articleIdx) {
        Map<String, Object> msgMap = new HashMap<>(5);

        msgMap.put("msgId", msgId);
        msgMap.put("articleIdx", articleIdx);

        String msgJson = JSONObject.toJSONString(msgMap);
        logger.info("删除群发消息:" + msgJson);
        String url = String.format(DELETE, accessToken);
        logger.info("删除群发消息，url：" + url);
        String result = HttpClientUtil.doPostJson(url, msgJson);
        logger.info("删除群发消息，返回结果：" + result);
        return JSONObject.parseObject(result);
    }

    /**
     * 查询群发消息发送状态
     *
     * @param accessToken access_token
     * @param msgId       发送出去的消息ID
     * @return {"msg_id":201053012,"msg_status":"SEND_SUCCESS"}
     */
    public static JSONObject getStateMessage(String accessToken, String msgId) {
        Map<String, Object> msgMap = new HashMap<>(5);

        msgMap.put("msgId", msgId);

        String msgJson = JSONObject.toJSONString(msgMap);
        logger.info("查询群发消息发送状态:" + msgJson);
        String url = String.format(GETMSG_STATE, accessToken);
        logger.info("查询群发消息发送状态，url：" + url);
        String result = HttpClientUtil.doPostJson(url, msgJson);
        logger.info("查询群发消息发送状态，返回结果：" + result);
        return JSONObject.parseObject(result);
    }

    public static void main(String[] args) {
        String accessToken = "16_DhAAzrtWtPK0zYCL8WekUrPq-bhLtYF6hl06XRt2GT2AdHUtCZQZTjzhhFwNNRy-JOJRx9szmH3IK3tghezv8TJKxBETARXbVX1WwGrniP6k5-a2ykAuzaMy-H4y0buXQ5R8Y19WSUWRACuAYENcABAIOX";
        /*List<Map> articlesList = new ArrayList<>();

        Map<String, String> paramsMap;
        //第一个
        paramsMap = Maps.newHashMap();
        paramsMap.put("thumb_media_id","ZriiZlhU1__sDTOIQnVf5MFkDyIOhpxonKcy3zbbF-LfJbKsZtqpjdBbUHWvGkev");
        paramsMap.put("author","作者1");
        paramsMap.put("title","标题1");
        paramsMap.put("content_source_url","www.qq.com");
        paramsMap.put("content","图文消息页面的内容，支持HTML标签。具备微信支付权限的公众号，可以使用a标签，其他公众号不能使用，如需插入小程序卡片，可参考下文。");
        paramsMap.put("digest","图文消息的描述，如本字段为空，则默认抓取正文前64个字");
        paramsMap.put("show_cover_pic","1");
        paramsMap.put("need_open_comment","1");
        paramsMap.put("only_fans_can_comment","1");
        articlesList.add(paramsMap);
        //第二个
        paramsMap = Maps.newHashMap();
        paramsMap.put("thumb_media_id","o_twRp6sRFRH79CP5i0ECv7BiH_bK1v7yvBE3-0B9aAQVYPiR0fKo1V8mZheGU2e");
        paramsMap.put("author","作者2");
        paramsMap.put("title","标题2");
        paramsMap.put("content_source_url","www.qq.com");
        paramsMap.put("content","图文消息页面的内容，支持HTML标签。具备微信支付权限的公众号，可以使用a标签，其他公众号不能使用，如需插入小程序卡片，可参考下文。");
        paramsMap.put("digest","图文消息的描述，如本字段为空，则默认抓取正文前64个字");
        paramsMap.put("show_cover_pic","0");
        paramsMap.put("need_open_comment","0");
        paramsMap.put("only_fans_can_comment","0");
        articlesList.add(paramsMap);


        Map<String, List> articlesMap = new HashMap<>(1);
        articlesMap.put("articles", articlesList);
        String news = JSONObject.toJSONString(articlesMap);

        uploadNews(accessToken, news);*/

        /*{"type":"news","media_id":"7ktnxwFI2_dA7gG798_GxpRm1OP5jZG5RRMwDOfZDYZq9gyhfNbD49V_MvBK3t0p","created_at":1543306176}*/
        String mpnewsMedia_id = "7ktnxwFI2_dA7gG798_GxpRm1OP5jZG5RRMwDOfZDYZq9gyhfNbD49V_MvBK3t0p";
        String imgMediaId = "o_twRp6sRFRH79CP5i0ECv7BiH_bK1v7yvBE3-0B9aAQVYPiR0fKo1V8mZheGU2e";
        //buildSendAllMessage(accessToken, "消息的标题", "消息的描述", Boolean.TRUE, "", mpnewsMedia_id, AllMsgType.mpnews);
        buildPreviewMessage(accessToken, "ogc0t1N5kaVlOKd7YBmOsNlsUZMs", mpnewsMedia_id, AllMsgType.mpnews);
        /*{"filter":"{\"is_to_all\":\"true\"}","mpnews":"{\"media_id\":\"7ktnxwFI2_dA7gG798_GxpRm1OP5jZG5RRMwDOfZDYZq9gyhfNbD49V_MvBK3t0p\"}","msgtype":"mpnews"}*/
        /*{"filter":{"is_to_all":false,"tag_id":2},"text":{"content":"CONTENT"},"msgtype":"text"}*/

    }
}
