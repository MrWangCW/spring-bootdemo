package com.wang.domain.wechat;

/**
 * 消息类型
 * Created by wangyanwei on 2018/11/21.
 *
 * @author wangyanwei
 * @version 1.0
 */
public enum MsgType {
    /**
     * 消息类型
     */
    TEXT("文本"),
    IMAGE("图片"),
    VOICE("语音"),
    VIDEO("视频"),
    SHORTVIDEO("小视频"),
    LOCATION("地理位置"),
    LINK("链接"),
    /**
     * event是事件类型
     */
    EVENT("事件");

    private String remark;

    MsgType(String remark) {
        this.remark = remark;
    }


    public String getRemark() {
        return remark;
    }

}
