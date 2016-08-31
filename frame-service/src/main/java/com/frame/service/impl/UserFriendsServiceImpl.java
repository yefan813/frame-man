package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.frame.dao.UserFriendsDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.User;
import com.frame.domain.UserFriends;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.UserFriendsService;
import com.frame.service.base.BaseServiceImpl;


@Service("userFriendsService")
public class UserFriendsServiceImpl extends BaseServiceImpl<UserFriends, Long> implements UserFriendsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserFriendsServiceImpl.class);
	
	@Resource
	private UserFriendsDao userFriendsDao;
	

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
		userFriends.setStatus(UserFriends.STATUS_WAITING);
		
		int res = userFriendsDao.insertEntryCreateId(userFriends);
		
		if(res > 0){
			//TODO 调用环信接口
			
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(), BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}


	@Override
	public List<User> getFriendsList(Long userId) {
		//TODO 调用环信接口
		
		
		return null;
	}


	@Override
	public List<User> queryFriends(String query) {
		
		//TODO 调用环信接口
		return null;
	}


	@Override
	public int deleteFriends(Long userId) {
		
		//TODO 调用环信接口
		return 0;
	}


	@Override
	public int agreeApplyFriends(Long userId) {
		
		//TODO 调用环信接口
		return 0;
	}


	@Override
	public int refuseInvitation(Long userId) {
		
		//TODO 调用环信接口
		return 0;
	}


}
