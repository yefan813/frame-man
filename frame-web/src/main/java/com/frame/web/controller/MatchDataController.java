package com.frame.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.frame.domain.MatchData;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.MatchDataService;


@Controller
@RequestMapping(value = "/matchData")
public class MatchDataController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchDataController.class);
	
	@Resource
	private MatchDataService matchDataService;
	
	@RequestMapping(value = "/uploadMatchData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String uploadMatchData(MatchData matchData) {
		RemoteResult result = null;
		if(null == matchData || matchData.getHomeTeamId() == null ||  matchData.getGuestTeamId() == null ){
			LOGGER.info("调用uploadMatchData 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		matchData.setYn(YnEnum.Normal.getKey());
		if(matchDataService.insertEntry(matchData) > 0){
			LOGGER.info("调用uploadMatchData 上传数据成功");
			result = RemoteResult.success();
		}else{
			LOGGER.info("调用uploadMatchData 上床数据失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping(value = "/getMatchDataById", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getMatchDataById(Long matchId) {
		RemoteResult result = null;
		if(null == matchId || matchId < 0 ){
			LOGGER.info("调用getMatchData 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		MatchData query = new MatchData();
		query.setMatchId(matchId);
		query.setYn(YnEnum.Normal.getKey());
		
		List<MatchData> matchDatas = matchDataService.selectEntryList(query);
		if(CollectionUtils.isNotEmpty(matchDatas)){
			LOGGER.info("调用 getMatchData 获取数据成功");
			result = RemoteResult.success(matchDatas.get(0));
		}else{
			LOGGER.info("调用getMatchData 获取数据失败");
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(),BusinessCode.NO_RESULTS.getValue());
		}
		return JSON.toJSONString(result);
	}
	
}
