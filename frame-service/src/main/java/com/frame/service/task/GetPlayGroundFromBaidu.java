package com.frame.service.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.frame.domain.Playground;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.GaoDeAPIResult;
import com.frame.service.PlayGroundInfoService;
import com.frame.service.utils.HttpClientUtil;

public class GetPlayGroundFromBaidu {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetPlayGroundFromBaidu.class);

	@Value("${GaoDeAPIKey}")
	private String BAIDU_PRIVATE_KEY;

	@Value("${GaoDeSearchAPIUrl}")
	private String BAIDU_MAP_URL;

	@Resource
	private PlayGroundInfoService playGroundInfoService;

	public void work() {
		LOGGER.info("从高德取数据------");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", BAIDU_PRIVATE_KEY);
		params.put("keywords", "篮球场");
		params.put("city", "成都");
		params.put("types", "");
		params.put("offset", 50);
		params.put("extensions", "all");

		try {
			for(int i = 1 ; i <= 30 ; i++){
				params.put("page", i);
				List<Playground> pList = sendGetPlaygroundRequest(params);
				if(CollectionUtils.isNotEmpty(pList)){
					storDate2DB(pList);
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("请求高德api出现错误" + e);
		}
	}

	private List<Playground> sendGetPlaygroundRequest(Map<String, Object> params) {
		List<Playground> pList = new ArrayList<Playground>();
		try {
			
			String result = HttpClientUtil.sendGetRequestByJava(BAIDU_MAP_URL, params, null);
			GaoDeAPIResult gaodeApiResult = JSON.parseObject(result, GaoDeAPIResult.class);
			
			JSONArray jsonArray  = JSON.parseArray(gaodeApiResult.getPois());
			Iterator<Object> ctgJsonIter = jsonArray.iterator();
			while (ctgJsonIter.hasNext()) {
				JSONObject ctgJson = (JSONObject) ctgJsonIter.next();
				ctgJson.remove("id");
				Playground playground = JSON.toJavaObject(ctgJson, Playground.class);
				pList.add(playground);
			}
			
//			pList = JSON.parseArray(, Playground.class);
		} catch (Exception e) {
			LOGGER.error("请求高德api出现错误" + e);
		}
		return pList;

	}

	private void storDate2DB(List<Playground> pList) {
		if (CollectionUtils.isEmpty(pList)) {
			LOGGER.error("输入的参数为空" + pList);
		}

		for (Playground playground : pList) {
			playground.setYn(YnEnum.Normal.getKey());
			
			String[] locations = playground.getLocation().split(",");
			if(locations.length > 0){
				playground.setLongitude(Double.valueOf(locations[0]));
				playground.setLatitude(Double.valueOf(locations[1]));
			}
			playGroundInfoService.insertEntry(playground);
		}
	}

}
