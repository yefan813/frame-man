package com.frame.service;

import java.util.List;

import com.frame.domain.Match;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;

public interface MatchService extends BaseService<Match, Long> {
	
	
	public RemoteResult createMatch(Match match);
	
	public RemoteResult getMatchByTeamId(Page<Match> page, Long teamId);
	
	public int getMatchByTeamIdCount(Long teamId);

}
