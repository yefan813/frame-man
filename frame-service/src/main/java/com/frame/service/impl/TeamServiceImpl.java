package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.TeamDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Team;
import com.frame.domain.common.Page;
import com.frame.service.TeamService;
import com.frame.service.base.BaseServiceImpl;


@Service("teamService")
public class TeamServiceImpl extends BaseServiceImpl<Team, Long> implements TeamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);
	
	
	@Resource
	private TeamDao teamDao;
	

	@Override
	public BaseDao<Team, Long> getDao() {
		// TODO Auto-generated method stub
		return teamDao;
	}


	@Override
	public List<Team> getAllTeams(Page<Team> page) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Team> getUserTeams(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
