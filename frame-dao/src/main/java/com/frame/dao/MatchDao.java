package com.frame.dao;

import java.util.List;
import java.util.Map;

import com.frame.dao.base.BaseDao;
import com.frame.domain.Match;
import com.frame.domain.Playground;
import com.frame.domain.common.Page;
import com.frame.domain.vo.MatchVO;

public interface MatchDao extends BaseDao<Match, Long> {
	
	/**
	 * 查找球场信息
	 * @param parameters
	 * @return
	 */
	public List<Playground> getPlaygroundInfo(Map<String, Object> parameters);
	
	
	public List<Match> getMatchByTeamId(Page<MatchVO> page, Long teamId);
	
	
	public int getMatchByTeamIdCount(Long teamId);
	
}
