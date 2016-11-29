package com.frame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.frame.dao.MatchApplyDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.MatchApply;

@Repository("matchApplyDao")
public class MatchApplyDaoImpl extends BaseDaoImpl<MatchApply, Long> implements MatchApplyDao {

	private final static String NAMESPACE = "com.frame.dao.MatchApplyDao.";
	
	private static final String SELECT_PERSIONAPPLYBY_LOCATION = "selectPersionApplyByLocation";
	
	private static final String SELECT_PERSIONOUTDATEAPPLY = "selectPersionOutDateApply";
	

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}

	
	@Override
	public List<MatchApply> getPerionApplyByLocation(MatchApply matchApply) {
		return selectList(getNameSpace(SELECT_PERSIONAPPLYBY_LOCATION), matchApply);
	}


	@Override
	public List<MatchApply> selectPersionOutDateApply() {
		return selectList(getNameSpace(SELECT_PERSIONOUTDATEAPPLY), null);
	}
	

}
