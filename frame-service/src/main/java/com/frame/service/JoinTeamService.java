package com.frame.service;

import com.frame.domain.JoinTeam;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.JoinTeamVO;
import com.frame.service.base.BaseService;

public interface JoinTeamService extends BaseService<JoinTeam, Long> {
	
	
	public Page<JoinTeamVO> getJoinTeamVO(Page<JoinTeam> page, JoinTeam joinTeam);
	
	/**
	 * 得到加入球队记录
	 * @param joinTeam
	 * @return
	 */
	public JoinTeamVO getJoinTeamVO(JoinTeam joinTeam);
	
	/**
	 * 
	 * 申请加入球队
	 * @param joinTeam
	 * @return
	 */
	public RemoteResult applyJoinTeam(JoinTeam joinTeam);
	
	
	/**
	 * 同意加入球队
	 * @param joinTeam
	 * @return
	 */
	public RemoteResult agreeApplyJoinTeam(JoinTeam joinTeam);
	
	
	/**
	 * 拒绝加入球队
	 * @param joinTeam
	 * @return
	 */
	public RemoteResult rejectApplyJoinTeam(JoinTeam joinTeam);
	
	
	/**
	 * 邀请加入球队
	 * @param joinTeam
	 * @return
	 */
	public RemoteResult inventJoinTeam(JoinTeam joinTeam);
	
	
	/**
	 * 同意邀请加入球队
	 * @param joinTeam
	 * @return
	 */
	public RemoteResult agreeInventJoinTeam(JoinTeam joinTeam);
	
	
	/**
	 * 拒绝邀请加入
	 * @param joinTeam
	 * @return
	 */
	public RemoteResult rejectInventJoinTeam(JoinTeam joinTeam);
	
	
}
