package com.frame.service.impl;

import java.lang.reflect.InvocationTargetException;
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
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.TeamApplyRecordVO;
import com.frame.domain.vo.UserApplyRecordVO;
import com.frame.service.MatchApplyService;
import com.frame.service.TeamService;
import com.frame.service.UserLoginService;
import com.frame.service.UserService;
import com.frame.service.UserTeamRelationService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.CopyProperties;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.taobao.api.internal.toplink.channel.embedded.EmbeddedWebSocketClient;



@Service("matchApplyService")
public class MatchApplyServiceImpl extends BaseServiceImpl<MatchApply, Long> implements MatchApplyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchApplyServiceImpl.class);
	
	
	@Resource
	private MatchApplyDao matchApplyDao;
	
	
	@Resource
	private TeamService teamService;
	
	
	@Resource
	private UserLoginService userLoginService;
	
	@Resource
	private APNSService apnsService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private UserTeamRelationService userTeamRelationService;

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
				Team souTL = teamService.selectEntry(matchApply.getSourceIdentityId().longValue());
				Team tarTL = teamService.selectEntry(matchApply.getTargetIdentityId().longValue());
				
				
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


	@Override
	public List<UserApplyRecordVO> queryPersionMatchApply(Integer userId) {
		List<UserApplyRecordVO> res = null;
		if(null == userId){
			LOGGER.error("调用 applMatchService 接口 mineApplyMatch 传入参数错误");
			return null;
		}
		
		res = Lists.newArrayList();
		
		MatchApply matchQuery = new MatchApply();
		matchQuery.setSourceIdentityId(userId);
		matchQuery.setType(MatchApply.TYPE_PERSONLY);
		matchQuery.setYn(YnEnum.Normal.getKey());
		
		List<MatchApply> list = matchApplyDao.selectEntryList(matchQuery);
		if(CollectionUtils.isNotEmpty(list)){
			for (MatchApply apply : list) {
				UserApplyRecordVO recordVO = new UserApplyRecordVO();
				try {
					CopyProperties.copy(apply, recordVO);
					//TODO 获取用户参加列表
					
					recordVO.setId(apply.getId());
					res.add(recordVO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}


	@Override
	public List<TeamApplyRecordVO> queryMineTeamApplyMatch(Integer userId) {
		List<TeamApplyRecordVO> res = null;
		if(null == userId || userId < 0){
			LOGGER.error("调用 queryMineTeamApplyMatch 接口 mineApplyMatch 传入参数错误");
			return null;
		}
		res = Lists.newArrayList();
		UserTeamRelation query = new UserTeamRelation();
		query.setUserId(userId.longValue());
		query.setYn(YnEnum.Normal.getKey());
		List<UserTeamRelation> relations = userTeamRelationService.selectEntryList(query);
		
		if(CollectionUtils.isNotEmpty(relations)){
			MatchApply matchQuery;
			for (UserTeamRelation userTeamRelation : relations) {
				matchQuery = new MatchApply();
				matchQuery.setSourceIdentityId(userTeamRelation.getTeamId().intValue());
				matchQuery.setType(MatchApply.TYPE_TEAM);
				matchQuery.setYn(YnEnum.Normal.getKey());
				List<MatchApply> matchs = matchApplyDao.selectEntryList(matchQuery);
				
				List<TeamApplyRecordVO>  convertRes = convert2TeamApplyRecordVO(matchs);
				if(CollectionUtils.isNotEmpty(convertRes)){
					res.addAll(convertRes);
				}
			}
		}
		return res;
	}

	
	private List<TeamApplyRecordVO> convert2TeamApplyRecordVO(List<MatchApply> matchs){
		List<TeamApplyRecordVO> res = null;
		if(CollectionUtils.isEmpty(matchs)){
			return null;
		}
		res = Lists.newArrayList();
		for (MatchApply match : matchs) {
			TeamApplyRecordVO vo = new TeamApplyRecordVO();
			try {
				CopyProperties.copy(match, vo);
				Team sourceTeam = teamService.selectEntry(match.getSourceIdentityId().longValue());
				Team targetTeam = teamService.selectEntry(match.getTargetIdentityId().longValue());
				vo.setId(match.getId());
				vo.setSourceIdentity(sourceTeam);
				vo.setTargetIdentity(targetTeam);
				res.add(vo);
			} catch (Exception e) {
				LOGGER.error("调用convert2TeamApplyRecordVO 异常",e);
			}
		}
		
		
		
		return res;
	}

	@Override
	public List<TeamApplyRecordVO> queryMineTeamInventMatch(Integer userId) {
		List<TeamApplyRecordVO> res = null;
		if(null == userId || userId < 0){
			LOGGER.error("调用 queryMineTeamApplyMatch 接口 mineApplyMatch 传入参数错误");
			return null;
		}
		res = Lists.newArrayList();
		UserTeamRelation query = new UserTeamRelation();
		query.setUserId(userId.longValue());
		query.setYn(YnEnum.Normal.getKey());
		List<UserTeamRelation> relations = userTeamRelationService.selectEntryList(query);
		
		if(CollectionUtils.isNotEmpty(relations)){
			MatchApply matchQuery;
			for (UserTeamRelation userTeamRelation : relations) {
				matchQuery = new MatchApply();
				matchQuery.setTargetIdentityId(userTeamRelation.getTeamId().intValue());
				matchQuery.setType(MatchApply.TYPE_TEAM);
				matchQuery.setYn(YnEnum.Normal.getKey());
				List<MatchApply> matchs = matchApplyDao.selectEntryList(matchQuery);
				
				List<TeamApplyRecordVO>  convertRes = convert2TeamApplyRecordVO(matchs);
				if(CollectionUtils.isNotEmpty(convertRes)){
					res.addAll(convertRes);
				}
			}
		}
		return res;
	}

}
