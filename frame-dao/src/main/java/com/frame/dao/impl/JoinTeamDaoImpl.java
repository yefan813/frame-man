package com.frame.dao.impl;

import org.springframework.stereotype.Repository;

import com.frame.dao.JoinTeamDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.JoinTeam;

@Repository("joinTeamDao")
public class JoinTeamDaoImpl extends BaseDaoImpl<JoinTeam, Long> implements JoinTeamDao {

	private final static String NAMESPACE = "com.frame.dao.JoinTeamDao.";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
