package com.frame.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.frame.dao.PlayGroundDao;
import com.frame.dao.TeamDao;
import com.frame.dao.UserDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Playground;
import com.frame.domain.Team;
import com.frame.domain.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

	private final static String NAMESPACE = "com.frame.dao.UserDao.";
	
	private final static String UPDATE_BY_TEL = "updateByTel";

	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


	@Override
	public int updateUserByTel(User user) {
		return this.update(getNameSpace(UPDATE_BY_TEL), user);
	}


}
