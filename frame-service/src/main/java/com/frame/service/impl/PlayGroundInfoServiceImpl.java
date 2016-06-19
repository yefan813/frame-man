package com.frame.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Service;

import com.frame.dao.PlayGroundDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Playground;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.service.PlayGroundInfoService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.HttpClientUtil;
import com.frame.service.vo.PlaygroundVO;


@Service("playGroundInfoService")
public class PlayGroundInfoServiceImpl extends BaseServiceImpl<Playground, Long> implements PlayGroundInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayGroundInfoServiceImpl.class);
	
	private static final int DISTANCE_TYPE_DIS = 0;
	private static final int DISTANCE_TYPE_CAR = 1;
	private static final int DISTANCE_TYPE_BUS = 2;
	private static final int DISTANCE_TYPE_STEP = 3;
	
	
	
	@Resource
	private PlayGroundDao playGroundDao;
	
	@Value("${GaoDeAPIKey}")
	private String BAIDU_PRIVATE_KEY;

	@Value("${GaoDeDistanceAPIUrl}")
	private String GAODE_DISDANCE_URL;
	
	@Override
	public List<Playground> getPlaygroundInfo(Page<Playground> page,Playground playground) {
		return playGroundDao.selectEntryList(playground);
	}

	@Override
	public BaseDao<Playground, Long> getDao() {
		// TODO Auto-generated method stub
		return playGroundDao;
	}

	@Override
	public Page<Playground> getPlayGroundByLocation(Page<Playground> page,double longitude, double latitude) {
		
		Map<String , Double> params = new HashMap<String, Double>();
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		List<Playground> data = playGroundDao.getPlayGroundByLocation(params);
		if(CollectionUtils.isNotEmpty(data)){
			page.setResult(data);
		}
		
		Playground query = new Playground();
		query.setYn(YnEnum.Normal.getKey());
		int totalCount = playGroundDao.selectEntryListCount(query);
		
		page.setTotalCount(totalCount);
		return page;
	}

	@Override
	public List<PlaygroundVO> conver2PlaygroundVO(List<Playground> params,String location) {
		List<PlaygroundVO> voList = new ArrayList<PlaygroundVO>() ;
		if(CollectionUtils.isEmpty(params)){
			LOGGER.error("调用conver2PlaygroundVO 传入的参数错误");
			return null;
		}
		for (Playground playground : params) {
			PlaygroundVO vo = convert2VO(playground,location);
			voList.add(vo);
		}
		return voList;
	}

	private PlaygroundVO convert2VO(Playground playground,String location){
		PlaygroundVO vo = new PlaygroundVO();
		vo.setId(String.valueOf(playground.getId()));
		vo.setName(playground.getName());
		vo.setTel(playground.getTel());
		vo.setLocation(playground.getLocation());
		vo.setPcode(playground.getPcode());
		vo.setCityName(playground.getCityName());
		vo.setCityCode(playground.getCityCode());
		
		
		
		vo.setDictance(playground.getDistance());
		return vo;
	}
}
