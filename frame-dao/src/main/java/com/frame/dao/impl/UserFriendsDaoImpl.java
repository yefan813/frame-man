package com.frame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserFriendsDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.UserFriends;

@Repository("userFriendsDao")
public class UserFriendsDaoImpl extends BaseDaoImpl<UserFriends, Long> implements UserFriendsDao {

	private final static String NAMESPACE = "com.frame.dao.UserFriendsDao.";
	
	private final static String CHANGEUSERFRIENDSTATUS  = "changeUserFriendStatus";
	
	private final static String SELECTFRIENDSLIST  = "selectFriendsList";
	private final static String SELECTFRIENDSLIST_COUNT  = "selectFriendsListCount";
	
	private final static String SELECTPENDINGFRIENDSLIST  = "selectPendingFriendsList";
	private final static String SELECTPENDINGFRIENDSLIST_COUNT  = "selectPendingFriendsListCount";
	
	private final static String SELCT2PFRIENDSTATUS  = "selct2PFriendStatus";
	private final static String SELCT2PISFRIEND  = "selct2PIsFriend";
	private final static String DELETEFRIENDS  = "deleteFriends";
	
		

	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public int changeUserFriendStatus(UserFriends userFriends) {
		return update(getNameSpace(CHANGEUSERFRIENDSTATUS), userFriends);
	}

	@Override
	public List<UserFriends> selectFriendsList(UserFriends userFriends) {
		return selectList(getNameSpace(SELECTFRIENDSLIST), userFriends);
	}

	@Override
	public List<UserFriends> selectPendingFriendsList(UserFriends userFriends) {
		return selectList(getNameSpace(SELECTPENDINGFRIENDSLIST), userFriends);
	}

	@Override
	public UserFriends selct2PFriendStatus(UserFriends userFriends) {
		return select(getNameSpace(SELCT2PFRIENDSTATUS), userFriends);
	}

	@Override
	public int selct2PIsFriend(UserFriends userFriends) {
		return select(getNameSpace(SELCT2PISFRIEND), userFriends);
	}

	@Override
	public int deleteFriends(UserFriends friends) {
		return update(getNameSpace(DELETEFRIENDS), friends);
	}

	@Override
	public int selectFriendsListCount(UserFriends userFriends) {
		return select(getNameSpace(SELECTFRIENDSLIST_COUNT), userFriends);
	}

	@Override
	public int selectPendingFriendsListCount(UserFriends userFriends) {
		return select(getNameSpace(SELECTPENDINGFRIENDSLIST_COUNT), userFriends);
	}


}
