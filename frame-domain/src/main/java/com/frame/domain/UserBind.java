package com.frame.domain;

import java.io.Serializable;

/**
 * Created by garnett on 2015/12/9.
 */
public class UserBind implements Serializable {


    private static final long serialVersionUID = 9146129878962975758L;
    // 用户手机号
    private String phoneNumber;

    // 验证码
    private String validCode;

    // 用户IP
    private String userIp;

    // 用户请求头里面的userAgent
    private String userAgent;

    // 微信用户openId
    private String openId;

    // 微信用户 unionId
    private String unionId;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
