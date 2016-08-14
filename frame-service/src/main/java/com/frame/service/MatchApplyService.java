package com.frame.service;

import java.util.List;

import com.frame.domain.MatchApply;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.MatchApplyVO;
import com.frame.service.base.BaseService;

public interface MatchApplyService extends BaseService<MatchApply, Long> {
	
	public RemoteResult applyMatch(MatchApply matchApply);
	
	public List<MatchApplyVO> queryPersionMatchApply(Integer userId);
	
	public List<MatchApplyVO> queryMineTeamApplyMatch(Integer userId);
	
	public List<MatchApplyVO> queryMineTeamInventMatch(Integer userId);
	
	public Page<MatchApplyVO> getPerionApplyByLocation(Page<MatchApply> page,Double lng, Double lat);
	
	public MatchApplyVO getMatchApplyById(MatchApply matchApply);
	
	
}
