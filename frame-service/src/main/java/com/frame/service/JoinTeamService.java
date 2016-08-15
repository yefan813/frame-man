package com.frame.service;

import com.frame.domain.JoinTeam;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.JoinTeamVO;
import com.frame.service.base.BaseService;

public interface JoinTeamService extends BaseService<JoinTeam, Long> {
	
	public JoinTeamVO geJoinTeamVO(JoinTeam joinTeam);
	
	public RemoteResult applyJoinTeam(JoinTeam joinTeam);
	
	
	public RemoteResult agreeApplyJoinTeam(JoinTeam joinTeam);
	
	
	public RemoteResult rejectApplyJoinTeam(JoinTeam joinTeam);
	
	
	public RemoteResult inventJoinTeam(JoinTeam joinTeam);
	
	
	public RemoteResult agreeInventJoinTeam(JoinTeam joinTeam);
	
	
	public RemoteResult rejectInventJoinTeam(JoinTeam joinTeam);
	
	
}
