package com.frame.dao.impl;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserValidDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.UserValid;

@Repository("userValidDao")
public class UserValidDaoImpl extends BaseDaoImpl<UserValid, Long> implements UserValidDao {

	private final static String NAMESPACE = "com.frame.dao.UserValidDao.";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
