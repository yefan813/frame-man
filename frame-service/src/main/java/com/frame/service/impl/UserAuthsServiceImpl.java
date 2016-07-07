package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.UserAuthsDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.UserAuthsService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.RandomStrUtils;


@Service("userAuthsService")
public class UserAuthsServiceImpl extends BaseServiceImpl<UserAuths, Long> implements UserAuthsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthsServiceImpl.class);
	
	@Resource
	private UserAuthsDao userAuthsDao;
	

	@Override
	public BaseDao<UserAuths, Long> getDao() {
		return userAuthsDao;
	}


}
