package com.frame.dao;

import java.util.List;

import com.frame.dao.base.BaseDao;
import com.frame.domain.MatchApply;

public interface MatchApplyDao extends BaseDao<MatchApply, Long> {
	
	public List<MatchApply> getPerionApplyByLocation(MatchApply matchApply);
	
	public List<MatchApply> selectPersionOutDateApply();
	

}
