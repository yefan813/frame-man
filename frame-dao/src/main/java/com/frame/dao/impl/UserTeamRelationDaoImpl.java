package com.frame.dao.impl;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserTeamRelationDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.UserTeamRelation;

@Repository("userTeamRelationDao")
public class UserTeamRelationDaoImpl extends BaseDaoImpl<UserTeamRelation, Long> implements UserTeamRelationDao {

	private final static String NAMESPACE = "com.frame.dao.UserTeamRelationDao.";
	
	private final static String SELECT_PLAYGROUNBINFO = "selectPlaygroundInfo";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
