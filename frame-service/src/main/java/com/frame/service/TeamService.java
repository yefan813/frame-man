package com.frame.service;

import java.util.List;

import com.frame.domain.Team;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.TeamVO;
import com.frame.service.base.BaseService;

public interface TeamService extends BaseService<Team, Long> {
	
	/**
	 * 得到所有球队
	 * @param page
	 * @return
	 */
	public List<Team> getAllTeams(Page<Team> page);
	
	/**
	 * 得到用户的球队
	 * @param userId
	 * @return
	 */
	public List<Team> getUserTeams(Long userId);
	
	/**
	 * 创建球队
	 * @param userId
	 * @param team
	 * @return
	 */
	public RemoteResult createTeam(Long userId,Team team);
	
	/**
	 * 查看球队详细信息
	 * @param id
	 * @return
	 */
	public TeamVO getTeamById(Long id);
	
}
