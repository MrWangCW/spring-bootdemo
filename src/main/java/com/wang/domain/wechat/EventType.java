package com.wang.domain.wechat;

/**
 * 事件类型
 * Created by wangyanwei on 2018/11/21.
 *
 * @author wangyanwei
 * @version 1.0
 */
public enum EventType {

    /**
     * 事件类型
     */
    SUBSCRIBE("订阅"),
    UNSUBSCRIBE("取消订阅"),
    SCAN("已关注时扫描二维码"),
    LOCATION("地理位置"),
    CLICK("点击菜单"),
    VIEW("点击菜单跳转链接");

    private String remark;

    EventType(String remark) {
        this.remark = remark;
    }


    public String getRemark() {
        return remark;
    }

}
