package com.frame.service;

import java.util.List;

import com.frame.domain.MatchApply;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.TeamApplyRecordVO;
import com.frame.domain.vo.UserApplyRecordVO;
import com.frame.service.base.BaseService;

public interface MatchApplyService extends BaseService<MatchApply, Long> {
	
	public RemoteResult applyMatch(MatchApply matchApply);
	
	public List<UserApplyRecordVO> queryPersionMatchApply(Integer userId);
	
	public List<TeamApplyRecordVO> queryMineTeamApplyMatch(Integer userId);
	
	public List<TeamApplyRecordVO> queryMineTeamInventMatch(Integer userId);
	
	public Page<MatchApply> getPerionApplyByLocation(Page<MatchApply> page,Double lng, Double lat);
	
	
}
