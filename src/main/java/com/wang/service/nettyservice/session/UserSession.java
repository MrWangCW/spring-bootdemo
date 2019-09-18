package com.wang.service.nettyservice.session;

import lombok.Data;

/**
 * 用户Session
 * Created by wangyanwei on 2019/6/10.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Data
public class UserSession {

    private String userId;

    private String userName;


    public UserSession() {
    }

    public UserSession(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

}
