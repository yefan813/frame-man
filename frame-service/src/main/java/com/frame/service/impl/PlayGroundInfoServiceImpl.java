package com.frame.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.dao.PlayGroundDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Playground;
import com.frame.domain.common.Page;
import com.frame.service.PlayGroundInfoService;
import com.frame.service.base.BaseServiceImpl;

public class PlayGroundInfoServiceImpl extends BaseServiceImpl<Playground, Long> implements PlayGroundInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayGroundInfoServiceImpl.class);
	
	
	@Resource
	private PlayGroundDao playGroundDao;
	
	@Override
	public List<Playground> getPlaygroundInfo(Page<Playground> page) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("currentPage", page.getCurrentPage());
		params.put("pageSize", page.getPageSize());
//		params.put("orderField", "createDate");
//		params.put("orderFieldType", "DESC");
		return playGroundDao.getPlaygroundInfo(params);
	}

	@Override
	public BaseDao<Playground, Long> getDao() {
		// TODO Auto-generated method stub
		return playGroundDao;
	}

}
