package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.UserTeamRelationDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.UserTeamRelation;
import com.frame.service.UserTeamRelationService;
import com.frame.service.base.BaseServiceImpl;


@Service("userTeamRelationService")
public class UserTeamRelationServiceImpl extends BaseServiceImpl<UserTeamRelation, Long> implements UserTeamRelationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserTeamRelationServiceImpl.class);
	
	
	@Resource
	private UserTeamRelationDao userTeamRelationDao;

	@Override
	public BaseDao<UserTeamRelation, Long> getDao() {
		return userTeamRelationDao;
	}

}
