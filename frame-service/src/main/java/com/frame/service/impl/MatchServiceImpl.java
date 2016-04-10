package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.MatchDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Match;
import com.frame.service.MatchService;
import com.frame.service.base.BaseServiceImpl;


@Service("matchService")
public class MatchServiceImpl extends BaseServiceImpl<Match, Long> implements MatchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchServiceImpl.class);
	
	
	@Resource
	private MatchDao matchDao;
	

	@Override
	public BaseDao<Match, Long> getDao() {
		// TODO Auto-generated method stub
		return matchDao;
	}

}
