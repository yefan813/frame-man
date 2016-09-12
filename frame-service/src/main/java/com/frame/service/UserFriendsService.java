package com.frame.service;

import com.frame.domain.User;
import com.frame.domain.UserFriends;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;

public interface UserFriendsService extends BaseService<UserFriends, Long> {
	
	public RemoteResult applyFriend(UserFriends userFriends);
	
	public RemoteResult getPendingFriendList(Page<User> page, Long userId);
	
	public RemoteResult getFriendsList(Page<User> page, Long userId);
	
	public RemoteResult queryFriends(Page<User> page, Long userId, String query);
	
	public RemoteResult deleteFriends(UserFriends userFriends);
	
	public RemoteResult agreeApplyFriends(UserFriends userFriends) throws Exception;
	
	public RemoteResult refuseInvitation(UserFriends userFriends);
	
	public int check2PIsFriend(UserFriends userFriends);
}
