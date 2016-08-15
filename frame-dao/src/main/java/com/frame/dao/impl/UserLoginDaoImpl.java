package com.frame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserLoginDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.UserLogin;

@Repository("userLoginDao")
public class UserLoginDaoImpl extends BaseDaoImpl<UserLogin, Integer> implements UserLoginDao {

	private final static String NAMESPACE = "com.frame.dao.UserLoginDao.";
	
	private final static String SELECT_USERDEVICETOKENENTRYLIST = "selectUserDeviceTokenEntryList";
	
	private final static String REGIST_DEVICES = "registDeviceToken";
	
	private final static String SELECT_DEVICETOKENSBY_USERIDS = "selectDeviceTokensByUserIds";

	
	
	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


	@Override
	public List<UserLogin> queryUserDeviceTokenByTeamId(Integer teamId) {
		return selectList(getNameSpace(SELECT_USERDEVICETOKENENTRYLIST), teamId);
	}


	@Override
	public int registDeviceToken(UserLogin userLogin) {
		return update(getNameSpace(REGIST_DEVICES), userLogin);
	}


	@Override
	public List<String> getDeviceTokenByIds(List<Long> userIds) {
		return selectList(getNameSpace(SELECT_DEVICETOKENSBY_USERIDS), userIds);
	}

	


}
