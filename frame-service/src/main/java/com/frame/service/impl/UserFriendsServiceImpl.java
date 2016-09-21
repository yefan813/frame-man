package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.UserFriendsDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.User;
import com.frame.domain.UserFriends;
import com.frame.domain.UserTeamRelation;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.EasemobAPIService;
import com.frame.service.UserFriendsService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Lists;


@Service("userFriendsService")
public class UserFriendsServiceImpl extends BaseServiceImpl<UserFriends, Long> implements UserFriendsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserFriendsServiceImpl.class);
	
	@Resource
	private UserFriendsDao userFriendsDao;
	
	@Resource
	private EasemobAPIService easemobAPIService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private APNSService apnsService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;
	
	@Override
	public BaseDao<UserFriends, Long> getDao() {
		return userFriendsDao;
	}


	@Override
	@Transactional
	public RemoteResult applyFriend(UserFriends userFriends) {
		RemoteResult result = null;
		if(null == userFriends || userFriends.getFromUserId() == null || userFriends.getToUserId() == null){
			LOGGER.info("调用applyFriend 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return result;
		}
		long fromId = userFriends.getFromUserId();
		long toId = userFriends.getToUserId();
		
		userFriends.setFromUserId(Math.min(fromId, toId));
		userFriends.setToUserId(Math.max(fromId, toId));
		userFriends.setYn(YnEnum.Normal.getKey());
		List<UserFriends> fList = userFriendsDao.selectEntryList(userFriends);
		if(CollectionUtils.isNotEmpty(fList)){
			LOGGER.info("两人已经是好友");
			result = RemoteResult.failure("0001", "两人已经是好友");
			return result;
		}
		
		userFriends.setActionUserId(fromId);
		userFriends.setYn(YnEnum.Normal.getKey());
		userFriends.setStatus(UserFriends.STATUS_WAITING);
		
		int res = userFriendsDao.insertEntryCreateId(userFriends);
		if(res > 0){
			User user = userService.selectEntry(fromId);
			if(null == user){
				LOGGER.error("申请加入还有没查询相关用户");
				return result;
			}
			apnsService.senPushNotification(toId, "用户'" + user.getNickName() +"' 请求加您为好友，请及时回复!");
			
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(), BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}


	@Override
	public RemoteResult getFriendsList(Page<User> page, Long userId) {
		RemoteResult result = null;
		List<User> users = Lists.newArrayList();
		if(userId == null || userId < 0 ){
			LOGGER.error("调用getFriendsList 传入参数错误");
			return RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
		}
		
		UserFriends friends = new UserFriends();
		friends.setFromUserId(userId);
		friends.setStartIndex(page.getStartIndex());
		friends.setEndIndex(page.getEndIndex());
		friends.setYn(YnEnum.Normal.getKey());
		List<UserFriends> fList = userFriendsDao.selectFriendsList(friends);
		int totalCount = userFriendsDao.selectFriendsListCount(friends);
		page.setTotalCount(totalCount);
		if(CollectionUtils.isNotEmpty(fList)){
			for (UserFriends userFriends : fList) {
				Long friendId = null;
				if(userId.longValue() == userFriends.getFromUserId()){
					friendId = userFriends.getToUserId();
				}else{
					friendId = userFriends.getFromUserId();
				}
				User user = userService.selectEntry(friendId);
				users.add(user);
			}
		}
		for (User user : users) {
			user.setAvatarUrl(IMAGEPREFIX + user.getAvatarUrl());
		}
		page.setResult(users);
		result = RemoteResult.success(users);
		return result;
	}


	@Override
	public RemoteResult queryFriends(Page<User> page, final Long userId, String query) {
		RemoteResult result = null;
		if(userId == null || StringUtils.isEmpty(query)){
			return RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
		}
		List<Long> userIds = Lists.newArrayList();
		UserFriends friends = new UserFriends();
		friends.setFromUserId(userId);
		List<UserFriends> list = userFriendsDao.selectFriendsList(friends);
		
		if(CollectionUtils.isNotEmpty(list)){
			userIds = Lists.transform(list,new Function<UserFriends , Long>(){
				@Override
				public Long apply(UserFriends input) {
					Long friendId = null;
					if(userId.longValue() == input.getFromUserId()){
						friendId = input.getToUserId();
					}else{
						friendId = input.getFromUserId();
					}
					return friendId;
				}
				
			});
		}
		List<User> users = userService.queryFriendsByTelOrNickName(userIds, query);
		result = RemoteResult.success(users);
		return result;
	}


	@Override
	@Transactional
	public RemoteResult deleteFriends(UserFriends userFriends) {
		RemoteResult result = null;
		if(null == userFriends.getFromUserId() || null==userFriends.getToUserId()){
			LOGGER.error("0001","deleteFriends传入参数错误");
			return RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
		}
		
		long fromId = userFriends.getFromUserId();
		long toId = userFriends.getToUserId();
		userFriends.setFromUserId(Math.min(fromId, toId));
		userFriends.setToUserId(Math.max(fromId, toId));
		int res = userFriendsDao.deleteFriends(userFriends);
		if(res < 0){
			return RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(), BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		//TODO 调用环信接口
		User fromeUser = userService.selectEntry(userFriends.getFromUserId());
		User toUser = userService.selectEntry(userFriends.getToUserId());
		result = easemobAPIService.deleteFriendSingle(fromeUser.getTel(), toUser.getTel());
		return result;
	}

	@Override
	@Transactional
	public RemoteResult agreeApplyFriends(UserFriends userFriends) throws Exception{
		RemoteResult result = null;
		if(null == userFriends || userFriends.getFromUserId() == null || userFriends.getToUserId() == null){
			LOGGER.info("调用applyFriend 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return result;
		}
		long fromId = userFriends.getFromUserId();
		long toId = userFriends.getToUserId();
		userFriends.setActionUserId(fromId);
		userFriends.setFromUserId(Math.min(fromId, toId));
		userFriends.setToUserId(Math.max(fromId, toId));
		userFriends.setStatus(UserFriends.STATUS_ACCEPTED);
		int res = userFriendsDao.changeUserFriendStatus(userFriends);
		
		
		//TODO 调用环信接口
		User fromUser = userService.selectEntry(fromId);
		User toUser = userService.selectEntry(toId);
		
		result = easemobAPIService.addFriendSingle(fromUser.getTel(), toUser.getTel());
		if(!"0000".equals(result.getCode())){
			throw new Exception();
		}
		if(res > 0 && "0000".equals(result.getCode())){
			
			User froUs = userService.selectEntry(fromId);
			if(null == froUs){
				LOGGER.error("申请加入好友查询相关用户");
				return result;
			}
			apnsService.senPushNotification(toId, "用户'" + froUs.getNickName() +"' 同意了你的好友请求,约起来吧~");
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(), BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}


	@Override
	public RemoteResult refuseInvitation(UserFriends userFriends) {
		RemoteResult result = null;
		
		if(null == userFriends || userFriends.getFromUserId() == null || userFriends.getToUserId() == null){
			LOGGER.info("调用applyFriend 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return result;
		}
		long fromId = userFriends.getFromUserId();
		long toId = userFriends.getToUserId();
		userFriends.setFromUserId(Math.min(fromId, toId));
		userFriends.setToUserId(Math.max(fromId, toId));
		userFriends.setStatus(UserFriends.STATUS_REJECT);
		int res = userFriendsDao.changeUserFriendStatus(userFriends);
		
		if(res > 0){
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(), BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}


	@Override
	public RemoteResult getPendingFriendList(Page<User> page, Long userId) {
		RemoteResult result = null;
		List<User> users = Lists.newArrayList();
		if(userId == null || userId < 0){
			LOGGER.info("调用getApplyFriendList 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
		}
		UserFriends friends = new UserFriends();
		friends.setFromUserId(userId);
		friends.setStatus(UserFriends.STATUS_WAITING);
		friends.setStartIndex(page.getStartIndex());
		friends.setEndIndex(page.getEndIndex());
		friends.setYn(YnEnum.Normal.getKey());
		List<UserFriends> fList = userFriendsDao.selectPendingFriendsList(friends);
		int totalCount = userFriendsDao.selectPendingFriendsListCount(friends);
		page.setTotalCount(totalCount);
		if(CollectionUtils.isNotEmpty(fList)){
			for (UserFriends userFriends : fList) {
				Long friendId = null;
				if(userId == userFriends.getFromUserId().longValue()){
					friendId = userFriends.getToUserId();
				}else{
					friendId = userFriends.getFromUserId();
				}
				User user = userService.selectEntry(friendId);
				if(null!= user){
					users.add(user);
				}
			}
		}
		for (User user : users) {
			user.setAvatarUrl(IMAGEPREFIX + user.getAvatarUrl());
		}
		page.setResult(users);
		result = RemoteResult.success(users);
		return result;
	}


	@Override
	public int check2PIsFriend(UserFriends userFriends) {
		int res = 0;
		if(null == userFriends || userFriends.getFromUserId() == null || userFriends.getToUserId() == null){
			LOGGER.info("调用check2PIsFriend 传入的参数错误");
			return res;
		}
		long fromId = userFriends.getFromUserId();
		long toId = userFriends.getToUserId();
		userFriends.setFromUserId(Math.min(fromId, toId));
		userFriends.setToUserId(Math.max(fromId, toId));
		
		res = userFriendsDao.selct2PIsFriend(userFriends);
		
		return res;
	}


}
