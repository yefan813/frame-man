package com.frame.service;

import com.frame.domain.MatchData;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;

public interface MatchDataService extends BaseService<MatchData, Long> {
	
	public RemoteResult uploadMatchData(MatchData matchData);
}
