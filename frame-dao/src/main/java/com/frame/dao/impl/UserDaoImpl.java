package com.frame.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.MatchApply;
import com.frame.domain.User;
import com.frame.domain.UserLogin;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

	private final static String NAMESPACE = "com.frame.dao.UserDao.";

	private final static String UPDATE_BY_TEL = "updateByTel";
	private final static String GETNEARBYUSER = "getNearByUser";
	private final static String GETNEARBYUSERCOUNT = "getNearByUserCount";
	private final static String GETUSERJOINPERSIONAPPLYRECORD = "getUserJoinPersionApplyRecord";
	private final static String SELECTUSERLISTBYTEAMID = "selectUserListByTeamId";
	
	private final static String QUERYFRIENDSBYTELORNICKNAME = "queryFriendsByTelOrNickName";

	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public int updateUserByTel(User user) {
		return this.update(getNameSpace(UPDATE_BY_TEL), user);
	}

	@Override
	public List<User> getNearByUser(UserLogin userLogin) {
		return selectList(getNameSpace(GETNEARBYUSER), userLogin);
	}

	@Override
	public int getNearByUserCount(UserLogin userLogin) {
		return select(getNameSpace(GETNEARBYUSERCOUNT), userLogin);
	}

	@Override
	public List<User> getUserJoinPersionApplyRecord(MatchApply matchApply) {
		return selectList(getNameSpace(GETUSERJOINPERSIONAPPLYRECORD), matchApply);
	}

	@Override
	public List<User> getTeamUserByTeamId(Long teamId) {
		return selectList(getNameSpace(SELECTUSERLISTBYTEAMID), teamId);
	}

	@Override
	public List<User> queryFriendsByTelOrNickName(Map<String, Object> params) {
		return selectList(getNameSpace(QUERYFRIENDSBYTELORNICKNAME), params);
	}

}
