package com.frame.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.frame.dao.PlayGroundDao;
import com.frame.dao.TeamDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Playground;
import com.frame.domain.Team;

@Repository("teamDao")
public class TeamDaoImpl extends BaseDaoImpl<Team, Long> implements TeamDao {

	private final static String NAMESPACE = "com.frame.dao.TeamDao.";
	
	private final static String SELECT_USERTEAMBY_USERID = "selectUserTeamByUserId";
	
	@Override
	public List<Team> getUserTeams(Long userId) {
		Map<String, Object> parameters  = new HashMap<String,Object>();
		parameters.put("userId", userId);
		return this.selectList(getNameSpace(SELECT_USERTEAMBY_USERID), parameters);
	}

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
