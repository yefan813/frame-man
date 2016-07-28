package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.MatchApplyDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.MatchApply;
import com.frame.domain.Team;
import com.frame.domain.UserLogin;
import com.frame.domain.UserTeamRelation;
import com.frame.domain.common.RemoteResult;
import com.frame.service.MatchApplyService;
import com.frame.service.TeamService;
import com.frame.service.UserLoginService;
import com.frame.service.base.BaseServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;



@Service("matchApplyService")
public class MatchApplyServiceImpl extends BaseServiceImpl<MatchApply, Long> implements MatchApplyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchApplyServiceImpl.class);
	
	
	@Resource
	private MatchApplyDao matchApplyDao;
	
	
	@Resource
	private TeamService TeamService;
	
	
	@Resource
	private UserLoginService userLoginService;
	
	@Resource
	private APNSService apnsService;

	@Override
	public BaseDao<MatchApply, Long> getDao() {
		// TODO Auto-generated method stub
		return matchApplyDao;
	}


	@Override
	
	public RemoteResult applyMatch(MatchApply matchApply) {
		// TODO Auto-generated method stub
		RemoteResult result = null;
		if(null == matchApply){
			result = RemoteResult.failure("0001", "参数错误");
			return result;
		}
		int res = matchApplyDao.insertEntry(matchApply);
		if(res > 0){
			//球队月球 需要推送双方球队，所有成员
			if(matchApply.getType() == MatchApply.TYPE_TEAM){
				Team souTL = TeamService.selectEntry(matchApply.getSourceIdentityId().longValue());
				Team tarTL = TeamService.selectEntry(matchApply.getTargetIdentityId().longValue());
				
				
				List<UserLogin> sourceTeam = userLoginService.queryUserDeviceTokenByTeamId(matchApply.getSourceIdentityId());
				if(CollectionUtils.isEmpty(sourceTeam)){
					LOGGER.info("球队无队员");
					return RemoteResult.success();
				}
				List<String> soDeviceTokens = Lists.transform(sourceTeam,new Function<UserLogin , String>(){

					@Override
					public String apply(UserLogin input) {
						// TODO Auto-generated method stub
						return input.getDeviceToken();
					}
					
				});
				String content = "' " + souTL.getName() +" ' 队长已向' " + tarTL.getName() + " ' 申请比赛，等待对方回复";
				apnsService.senPushNotification(soDeviceTokens, content);
				
				
				
				List<UserLogin> targetTeam = userLoginService.queryUserDeviceTokenByTeamId(matchApply.getTargetIdentityId());
				if(CollectionUtils.isEmpty(targetTeam)){
					LOGGER.info("球队无队员");
					return RemoteResult.success();
				}
				List<String> tarDeviceTokens = Lists.transform(targetTeam,new Function<UserLogin , String>(){

					@Override
					public String apply(UserLogin input) {
						// TODO Auto-generated method stub
						return input.getDeviceToken();
					}
					
				});
				content = "' " + tarTL.getName() +" ' 向' " + souTL.getName() + " ' 申请比赛，请及时回复";
				apnsService.senPushNotification(tarDeviceTokens, content);
				
				result = RemoteResult.success();
			}else{
				return RemoteResult.success();
			}
			
			
			//个人约球 不需要推送
		}
		
		return result;
	}

}
