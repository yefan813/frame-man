package com.frame.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.frame.dao.MatchDao;
import com.frame.dao.PlayGroundDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Match;
import com.frame.domain.Playground;

@Repository("matchDao")
public class MatchDaoImpl extends BaseDaoImpl<Match, Long> implements MatchDao {

	private final static String NAMESPACE = "com.frame.dao.MatchDao.";
	
	private final static String SELECT_PLAYGROUNBINFO = "selectPlaygroundInfo";
	
	@Override
	public List<Playground> getPlaygroundInfo(Map<String, Object> parameters) {
		return this.select(getNameSpace(SELECT_PLAYGROUNBINFO), parameters);
	}

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
