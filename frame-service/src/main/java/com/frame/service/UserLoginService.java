package com.frame.service;

import java.util.List;

import com.frame.domain.User;
import com.frame.domain.UserLogin;
import com.frame.service.base.BaseService;

public interface UserLoginService extends BaseService<UserLogin, Integer> {
	public List<UserLogin> queryUserDeviceTokenByTeamId(Integer teamId);
	
	public int registDeviceToken(UserLogin userLogin);
}
