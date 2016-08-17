package com.frame.service;

import java.util.List;

import com.frame.domain.MatchApply;
import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserLogin;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;

public interface UserService extends BaseService<User, Long> {
	
	/**
	 * 更新用户信息 通过电话号码
	 * @param user
	 * @return
	 */
	public int updateByTel(User user);
	
	
	/**
	 * 注册用户
	 * @param user
	 * @param userAuths
	 * @return
	 */
	public RemoteResult registUser(User user, UserAuths userAuths);
	
	/**
	 * 查找附近用户
	 * @param page
	 * @param userLogin
	 * @return
	 */
	public RemoteResult getNearByUser(Page<User> page, UserLogin userLogin);
	
	/**
	 * 得到用户加入个人约球记录
	 * @param matchApply
	 * @return
	 */
	public List<User> getUserJoinPersionApplyRecord(MatchApply matchApply);
	
	/**
	 * 得到球队用户
	 * @param teamId
	 * @return
	 */
	public List<User> getTeamUserByTeamId(Long teamId);
	
}
