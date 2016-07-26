package com.frame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserLoginDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.User;
import com.frame.domain.UserLogin;

@Repository("userLoginDao")
public class UserLoginDaoImpl extends BaseDaoImpl<UserLogin, Integer> implements UserLoginDao {

	private final static String NAMESPACE = "com.frame.dao.UserLoginDao.";
	
	private final static String SELECT_USERDEVICETOKENENTRYLIST = "selectUserDeviceTokenEntryList";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


	@Override
	public List<UserLogin> queryUserDeviceTokenByTeamId(Integer teamId) {
		return selectList(getNameSpace(SELECT_USERDEVICETOKENENTRYLIST), teamId);
	}

	


}
