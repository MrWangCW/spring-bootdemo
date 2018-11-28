package com.wang.rptimpl.wechatrpt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.wang.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单的管理
 * Created by wangyanwei on 2018/11/24.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class MenuUtil {

    private static Logger logger = LoggerFactory.getLogger(WeChatMessageUtil.class);

    /**
     * 新增自定义菜单 POST，https协议
     * {"errcode":0,"errmsg":"ok"}
     */
    private static final String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    /**
     * 查询菜单 http请求方式：GET
     */
    private static final String GET_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s";

    /**
     * 删除自定义菜单 http请求方式：GET
     * {"errcode":0,"errmsg":"ok"}
     */
    private static final String DEL_MENU = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";


    /**
     * 创建自定义菜单
     *
     * @param accessToken access_token
     * @param menuJson 菜单结构Json数据
     * @return true 创建成功
     */
    public static Boolean createMenu(String accessToken,String menuJson) {
        String url = String.format(CREATE_MENU, accessToken);
        logger.info("请求微信创建自定义菜单开始，url：" + url);
        String result = HttpClientUtil.doPostJson(url, menuJson);
        logger.info("请求微信创建自定义菜单，返回结果：" + result);
        String errCode= JSON.parseObject(result).getString("errcode");
        return "0".equals(errCode);
    }

    /**
     * 查询自定义菜单
     * @param accessToken access_token
     * @return 返回菜单列表的Json数据
     */
    public static JSONObject getMenu(String accessToken) {
        String url = String.format(GET_MENU, accessToken);
        logger.info("请求微信查询自定义菜单，url：" + url);
        String result = HttpClientUtil.doGet(url);
        logger.info("请求微信查询自定义菜单，返回结果：" + result);
        return JSON.parseObject(result);
    }

    /**
     * 删除自定义菜单
     * @param accessToken access_token
     * @return true 删除成功
     */
    public static Boolean delMenu(String accessToken) {
        String url = String.format(DEL_MENU, accessToken);
        logger.info("请求微信删除自定义菜单开始，url：" + url);
        String result = HttpClientUtil.doGet(url);
        logger.info("请求微信删除自定义菜单，返回结果：" + result);
        String errCode= JSON.parseObject(result).getString("errcode");
        return "0".equals(errCode);
    }

    public static void main(String[] args) {
        //一级菜单List
        List<Map<String, Object>> buttonMenuList = new ArrayList<>();
        //一级菜单Map 最多三个
        Map<String, Object> buttonMenuMap;
        //二级菜单List
        List<Map<String, String>> subButtonMenuList;
        //二级菜单Map 最多五个
        Map<String, String> subButtonMenuMap;


        buttonMenuMap = Maps.newHashMap();
        buttonMenuMap.put("name", "一级①");
        buttonMenuMap.put("type", "click");
        buttonMenuMap.put("key", "news");
        buttonMenuList.add(buttonMenuMap);

        buttonMenuMap = Maps.newHashMap();
        buttonMenuMap.put("name", "一级②");
        subButtonMenuList = new ArrayList<>();

        subButtonMenuMap = Maps.newHashMap();
        subButtonMenuMap.put("name", "二级①");
        subButtonMenuMap.put("type", "click");
        subButtonMenuMap.put("key", "news");
        subButtonMenuList.add(subButtonMenuMap);

        subButtonMenuMap = Maps.newHashMap();
        subButtonMenuMap.put("name", "二级②");
        subButtonMenuMap.put("type", "click");
        subButtonMenuMap.put("key", "news");
        subButtonMenuList.add(subButtonMenuMap);

        subButtonMenuMap = Maps.newHashMap();
        subButtonMenuMap.put("name", "二级③");
        subButtonMenuMap.put("type", "click");
        subButtonMenuMap.put("key", "news");
        subButtonMenuList.add(subButtonMenuMap);

        subButtonMenuMap = Maps.newHashMap();
        subButtonMenuMap.put("name", "二级④");
        subButtonMenuMap.put("type", "click");
        subButtonMenuMap.put("key", "news");
        subButtonMenuList.add(subButtonMenuMap);

        subButtonMenuMap = Maps.newHashMap();
        subButtonMenuMap.put("name", "二级⑤");
        subButtonMenuMap.put("type", "click");
        subButtonMenuMap.put("key", "news");
        subButtonMenuList.add(subButtonMenuMap);

        buttonMenuMap.put("sub_button", subButtonMenuList);
        buttonMenuList.add(buttonMenuMap);


        buttonMenuMap = Maps.newHashMap();
        buttonMenuMap.put("name", "一级③");
        buttonMenuMap.put("type", "click");
        buttonMenuMap.put("key", "news");
        buttonMenuList.add(buttonMenuMap);
        Map<String, List> map = new HashMap<>(1);
        map.put("button", buttonMenuList);
        String accessToken = "";
        createMenu(accessToken,JSONObject.toJSONString(map));
    }
}
