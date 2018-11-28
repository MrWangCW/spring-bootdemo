package com.wang.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by wangyanwei on 2018/7/2.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class User {

    private Long userId;
    private String userName;
    @JSONField(format = "yyyy-MM-dd")
    private Date date;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
