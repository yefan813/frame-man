package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.UserLoginDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.UserLogin;
import com.frame.service.UserLoginService;
import com.frame.service.base.BaseServiceImpl;


@Service("userLoginService")
public class UserLoginServiceImpl extends BaseServiceImpl<UserLogin, Long> implements UserLoginService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);
	
	@Resource
	private UserLoginDao userLoginDao;

	@Override
	public BaseDao<UserLogin, Long> getDao() {
		// TODO Auto-generated method stub
		return userLoginDao;
	}
	

}
