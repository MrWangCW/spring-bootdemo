package com.wang.domain.wechat;

/**
 * 群发消息类型
 * Created by wangyanwei on 2018/11/27.
 *
 * @author wangyanwei
 * @version 1.0
 */
public enum AllMsgType {

    /**
     * 群发消息类型
     */
    text("文本"),
    image("图片"),
    voice("语音"),
    mpnews("图文消息"),
    music("音乐"),
    mpvideo("视频"),
    wxcard("卡券");

    private String remark;

    AllMsgType(String remark) {
        this.remark = remark;
    }


    public String getRemark() {
        return remark;
    }

}
