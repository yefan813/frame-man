package com.frame.service.impl;

import java.util.List;

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
public class UserLoginServiceImpl extends BaseServiceImpl<UserLogin, Integer> implements UserLoginService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);
	
	@Resource
	private UserLoginDao userLoginDao;

	@Override
	public BaseDao<UserLogin, Integer> getDao() {
		// TODO Auto-generated method stub
		return userLoginDao;
	}

	@Override
	public List<UserLogin> queryUserDeviceTokenByTeamId(Integer teamId) {
		return userLoginDao.queryUserDeviceTokenByTeamId(teamId);
	}

	@Override
	public int registDeviceToken(UserLogin userLogin) {
		return userLoginDao.registDeviceToken(userLogin);
	}

	

}
