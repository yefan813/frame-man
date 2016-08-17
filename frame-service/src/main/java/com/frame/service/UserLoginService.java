package com.frame.service;

import java.util.List;

import com.frame.domain.UserLogin;
import com.frame.service.base.BaseService;

public interface UserLoginService extends BaseService<UserLogin, Integer> {
	
	
	/**
	 * @param teamId
	 * @return
	 */
	public List<UserLogin> queryUserDeviceTokenByTeamId(Integer teamId);
	
	/**
	 * @param userLogin
	 * @return
	 */
	public int registDeviceToken(UserLogin userLogin);
	
	/**
	 * @param userIds
	 * @return
	 */
	public List<String> getDeviceTokenByIds(List<Long> userIds);
}
