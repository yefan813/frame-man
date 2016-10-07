package com.frame.dao.impl;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserAuthsDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.UserAuths;

@Repository("userAuthsDao")
public class UserAuthsDaoImpl extends BaseDaoImpl<UserAuths, Long> implements UserAuthsDao {

	private final static String NAMESPACE = "com.frame.dao.UserAuthsDao.";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
