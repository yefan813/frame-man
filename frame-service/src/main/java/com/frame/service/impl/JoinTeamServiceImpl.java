package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.JoinTeamDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.JoinTeam;
import com.frame.domain.Team;
import com.frame.domain.User;
import com.frame.domain.UserTeamRelation;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.JoinTeamVO;
import com.frame.service.JoinTeamService;
import com.frame.service.TeamService;
import com.frame.service.UserService;
import com.frame.service.UserTeamRelationService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.CopyProperties;
import com.google.common.base.Function;
import com.google.common.collect.Lists;


@Service("joinTeamService")
public class JoinTeamServiceImpl extends BaseServiceImpl<JoinTeam, Long> implements JoinTeamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(JoinTeamServiceImpl.class);
	
	@Resource
	private JoinTeamDao joinTeamDao;
	
	@Resource
	private APNSService apnsService;
	
	@Resource
	private UserTeamRelationService userTeamRelationService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private TeamService teamService;
	
	@Override
	public BaseDao<JoinTeam, Long> getDao() {
		return joinTeamDao;
	}

	@Override
	public Page<JoinTeamVO> getJoinTeamVO(Page<JoinTeam> page, JoinTeam joinTeam) {
		
		Page<JoinTeamVO> pageVo = new Page<JoinTeamVO>();
		pageVo.setCurrentPage(page.getCurrentPage());
		
		List<JoinTeamVO> voList = Lists.newArrayList();
		
		Page<JoinTeam> pages = selectPage(joinTeam, page);
		
		List<JoinTeam> joinTeams = pages.getResult();
		if(CollectionUtils.isNotEmpty(joinTeams)){
			for (JoinTeam join : joinTeams) {
				JoinTeamVO vo = getJoinTeamVO(join);
				if(vo != null){
					voList.add(vo);
				}
			}
			pageVo.setTotalCount(pages.getTotalCount());
			pageVo.setResult(voList);
		}
		return pageVo;
	}

	@Override
	public JoinTeamVO getJoinTeamVO(JoinTeam joinTeam) {
		if(null == joinTeam){
			return null;
		}
		JoinTeamVO vo = new JoinTeamVO();
		try {
			CopyProperties.copy(joinTeam, vo);
		} catch (Exception e) {
			LOGGER.error("调用geJoinTeamVO 转换属性异常", e);
		}
		if(joinTeam.getType() != null &&  joinTeam.getType() == JoinTeam.TYPE_INVENT){
			if(joinTeam.getInitiator() != null){
				User initator = userService.selectEntry(joinTeam.getInitiator());
				vo.setInitiatorUser(initator);
			}
		}
		if(joinTeam.getTeamId() != null){
			Team  team = teamService.selectEntry(joinTeam.getTeamId());
			vo.setTargetTeam(team);
		}
		if(joinTeam.getUserId() != null){
			User targetUser = userService.selectEntry(joinTeam.getUserId());
			vo.setTargetUser(targetUser);
		}
		vo.setId(joinTeam.getId());
		return vo;
	}

	@Override
	public RemoteResult applyJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		joinTeam.setType(JoinTeam.TYPE_APPLY);
		joinTeam.setStatus(JoinTeam.STATUS_APPLYING_JOIN);
		joinTeam.setYn(YnEnum.Normal.getKey());
		if(joinTeamDao.insertEntry(joinTeam) > 0){
			LOGGER.info("调用applyJoinTeam 上传数据成功");
			result = RemoteResult.success();
			
			User applyor = userService.selectEntry(joinTeam.getUserId());
			if(null == applyor){
				result = RemoteResult.failure("0001", "没找到申请用户相关信息");
				return result;
			}
			
			Team team = teamService.selectEntry(joinTeam.getTeamId());
			if(null == team){
				result = RemoteResult.failure("0001", "没找到申请球队相关信息");
				return result;
			}
			
			List<User> users =  userService.getTeamUserByTeamId(joinTeam.getTeamId());
			if(CollectionUtils.isNotEmpty(users)){
				List<Long> userIds = Lists.transform(users,new Function<User, Long>(){

					@Override
					public Long apply(User input) {
						// TODO Auto-generated method stub
						return input.getId().longValue();
					}
					
				});
				apnsService.senPushNotificationByIds(userIds, "用户:" + applyor.getNickName() + ",申请加入球队:" + team.getName() + ",请及时回复");
			}
		}else{
			LOGGER.info("调用applyJoinTeam 上床数据失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		
		return result;
	}

	@Override
	@Transactional
	public RemoteResult agreeApplyJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		joinTeam.setStatus(JoinTeam.STATUS_AGREEMENT);
		if(joinTeamDao.updateByKey(joinTeam) > 0){
			LOGGER.info("调用agreeApplyJoinTeam 上传数据成功");
			result = RemoteResult.success();
			
			JoinTeam thisJoinTeam = joinTeamDao.selectEntry(joinTeam.getId().longValue());
			if(null == thisJoinTeam){
				result = RemoteResult.failure("0001", "没找到申请球队相关信息");
				return result;
			}
			
			UserTeamRelation userTeamRelation = new UserTeamRelation();
			userTeamRelation.setTeamId(thisJoinTeam.getTeamId());
			userTeamRelation.setUserId(thisJoinTeam.getUserId());
			userTeamRelation.setType(UserTeamRelation.TEAM_TYPE_MEMBER);
			userTeamRelation.setYn(YnEnum.Normal.getKey());
			if(userTeamRelationService.insertEntry(userTeamRelation) > 0){
				result = RemoteResult.success();
			}else{
				result = RemoteResult.failure("0001", "服务器内部异常，同意加入球队失败");
			}
			
			User applyor = userService.selectEntry(thisJoinTeam.getUserId());
			if(null == applyor){
				LOGGER.info("没找到申请用户相关信息");
				return result;
			}
			
			Team team = teamService.selectEntry(thisJoinTeam.getTeamId());
			if(null == team){
				LOGGER.info("没找到申请球队相关信息");
				return result;
			}
			apnsService.senPushNotification(applyor.getId().longValue(), "球队：" + team.getName() + " 队长同意了你的加入球队申请,开始的你的球队之旅吧！");
			
			
		}else{
			LOGGER.info("调用agreeApplyJoinTeam 上传数据失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		
		return result;
	}

	@Override
	public RemoteResult rejectApplyJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		joinTeam.setStatus(JoinTeam.STATUS_REJECT);
		if(joinTeamDao.updateByKey(joinTeam) > 0){
			LOGGER.info("调用rejectApplyJoinTeam 上传数据成功");
			result = RemoteResult.success();
			
			JoinTeam thisJoinTeam = joinTeamDao.selectEntry(joinTeam.getId().longValue());
			if(null == thisJoinTeam){
				LOGGER.info("没找到申请球队相关信息");
				return result;
			}
			
			User applyor = userService.selectEntry(thisJoinTeam.getUserId());
			if(null == applyor){
				LOGGER.info("没找到申请用户相关信息");
				return result;
			}
			
			Team team = teamService.selectEntry(thisJoinTeam.getTeamId());
			if(null == team){
				LOGGER.info("没找到申请球队相关信息");
				return result;
			}
			apnsService.senPushNotification(applyor.getId().longValue(), "球队：" + team.getName() + " 队长拒绝了你的加入球队申请,太没有眼光了吧！");
		}else{
			LOGGER.info("调用rejectApplyJoinTeam 上传数据失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}

	@Override
	public RemoteResult inventJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		joinTeam.setType(JoinTeam.TYPE_INVENT);
		joinTeam.setStatus(JoinTeam.STATUS_INVENT_JOIN);
		joinTeam.setYn(YnEnum.Normal.getKey());
		if(joinTeamDao.insertEntry(joinTeam) > 0){
			LOGGER.info("调用inventJoinTeam 上传数据成功");
			result = RemoteResult.success();
			
			
			User initator = userService.selectEntry(joinTeam.getInitiator());
			if(null == initator){
				LOGGER.info("没找到发起用户相关信息");
				return result;
			}
			
			Team targetTeam = teamService.selectEntry(joinTeam.getTeamId());
			if(null == targetTeam){
				LOGGER.info("没找到目标球队相关信息");
				return result;
			}
			apnsService.senPushNotification(joinTeam.getUserId().longValue(), "球队:" + targetTeam.getName() + " 队员:" + initator.getNickName() + " 发现你太强大了，邀请你加入球队,不要辜负他们哦...");
		}else{
			LOGGER.info("调用inventJoinTeam 上床数据失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}

	@Override
	@Transactional
	public RemoteResult agreeInventJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || null == joinTeam.getTeamId() || 
				joinTeam.getUserId() == null || joinTeam.getInitiator() == null){
			LOGGER.info("调用agreeInventJoinTeam 传入参数错误");
			result = RemoteResult.failure("0001", "调用agreeInventJoinTeam 传入参数错误");
			return result;
		}
		
		User targetUser = userService.selectEntry(joinTeam.getUserId());
		if(null == targetUser){
			LOGGER.info("没找到目标用户相关信息");
			return result;
		}
		
		User initor = userService.selectEntry(joinTeam.getInitiator());
		if(null == initor){
			LOGGER.info("没找到发起人用户相关信息");
			return result;
		}
		
		Team targetTeam = teamService.selectEntry(joinTeam.getTeamId());
		if(null == targetTeam){
			LOGGER.info("没找到申请球队相关信息");
			return result;
		}
		
		
		joinTeam.setStatus(JoinTeam.STATUS_AGREEMENT);
		if(joinTeamDao.updateByKey(joinTeam) > 0){
			LOGGER.info("调用agreeApplyJoinTeam 上传数据成功");
			result = RemoteResult.success();
			
			UserTeamRelation userTeamRelation = new UserTeamRelation();
			userTeamRelation.setTeamId(joinTeam.getTeamId());
			userTeamRelation.setUserId(joinTeam.getUserId());
			userTeamRelation.setType(UserTeamRelation.TEAM_TYPE_MEMBER);
			userTeamRelation.setYn(YnEnum.Normal.getKey());
			if(userTeamRelationService.insertEntry(userTeamRelation) > 0){
				result = RemoteResult.success();
			}else{
				result = RemoteResult.failure("0001", "服务器内部异常，同意加入球队失败");
			}
			
			apnsService.senPushNotification(initor.getId().longValue(), "用户:" + targetUser.getNickName() + ",同意加入球队:" + targetTeam.getName() + ",开始约球吧！");
			
		}else{
			LOGGER.info("调用agreeApplyJoinTeam 上传数据失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}

	@Override
	public RemoteResult rejectInventJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		joinTeam.setStatus(JoinTeam.STATUS_REJECT);
		if(joinTeamDao.updateByKey(joinTeam) > 0){
			LOGGER.info("调用rejectInventJoinTeam 上传数据成功");
			result = RemoteResult.success();
			
			JoinTeam thisJoinTeam = joinTeamDao.selectEntry(joinTeam.getId().longValue());
			if(null == thisJoinTeam){
				LOGGER.info("没找到申请球队相关信息");
				return result;
			}
			
			User targetUser = userService.selectEntry(thisJoinTeam.getInitiator());
			if(null == targetUser){
				LOGGER.info("没找到发起人用户相关信息");
				return result;
			}
			
			User initor = userService.selectEntry(thisJoinTeam.getInitiator());
			if(null == initor){
				LOGGER.info("没找到发起人用户相关信息");
				return result;
			}
			Team targetTeam = teamService.selectEntry(thisJoinTeam.getTeamId());
			if(null == targetTeam){
				result = RemoteResult.failure("0001", "没找到申请球队相关信息");
				return result;
			}
			
			apnsService.senPushNotification(initor.getId().longValue(), "用户:" + targetUser.getNickName() + ",拒绝加入球队:" + targetTeam.getName() + ",他咋这么骄傲呢，怎么不上天啊！");
		}else{
			LOGGER.info("调用rejectInventJoinTeam 上传数据失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}
	
	

}
