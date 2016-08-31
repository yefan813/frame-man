package com.frame.service;

import java.util.List;

import com.frame.domain.User;
import com.frame.domain.UserFriends;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;

public interface UserFriendsService extends BaseService<UserFriends, Long> {
	
	public RemoteResult applyFriend(UserFriends userFriends);
	
	public List<User> getFriendsList(Long userId);
	
	public List<User> queryFriends(String query);
	
	public int deleteFriends(Long userId);
	
	public int agreeApplyFriends(Long userId);
	
	public int refuseInvitation(Long userId);
}
