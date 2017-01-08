package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.frame.dao.MatchApplyDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.MatchApply;
import com.frame.domain.Team;
import com.frame.domain.User;
import com.frame.domain.UserLogin;
import com.frame.domain.UserTeamRelation;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.MatchApplyVO;
import com.frame.service.MatchApplyService;
import com.frame.service.TeamService;
import com.frame.service.UserLoginService;
import com.frame.service.UserService;
import com.frame.service.UserTeamRelationService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.CopyProperties;
import com.google.common.base.Function;
import com.google.common.collect.Lists;



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
	
	@Value("${img.prefix}")
	private String IMAGEPREFIX;

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
				content = "' " + souTL.getName() +" ' 向' " + tarTL.getName() + " ' 申请比赛，请及时回复";
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
	public List<MatchApplyVO> queryPersionMatchApply(Integer userId) {
		List<MatchApplyVO> res = null;
		if(null == userId){
			LOGGER.error("调用 applMatchService 接口 mineApplyMatch 传入参数错误");
			return null;
		}
		
		res = Lists.newArrayList();
		
		MatchApply matchQuery = new MatchApply();
		matchQuery.setSourceIdentityId(userId);
		matchQuery.setParentApplyId(MatchApply.DEFAULT_APPLYER_IDENTITY);
		matchQuery.setType(MatchApply.TYPE_PERSONLY);
		matchQuery.setYn(YnEnum.Normal.getKey());
		
		List<MatchApply> list = matchApplyDao.selectEntryList(matchQuery);
		if(CollectionUtils.isNotEmpty(list)){
			for (MatchApply apply : list) {
				MatchApplyVO vo = getMatchApplyById(apply);
				if(null != vo){
					res.add(vo);
				}
			}
		}
		return res;
	}


	@Override
	public List<MatchApplyVO> queryMineTeamApplyMatch(Integer userId) {
		List<MatchApplyVO> res = null;
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
				matchQuery.setOrderField("created");
				matchQuery.setOrderFieldType("DESC");
				List<MatchApply> matchs = matchApplyDao.selectEntryList(matchQuery);
				
				List<MatchApplyVO>  convertRes = convert2TeamApplyRecordVO(matchs);
				if(CollectionUtils.isNotEmpty(convertRes)){
					res.addAll(convertRes);
				}
			}
		}
		return res;
	}

	
	private List<MatchApplyVO> convert2TeamApplyRecordVO(List<MatchApply> matchs){
		List<MatchApplyVO> res = null;
		if(CollectionUtils.isEmpty(matchs)){
			return null;
		}
		res = Lists.newArrayList();
		for (MatchApply match : matchs) {
			MatchApplyVO vo = getMatchApplyById(match);
			if(null != vo){
				res.add(vo);
			}
		}
		return res;
	}

	@Override
	public List<MatchApplyVO> queryMineTeamInventMatch(Integer userId) {
		List<MatchApplyVO> res = null;
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
				matchQuery.setOrderField("created");
				matchQuery.setOrderFieldType("DESC");
				List<MatchApply> matchs = matchApplyDao.selectEntryList(matchQuery);
				
				List<MatchApplyVO>  convertRes = convert2TeamApplyRecordVO(matchs);
				if(CollectionUtils.isNotEmpty(convertRes)){
					res.addAll(convertRes);
				}
			}
		}
		return res;
	}


	@Override
	public Page<MatchApplyVO> getPerionApplyByLocation(Page<MatchApply> page,Double lng, Double lat) {
		Page<MatchApplyVO> newPage = new Page<MatchApplyVO>();
		newPage.setPageSize(page.getStartIndex());
		newPage.setCurrentPage(page.getCurrentPage());
		
		List<MatchApplyVO> matchApplyVOs = Lists.newArrayList();
		
		MatchApply query = new MatchApply();
		query.setLongitude(lng);
		query.setLatitude(lat);
		query.setParentApplyId(MatchApply.DEFAULT_APPLYER_IDENTITY);
		query.setType(MatchApply.TYPE_PERSONLY);
		query.setStartIndex(page.getStartIndex());
		query.setEndIndex(page.getEndIndex());
		query.setOrderField("myDistance,match_time");
		query.setOrderFieldType("asc");
		query.setYn(YnEnum.Normal.getKey());
		
		List<MatchApply> data = matchApplyDao.getPerionApplyByLocation(query);
		if(CollectionUtils.isNotEmpty(data)){
			for (MatchApply matchApply : data) {
				MatchApplyVO vo = getMatchApplyById(matchApply);
				matchApplyVOs.add(vo);
			}
			newPage.setResult(matchApplyVOs);
		}
		
		MatchApply countQuery = new MatchApply();
		countQuery.setParentApplyId(MatchApply.DEFAULT_APPLYER_IDENTITY);
		countQuery.setYn(YnEnum.Normal.getKey());
		int totalCount = matchApplyDao.selectEntryListCount(countQuery);
		
		newPage.setTotalCount(totalCount);
		
		return newPage;
	}


	@Override
	public MatchApplyVO getMatchApplyById(MatchApply matchApply) {
		if(null == matchApply){
			return null;
		}
		MatchApplyVO res = null;
		try {
			res = new MatchApplyVO();
			CopyProperties.copy(matchApply, res);
			res.setId(matchApply.getId());
			if(matchApply.getType() == MatchApply.TYPE_PERSONLY){
				Integer userId = matchApply.getSourceIdentityId();
				User source = userService.selectEntry(userId.longValue());
				
				if(null!= source && null != source.getAvatarUrl()){
					source.setAvatarUrl(IMAGEPREFIX + source.getAvatarUrl());
				}
				res.setSourceObject(source);
				
				
				MatchApply joinQuery = new MatchApply();
				joinQuery.setSourceIdentityId(userId);
				joinQuery.setParentApplyId(matchApply.getId());
				joinQuery.setYn(YnEnum.Normal.getKey());
				List<User> userList = userService.getUserJoinPersionApplyRecord(joinQuery);
				if(CollectionUtils.isNotEmpty(userList)){
					for(User user : userList){
						if(null != user.getAvatarUrl()){
							user.setAvatarUrl(IMAGEPREFIX + user.getAvatarUrl());
						}
						
					}
					res.setTargetObject(userList);
				}
			}else if(matchApply.getType() == MatchApply.TYPE_TEAM){
				Team sourceTeam = teamService.selectEntry(matchApply.getSourceIdentityId().longValue());
				if(null != sourceTeam.getImgUrl()){
					sourceTeam.setImgUrl(IMAGEPREFIX + sourceTeam.getImgUrl());
				}
				
				
				Team targetTeam = teamService.selectEntry(matchApply.getTargetIdentityId().longValue());
				if(null != targetTeam.getImgUrl()){
					targetTeam.setImgUrl(IMAGEPREFIX + targetTeam.getImgUrl());
				}
				
				res.setSourceObject(sourceTeam);
				res.setTargetObject(targetTeam);
			}
		}catch (Exception e) {
			LOGGER.error("调用CopyProperties.copy 异常",e);
			return res;
		}
		return res;
	}


	@Override
	@Transactional
	public RemoteResult joinPersionMatchApply(Integer persionApplyId, Integer userId) {
		RemoteResult result = null;
		MatchApply originalApply = selectEntry(persionApplyId.longValue());
		if (null == originalApply) {
			result = RemoteResult.failure("0001", "没找到此个人约球记录");
		}
		MatchApply updateApply = new MatchApply();
		updateApply.setId(persionApplyId);
		updateApply.setStatus(MatchApply.STATUS_PERSION_GOING);
		update(updateApply);
		
		MatchApply matchApply = new MatchApply();
		matchApply.setSourceIdentityId(originalApply.getSourceIdentityId());
		matchApply.setTargetIdentityId(userId);
		matchApply.setParentApplyId(persionApplyId);
		matchApply.setMatchAddress(originalApply.getMatchAddress());
		matchApply.setMatchTime(originalApply.getMatchTime());
		matchApply.setLatitude(originalApply.getLatitude());
		matchApply.setLongitude(originalApply.getLongitude());
		matchApply.setType(MatchApply.TYPE_PERSONLY);
		matchApply.setStatus(MatchApply.STATUS_PERSION_GOING);
		matchApply.setYn(YnEnum.Normal.getKey());
		if (insertEntry(matchApply) > 0) {
			LOGGER.info("调用joinPersionMatchApply成功");
			result = RemoteResult.success();
		} else {
			LOGGER.info("调用mineApplyMatch ,无数据");
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
		}
		return result;
	}


	@Override
	public List<MatchApply> selectPersionOutDateApply() {
		return matchApplyDao.selectPersionOutDateApply();
	}
	
	

}
