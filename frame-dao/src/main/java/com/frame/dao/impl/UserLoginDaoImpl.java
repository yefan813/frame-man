package com.frame.dao.impl;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserLoginDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.UserLogin;

@Repository("userLoginDao")
public class UserLoginDaoImpl extends BaseDaoImpl<UserLogin, Long> implements UserLoginDao {

	private final static String NAMESPACE = "com.frame.dao.UserLoginDao.";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
