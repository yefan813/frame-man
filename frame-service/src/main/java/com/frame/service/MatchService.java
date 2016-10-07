package com.frame.service;

import com.frame.domain.Match;
import com.frame.domain.MatchData;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.MatchVO;
import com.frame.service.base.BaseService;

public interface MatchService extends BaseService<Match, Long> {
	
	
	public RemoteResult createMatch(Match match);
	
	public RemoteResult getMatchByTeamId(Page<MatchVO> page, Long teamId);
	
	public int getMatchByTeamIdCount(Long teamId);

}
