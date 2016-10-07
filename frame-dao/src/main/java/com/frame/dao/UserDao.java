package com.frame.dao;

import java.util.List;
import java.util.Map;

import com.frame.dao.base.BaseDao;
import com.frame.domain.MatchApply;
import com.frame.domain.User;
import com.frame.domain.UserLogin;

public interface UserDao extends BaseDao<User, Long> {

	public int updateUserByTel(User user);

	public List<User> getNearByUser(UserLogin userLogin);

	public int getNearByUserCount(UserLogin userLogin);
	
	public List<User> getUserJoinPersionApplyRecord(MatchApply matchApply);
	
	public List<User> getTeamUserByTeamId(Long teamId);
	
	public List<User> queryFriendsByTelOrNickName(Map<String, Object> params);

}
