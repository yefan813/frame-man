package com.frame.service;

import java.util.List;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey.Match;
import com.frame.domain.MatchApply;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.MatchApplyVO;
import com.frame.service.base.BaseService;

public interface MatchApplyService extends BaseService<MatchApply, Long> {
	
	
	public RemoteResult joinPersionMatchApply(Integer persionApplyId, Integer userId);
	
	/**
	 * 申请比赛
	 * @param matchApply
	 * @return
	 */
	public RemoteResult applyMatch(MatchApply matchApply);
	
	/**
	 * 更具userid查找个人月球记录
	 * @param userId
	 * @return
	 */
	public List<MatchApplyVO> queryPersionMatchApply(Integer userId);
	
	/**
	 * 根据userid 查看我的球队月球记录
	 * @param userId
	 * @return
	 */
	public List<MatchApplyVO> queryMineTeamApplyMatch(Integer userId);
	
	/**
	 * 根据userid 查看我的球队被邀请比赛记录
	 * @param userId
	 * @return
	 */
	public List<MatchApplyVO> queryMineTeamInventMatch(Integer userId);
	
	/**
	 * 
	 * 得到个人约球记录
	 * @param page
	 * @param lng
	 * @param lat
	 * @return
	 */
	public Page<MatchApplyVO> getPerionApplyByLocation(Page<MatchApply> page,Double lng, Double lat);
	
	/**
	 * 更具月球信息得到详细信息
	 * @param matchApply
	 * @return
	 */
	public MatchApplyVO getMatchApplyById(MatchApply matchApply);
	
	
	public List<MatchApply> selectPersionOutDateApply();
	
	
}
