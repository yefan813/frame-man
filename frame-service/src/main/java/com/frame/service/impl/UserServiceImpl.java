package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.UserDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.User;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;


@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserDao userDao;

	@Override
	public BaseDao<User, Long> getDao() {
		// TODO Auto-generated method stub
		return userDao;
	}

	@Override
	public int updateByTel(User user) {
		return userDao.updateUserByTel(user);
	}
	

}
