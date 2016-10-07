package com.frame.dao;

import java.util.List;

import com.frame.dao.base.BaseDao;
import com.frame.domain.UserFriends;

public interface UserFriendsDao extends BaseDao<UserFriends, Long> {
	
	/**
	 * 变好有关系 
	 * @param userFriends 穿入 from to action user
	 * @return
	 */
	public int changeUserFriendStatus(UserFriends userFriends);
	
	
	
	/**
	 * 得到某个用户的好友列表 
	 * @param userFriends 必须传入fromUserId
	 * @return
	 */
	public List<UserFriends> selectFriendsList(UserFriends userFriends);
	
	public int selectFriendsListCount(UserFriends userFriends);
	
	
	
	/**
	 * 查看某个用户被邀请的记录 
	 * @param userFriends 必须传入fromUserId
	 * @return
	 */
	public List<UserFriends> selectPendingFriendsList(UserFriends userFriends);
	
	public int selectPendingFriendsListCount(UserFriends userFriends);
	/**
	 * 登录的用户访问其他用户的个人信息，我们应该能够获取好友请求的状态
	 * @param userFriends 必须传入from to user
	 * @return
	 */
	public UserFriends selct2PFriendStatus(UserFriends userFriends);
	
	
	/**
	 * 登录的用户访问其他用户的个人信息，我们应该能够获取好友请求的状态
	 * @param userFriends 必须传入from to user
	 * @return 返回数量大于0 表示是朋友
	 * 
	 */
	public int selct2PIsFriend(UserFriends userFriends);
	
	
	/**
	 * @param friends
	 * @return
	 */
	public int deleteFriends(UserFriends friends);
}
