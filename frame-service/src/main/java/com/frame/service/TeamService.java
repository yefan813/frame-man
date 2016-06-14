package com.frame.service;

import java.util.List;

import com.frame.domain.Team;
import com.frame.domain.common.Page;
import com.frame.service.base.BaseService;

public interface TeamService extends BaseService<Team, Long> {
	
	public List<Team> getAllTeams(Page<Team> page);
	
	public List<Team> getUserTeams(Long userId);
	
	public Boolean createTeam(Long userId,Team team);
	
}
