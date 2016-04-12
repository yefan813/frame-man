package com.frame.domain;

import java.io.Serializable;

/**
 * Created by garnett on 2015/11/18.
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 6723005414893784943L;

    // 用户昵称
    private String nickName;

    // 用户头像URL
    private String avatarUrl;

    // 用户ID
    private Long userId;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
