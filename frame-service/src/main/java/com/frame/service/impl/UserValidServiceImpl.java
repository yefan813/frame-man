package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.UserValidDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.UserValid;
import com.frame.service.UserValidService;
import com.frame.service.base.BaseServiceImpl;


@Service("userValidService")
public class UserValidServiceImpl extends BaseServiceImpl<UserValid, Long> implements UserValidService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserValidServiceImpl.class);
	
	@Resource
	private UserValidDao userValidDao;

	@Override
	public BaseDao<UserValid, Long> getDao() {
		// TODO Auto-generated method stub
		return userValidDao;
	}
	

}
