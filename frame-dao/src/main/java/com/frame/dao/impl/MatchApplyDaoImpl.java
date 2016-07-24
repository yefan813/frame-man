package com.frame.dao.impl;

import org.springframework.stereotype.Repository;

import com.frame.dao.MatchApplyDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.MatchApply;

@Repository("matchApplyDao")
public class MatchApplyDaoImpl extends BaseDaoImpl<MatchApply, Long> implements MatchApplyDao {

	private final static String NAMESPACE = "com.frame.dao.MatchApplyDao.";
	

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
