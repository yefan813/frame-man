package com.frame.service;

import com.frame.domain.Match;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;

public interface MatchService extends BaseService<Match, Long> {
	
	
	public RemoteResult createMatch(Match match);

}
