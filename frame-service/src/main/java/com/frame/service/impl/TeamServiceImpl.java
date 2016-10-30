package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.frame.dao.TeamDao;
import com.frame.dao.UserTeamRelationDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Team;
import com.frame.domain.User;
import com.frame.domain.UserTeamRelation;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.TeamVO;
import com.frame.service.TeamService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.CopyProperties;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;


@Service("teamService")
public class TeamServiceImpl extends BaseServiceImpl<Team, Long> implements TeamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);
	
	
	@Resource
	private TeamDao teamDao;
	
	@Resource
	private UserTeamRelationDao userTeamRelationDao;
	
	@Resource
	private UserService userService;
	
	@Value("${img.prefix}")
	private String IMAGEPREFIX;
	
	@Override
	public BaseDao<Team, Long> getDao() {
		// TODO Auto-generated method stub
		return teamDao;
	}


	@Override
	public List<Team> getAllTeams(Page<Team> page) {
		return null;
	}


	@Override
	public List<Team> getUserTeams(Long userId) {
		return teamDao.getUserTeams(userId);
	}


	@Override
	@Transactional
	public RemoteResult createTeam(Long userId, Team team) {
		RemoteResult res = null;
		teamDao.insertEntryCreateId(team);
		
		UserTeamRelation utRelation  =  new UserTeamRelation();
		utRelation.setTeamId(team.getId().longValue());
		utRelation.setTeamName(team.getName());
		utRelation.setUserId(userId);
		utRelation.setType(UserTeamRelation.TEAM_TYPE_CAPTURE);
		utRelation.setYn(YnEnum.Normal.getKey());
		int result = userTeamRelationDao.insertEntry(utRelation);
		if(result > 0){
			res = RemoteResult.success();
		}else{
			res = RemoteResult.failure("0001", "创建失败");
		}
		return res;
	}


	@Override
	public List<Team> searchTeamByName(String name, String cityCode) {
		if(StringUtils.isEmpty(name)){
			LOGGER.error("fetcb team paramter is err");
			return null;
		}
		return teamDao.searchTeamByName(name,cityCode);
	}


	@Override
	@Transactional
	public TeamVO getTeamById(Long id) {
		if(null == id){
			return null;
		}
		Team team = teamDao.selectEntry(id);
		if(team == null){
			LOGGER.info("没找到相关球队");
			return null;
		}
		TeamVO teamVO = new TeamVO();
		
		try {
			CopyProperties.copy(team, teamVO);
			teamVO.setId(team.getId());
			teamVO.setImgUrl(IMAGEPREFIX + teamVO.getImgUrl());
		} catch (Exception e) {
			LOGGER.info("getTeamById 属性拷贝异常" , e);
			return null;
		}
		
		UserTeamRelation query = new UserTeamRelation();
		query.setTeamId(id.longValue());
		query.setYn(YnEnum.Normal.getKey());
		List<UserTeamRelation> userTeamRelations = userTeamRelationDao.selectEntryList(query);
		if(CollectionUtils.isNotEmpty(userTeamRelations)){
			List<Long> userIds = Lists.transform(userTeamRelations,new Function<UserTeamRelation , Long>(){

				@Override
				public Long apply(UserTeamRelation input) {
					// TODO Auto-generated method stub
					return input.getUserId();
				}
				
			});
			List<User> users  = Lists.newArrayList();
			if(CollectionUtils.isNotEmpty(userIds)){
				for (Long key : userIds) {
					User user =  userService.selectEntry(key);
					if(null != user && !StringUtils.isEmpty(user.getAvatarUrl()) && !user.getAvatarUrl().startsWith("http")){
						user.setAvatarUrl(IMAGEPREFIX + user.getAvatarUrl());
					}
					users.add(user);
				}
				
			}
			teamVO.setUsers(users);
		}
		return teamVO;
	}
	

}
