package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.PlayGroundDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Playground;
import com.frame.domain.common.Page;
import com.frame.service.PlayGroundInfoService;
import com.frame.service.base.BaseServiceImpl;


@Service("playGroundInfoService")
public class PlayGroundInfoServiceImpl extends BaseServiceImpl<Playground, Long> implements PlayGroundInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayGroundInfoServiceImpl.class);
	
	
	@Resource
	private PlayGroundDao playGroundDao;
	
	@Override
	public List<Playground> getPlaygroundInfo(Page<Playground> page,Playground playground) {
		return playGroundDao.selectEntryList(playground);
	}

	@Override
	public BaseDao<Playground, Long> getDao() {
		// TODO Auto-generated method stub
		return playGroundDao;
	}

}
