package com.frame.dao.impl;

import org.springframework.stereotype.Repository;

import com.frame.dao.AppSecretDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.AppSecret;

@Repository("appSecretDao")
public class AppSecretDaoImpl extends BaseDaoImpl<AppSecret, Long> implements AppSecretDao {

	private final static String NAMESPACE = "com.frame.dao.AppSecretDao.";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
