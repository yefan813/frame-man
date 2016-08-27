package com.frame.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.frame.dao.MatchDao;
import com.frame.dao.PlayGroundDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Match;
import com.frame.domain.Playground;
import com.frame.domain.common.Page;
import com.frame.domain.vo.MatchVO;

@Repository("matchDao")
public class MatchDaoImpl extends BaseDaoImpl<Match, Long> implements MatchDao {

	private final static String NAMESPACE = "com.frame.dao.MatchDao.";
	
	private final static String SELECT_PLAYGROUNBINFO = "selectPlaygroundInfo";
	
	private final static String SELECT_MATCHBY_TEAMID = "selectMatchByTeamId";
	
	private final static String SELECT_MATCHBY_TEAMID_COUNT = "selectMatchByTeamIdCount";
	
	@Override
	public List<Playground> getPlaygroundInfo(Map<String, Object> parameters) {
		return this.select(getNameSpace(SELECT_PLAYGROUNBINFO), parameters);
	}

	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<Match> getMatchByTeamId(Page<MatchVO> page, Long teamId) {
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("teamId", teamId);
		params.put("startIndex", page.getStartIndex());
		params.put("pageSize", page.getPageSize());
		params.put("orderField", "created");
		params.put("orderFieldType", "DESC");
		
		return this.selectList(getNameSpace(SELECT_MATCHBY_TEAMID), params);
	}

	@Override
	public int getMatchByTeamIdCount(Long teamId) {
		return select(getNameSpace(SELECT_MATCHBY_TEAMID_COUNT), teamId);
	}

	
	

}
