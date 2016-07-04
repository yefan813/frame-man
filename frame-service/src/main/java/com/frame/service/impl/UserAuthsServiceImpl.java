package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.UserAuthsDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.UserAuths;
import com.frame.service.UserAuthsService;
import com.frame.service.base.BaseServiceImpl;


@Service("userAutahsService")
public class UserAuthsServiceImpl extends BaseServiceImpl<UserAuths, Long> implements UserAuthsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthsServiceImpl.class);
	
	@Resource
	private UserAuthsDao userAuthsDao;

	@Override
	public BaseDao<UserAuths, Long> getDao() {
		// TODO Auto-generated method stub
		return userAuthsDao;
	}
	

}
