package com.frame.service;

import com.frame.domain.User;
import com.frame.domain.UserBind;

/**
 * Created by garnett on 2015/11/18.
 */
public interface PassportService {

    //根据微信unionId获取用户信息
    public User getUserInfoByUnionId(String unionId); 
    
    /**
     * 根据微信userId获取用户信息
     * @param userId
     * @return
     */
    public User getUserInfoByUserId(Long userId);

    // 根据微信unionId判断用户是否绑定
    public boolean isUserBindedByUnionId(String unionId);

    // 绑定微信用户
    public boolean bindWeChatUser(UserBind userBind);
}
