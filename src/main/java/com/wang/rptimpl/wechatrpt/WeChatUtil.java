package com.wang.rptimpl.wechatrpt;

import com.alibaba.fastjson.JSONObject;
import com.wang.util.HttpClientUtil;
import com.wang.util.StringUtils;
import com.wang.util.propertiesutil.PropertiesUtil;
import com.wang.util.redis.RedisKeyType;
import com.wang.util.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author caoqc
 * @Description: 微信相关
 * @date 2018/5/4 17:41
 */
@Component("WeChatUtil")
public class WeChatUtil {

    @Resource
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String APP_ID = PropertiesUtil.getPropertyMapByName("appID");
    private static final String APP_SECRET = PropertiesUtil.getPropertyMapByName("appSecret");

    /**
     * 获取基础支持的access_token（有效期7200秒，开发者必须在自己的服务全局缓存access_token）
     * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     *
     * @return
     */
    public String getAccessToken() {

        String accessToken = null;

        if (StringUtils.isBlank(APP_ID) || StringUtils.isBlank(APP_SECRET)) {
            throw new RuntimeException("参数非法");
        }

        try {

            String key = String.format(RedisKeyType.WECHAT_ACCESS_TOKEN.getText(), (APP_ID + APP_SECRET));
            if (redisUtil.exists(key)) {
                accessToken = redisUtil.getString(key);
                if (StringUtils.isNotBlank(accessToken)) {
                    return accessToken;
                }
            }

            String url = "https://api.weixin.qq.com/cgi-bin/token";
            String urlStr = url + "?grant_type=client_credential" + "&appid=" + APP_ID + "&secret=" + APP_SECRET;

            String result = HttpClientUtil.doGet(urlStr);
            JSONObject jsonObject = JSONObject.parseObject(result);

            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isNotBlank(errcode) && !"0".equals(errcode)) {
                throw new RuntimeException("获取失败。信息：" + result);
            }

            accessToken = jsonObject.getString("access_token");
            Integer expires = jsonObject.getInteger("expires_in");

            if (StringUtils.isNotBlank(accessToken) && null != expires && expires > 1) {
                expires = expires - 1;
                redisUtil.addStringExipre(key, accessToken, expires);
            }

        } catch (Exception e) {
            logger.info("微信工具类，获取access_token。参数,appid:" + APP_ID);
            logger.info("微信工具类，获取access_token异常。信息：" + e.getMessage());
        }
        return accessToken;
    }

    /**
     * 获取jsapi_ticket
     * 用第一步拿到的access_token 采用http GET方式请求获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket）
     * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
     *
     * @param accessToken
     * @return
     */
    public String getJsApiTicket(String accessToken) {

        String ticket = null;

        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("参数非法");
        }

        try {

            String key = String.format(RedisKeyType.WECHAT_JSAPI_TICKET.getText(), accessToken);
            if (redisUtil.exists(key)) {
                ticket = redisUtil.getString(key);
                if (StringUtils.isNotBlank(ticket)) {
                    return ticket;
                }
            }

            //https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
            String urlStr = url + "?access_token=" + accessToken + "&type=jsapi";
            String result = HttpClientUtil.doGet(urlStr);

            JSONObject jsonObject = JSONObject.parseObject(result);

            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isNotBlank(errcode) && !"0".equals(errcode)) {
                throw new RuntimeException("获取失败。信息：" + result);
            }

            ticket = jsonObject.getString("ticket");
            Integer expires = jsonObject.getInteger("expires_in");

            if (StringUtils.isNotBlank(ticket) && null != expires && expires.intValue() > 1) {
                expires = expires - 1;
                redisUtil.addStringExipre(key, ticket, expires);
            }

        } catch (Exception e) {
            logger.info("微信工具类，获取jsapi_ticket。参数,accessToken:" + accessToken);
            logger.info("微信工具类，获取jsapi_ticket异常。信息：" + e.getMessage());
        }

        return ticket;
    }


    /**
     * 获取网页授权access_token（无次数限制且每次都唯一，不存储服务器）
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     *
     * @param appId
     * @param appSecret
     * @return
     */
    public String getOAuthAccessToken(String appId, String appSecret, String code) {

        String jsonResult = null;

        if (StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret) || StringUtils.isBlank(code)) {
            throw new RuntimeException("参数非法");
        }

        try {

            String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
            String urlStr = url + "?appid=" + appId +
                    "&secret=" + appSecret +
                    "&code=" + code +
                    "&grant_type=" + "authorization_code";

            String result = HttpClientUtil.doGet(urlStr);
            JSONObject jsonObject = JSONObject.parseObject(result);

            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isNotBlank(errcode) && !"0".equals(errcode)) {
                StringBuffer failInfo = new StringBuffer("获取失败。信息：");
                failInfo.append(result);
                throw new RuntimeException(failInfo.toString());
            }
            jsonResult = result;
        } catch (Exception e) {
            logger.info("微信工具类，获取网页授权access_token。参数,appid:" + appId);
            logger.info("微信工具类，获取网页授权access_token异常。信息：" + e.getMessage());
        }
        return jsonResult;
    }


    /**
     * 拉取用户信息 (如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息)
     * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public String getOAuthUserInfo(String accessToken, String openId) {

        String jsonResult = null;

        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openId)) {
            throw new RuntimeException("参数非法");
        }

        try {

            String url = "https://api.weixin.qq.com/sns/userinfo";
            StringBuffer urlStr = new StringBuffer(url);
            urlStr.append("?access_token=").append(accessToken);
            urlStr.append("&openid=").append(openId);
            urlStr.append("&lang=").append("zh_CN");

            String result = HttpClientUtil.doGet(urlStr.toString());
            result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);

            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isNotBlank(errcode) && !"0".equals(errcode)) {
                StringBuffer failInfo = new StringBuffer("获取失败。信息：");
                failInfo.append(result);
                throw new RuntimeException(failInfo.toString());
            }
            jsonResult = result;
        } catch (Exception e) {
            logger.info("微信工具类，网页授权拉取用户信息。参数,openId:" + openId);
            logger.info("微信工具类，获取网页拉取用户信息异常。信息：" + e.getMessage());
        }
        return jsonResult;
    }

    /**
     * 获取用户基本信息(UnionID机制)（详情点击：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839）
     * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public String getIsAttentionInfo(String accessToken, String openId) {

        String jsonResult = null;

        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openId)) {
            throw new RuntimeException("参数非法");
        }

        try {
            String url = "https://api.weixin.qq.com/cgi-bin/user/info";
            StringBuffer urlStr = new StringBuffer(url);
            urlStr.append("?access_token=").append(accessToken);
            urlStr.append("&openid=").append(openId);
            urlStr.append("&lang=").append("zh_CN");

            String result = HttpClientUtil.doGet(urlStr.toString());
            result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);

            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isNotBlank(errcode) && !"0".equals(errcode)) {
                StringBuffer failInfo = new StringBuffer("获取失败。信息：");
                failInfo.append(result);
                throw new RuntimeException(failInfo.toString());
            }
            jsonResult = result;
        } catch (Exception e) {
            logger.info("微信工具类，获取用户是否关注微信公众号信息。参数,openId:" + openId);
            logger.info("微信工具类，获取用户是否关注微信公众号异常。信息：" + e.getMessage());
        }
        return jsonResult;
    }


    /**
     * 获取关注者列表
     * https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
     *
     * @param accessToken
     * @param nextOpenId
     * @return {"total":2,"count":2,"data":{"openid":["OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
     */
    public String batchGetAttentionList(String accessToken, String nextOpenId) {

        String jsonResult = null;

        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("参数非法");
        }

        try {
            String url = "https://api.weixin.qq.com/cgi-bin/user/get";
            StringBuffer urlStr = new StringBuffer(url);
            urlStr.append("?access_token=").append(accessToken);
            if (StringUtils.isNotBlank(nextOpenId)) {
                urlStr.append("&next_openid=").append(nextOpenId);
            }
            String result = HttpClientUtil.doGet(urlStr.toString());
            result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);

            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isNotBlank(errcode) && !"0".equals(errcode)) {
                StringBuffer failInfo = new StringBuffer("获取失败。信息：");
                failInfo.append(result);
                throw new RuntimeException(failInfo.toString());
            }
            jsonResult = result;
        } catch (Exception e) {
            logger.info("微信工具类，获取帐号的关注者列表。参数,accessToken:" + accessToken + ",nextOpenId:" + nextOpenId);
            logger.info("微信工具类，获取帐号的关注者列表异常。信息：" + e.getMessage());
        }
        return jsonResult;
    }


    /**
     * 批量获取用户基本信息
     * https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
     *
     * @param accessToken
     * @param openIdJsonStr
     * @return
     */
    public String batchGetWechatUserInfo(String accessToken, String openIdJsonStr) {

        String jsonResult = null;

        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("参数非法");
        }

        try {
            String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + accessToken;

            String result = HttpClientUtil.doPostJson(url, openIdJsonStr);
            result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);

            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isNotBlank(errcode) && !"0".equals(errcode)) {
                StringBuffer failInfo = new StringBuffer("获取失败。信息：");
                failInfo.append(result);
                throw new RuntimeException(failInfo.toString());
            }
            jsonResult = result;
        } catch (Exception e) {
            logger.info("微信工具类，获取帐号的关注者列表。参数,accessToken:" + accessToken + ",openIdJsonStr:" + openIdJsonStr);
            logger.info("微信工具类，获取帐号的关注者列表异常。信息：" + e.getMessage());
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        String accessToken = "11_ETFkBvt9huINBcMqqpSOhM_7euAHITh-ob4okAmiFKqEasP3NfEPhf4z4UPYQlRtu5Fj31krW2fbJ-KAR2f_SJ7LbLAG7mhIJpw4vm_4z3lu3jzxBWl_gAOooLQpcN5Y1wZBYpndp-m5djRaCDJbAAAIJP";
        String body = "{\"user_list\": [{\"openid\": \"of9pl1Kk9Q6lR6O1kCKJOqcE1ZYI\", \"lang\": \"zh_CN\"}]}";
        WeChatUtil weChatUtil = new WeChatUtil();
        System.out.println(weChatUtil.batchGetWechatUserInfo(accessToken, body));
    }


}