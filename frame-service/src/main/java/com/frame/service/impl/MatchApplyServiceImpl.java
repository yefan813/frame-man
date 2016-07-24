package com.frame.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.MatchApplyDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.MatchApply;
import com.frame.service.MatchApplyService;
import com.frame.service.base.BaseServiceImpl;


@Service("matchApplyService")
public class MatchApplyServiceImpl extends BaseServiceImpl<MatchApply, Long> implements MatchApplyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchApplyServiceImpl.class);
	
	
	@Resource
	private MatchApplyDao matchApplyDao;
	

	@Override
	public BaseDao<MatchApply, Long> getDao() {
		// TODO Auto-generated method stub
		return matchApplyDao;
	}

}
