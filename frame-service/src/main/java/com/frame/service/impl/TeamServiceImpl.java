package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.TeamDao;
import com.frame.dao.UserTeamRelationDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Team;
import com.frame.domain.UserTeamRelation;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.service.TeamService;
import com.frame.service.base.BaseServiceImpl;


@Service("teamService")
public class TeamServiceImpl extends BaseServiceImpl<Team, Long> implements TeamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);
	
	
	@Resource
	private TeamDao teamDao;
	
	@Resource
	private UserTeamRelationDao userTeamRelationDao;
	

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
	public List<Team> getUserTeams(Long userId) {
		// TODO Auto-generated method stub
		return teamDao.getUserTeams(userId);
	}


	@Override
	@Transactional
	public Boolean createTeam(Long userId, Team team) {
		boolean res = false;
		teamDao.insertEntryCreateId(team);
		
		UserTeamRelation utRelation  =  new UserTeamRelation();
		utRelation.setTeamId(team.getId().longValue());
		utRelation.setTeamName(team.getName());
		utRelation.setUserId(userId);
		utRelation.setType(UserTeamRelation.TEAM_TYPE_CAPTURE);
		utRelation.setYn(YnEnum.Normal.getKey());
		int result = userTeamRelationDao.insertEntry(utRelation);
		if(result > 0){
			res = true;
		}
		return res;
	}
	

}
