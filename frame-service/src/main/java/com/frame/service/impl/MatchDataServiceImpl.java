package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.MatchDataDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.MatchData;
import com.frame.service.MatchDataService;
import com.frame.service.base.BaseServiceImpl;


@Service("matchDataService")
public class MatchDataServiceImpl extends BaseServiceImpl<MatchData, Long> implements MatchDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchDataServiceImpl.class);
	
	
	@Resource
	private MatchDataDao matchDataDao;
	

	@Override
	public BaseDao<MatchData, Long> getDao() {
		// TODO Auto-generated method stub
		return matchDataDao;
	}

}
