package com.frame.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Playground;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.GaoDeAPIResult;
import com.frame.service.PlayGroundInfoService;
import com.frame.service.utils.HttpClientUtil;

public class GetPlayGroundFromBaidu {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetPlayGroundFromBaidu.class);
	
	@Value("${GaoDeAPIKey}")
	private String BAIDU_PRIVATE_KEY;
	
	@Value("${GaoDeAPIUrl}")
	private String BAIDU_MAP_URL;
	
	@Resource
	private PlayGroundInfoService playGroundInfoService;
	
	public void work(){
		System.out.println("从百度取数据------");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("key", BAIDU_PRIVATE_KEY);
		params.put("keywords", "篮球场");
		params.put("city", "成都");
		params.put("types", "");
		params.put("offset", 100);
		params.put("page", 1);
		params.put("extensions", "all");
		
		try{
			String result = HttpClientUtil.sendGetRequestByJava(BAIDU_MAP_URL, params,null);
			GaoDeAPIResult gaodeApiResult = JSON.parseObject(result, GaoDeAPIResult.class);
			List<Playground> pList = JSON.parseArray(gaodeApiResult.getPois(), Playground.class);
			System.out.println("从百度取数据------" + gaodeApiResult.getPois());
			storDate2DB(pList);
		}catch(Exception e){
			LOGGER.error("请求高德api出现错误" + e);
			System.out.println("请求高德api出现错误" + e);
		}
	}
	
	private void storDate2DB(List<Playground> pList){
		if(CollectionUtils.isEmpty(pList)){
			LOGGER.error("输入的参数为空" + pList);
		}
		
		for (Playground playground : pList) {
			playground.setYn(YnEnum.Normal.getKey());
			playGroundInfoService.insertEntry(playground);
		}
	}

}
