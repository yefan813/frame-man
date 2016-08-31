package com.frame.dao.impl;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserFriendsDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.UserFriends;

@Repository("userFriendsDao")
public class UserFriendsDaoImpl extends BaseDaoImpl<UserFriends, Long> implements UserFriendsDao {

	private final static String NAMESPACE = "com.frame.dao.UserFriendsDao.";

	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


}
