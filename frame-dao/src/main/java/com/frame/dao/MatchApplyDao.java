package com.frame.dao;

import java.util.List;
import java.util.Map;

import com.frame.dao.base.BaseDao;
import com.frame.domain.Match;
import com.frame.domain.MatchApply;
import com.frame.domain.Playground;

public interface MatchApplyDao extends BaseDao<MatchApply, Long> {
	
	public List<MatchApply> getPerionApplyByLocation(MatchApply matchApply);
	

}
