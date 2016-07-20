package com.frame.service;

import java.util.List;

import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserLogin;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;

public interface UserService extends BaseService<User, Long> {
	
	/**
	 * 更新用户信息 通过电话号码
	 * @param user
	 * @return
	 */
	public int updateByTel(User user);
	
	
	public RemoteResult registUser(User user, UserAuths userAuths);
	
	List<User> getNearByUser(UserLogin userLogin);
	
}
