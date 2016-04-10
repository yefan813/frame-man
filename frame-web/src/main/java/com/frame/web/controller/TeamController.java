package com.frame.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Team;
import com.frame.domain.UserTeamRelation;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.TeamService;
import com.frame.service.UserTeamRelationService;


@Controller
@RequestMapping(value = "/team")
public class TeamController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);
	
	@Resource
	private TeamService teamService;
	
	@Resource
	private UserTeamRelationService userTeamRelationService;
	
	@RequestMapping(value = "/getAllTeams", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getAllTeams(Page<Team> page){
		RemoteResult result = null;
		try{
			Team query = new Team();
			query.setYn(YnEnum.Normal.getKey());
			Page<Team> teams = teamService.selectPage(query, page);
			result = RemoteResult.result(BusinessCode.SUCCESS, teams.getResult());
		}catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		} 
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/getUserTeams/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getUserTeams(@PathVariable Integer userId){
		
		RemoteResult result = null;
		if(null == userId || userId  < 0 ){
			LOGGER.error("调用getUserTeams 传入参数为：" + userId);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
			return JSON.toJSONString(result);
		}
		try{
			UserTeamRelation query = new UserTeamRelation();
			query.setYn(YnEnum.Normal.getKey());
			query.setUserId(userId);
			List<UserTeamRelation> teams = userTeamRelationService.selectEntryList(query);
			result = RemoteResult.result(BusinessCode.SUCCESS, teams);
		}catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		} 
		
		return JSON.toJSONString(result);
	}
	
	
}
