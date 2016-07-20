package com.frame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.frame.dao.UserDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.User;
import com.frame.domain.UserLogin;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

	private final static String NAMESPACE = "com.frame.dao.UserDao.";
	
	private final static String UPDATE_BY_TEL = "updateByTel";
	private final static String GETNEARBYUSER = "getNearByUser";
	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


	@Override
	public int updateUserByTel(User user) {
		return this.update(getNameSpace(UPDATE_BY_TEL), user);
	}

	@Override
	public List<User> getNearByUser(UserLogin userLogin) {
		// TODO Auto-generated method stub
		return selectList(getNameSpace(GETNEARBYUSER),userLogin);
	}
}
