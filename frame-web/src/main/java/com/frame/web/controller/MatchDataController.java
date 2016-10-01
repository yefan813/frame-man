package com.frame.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.domain.Match;
import com.frame.domain.MatchData;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.MatchDataService;
import com.frame.service.MatchService;
import com.frame.service.utils.MyCacheUtil;

@Controller
@RequestMapping(value = "/matchData")
public class MatchDataController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchDataController.class);

	@Resource
	private MatchDataService matchDataService;

	@Resource
	private MatchService matchService;

	@RequestMapping(value = "/uploadMatchData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String uploadMatchData(MatchData matchData) {
		RemoteResult result = null;
		try {

			if (null == matchData || matchData.getHomeTeamId() == null || matchData.getGuestTeamId() == null
					|| matchData.getMatchId() == null) {
				LOGGER.info("调用uploadMatchData 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			Match query = new Match();
			query.setHomeTeamId(matchData.getHomeTeamId());
			query.setGuestTeamId(matchData.getGuestTeamId());
			List<Match> matchs = matchService.selectEntryList(query);
			if(CollectionUtils.isEmpty(matchs)){
				LOGGER.info("调用uploadMatchData 比赛不存在");
				result = RemoteResult.failure("0001", "比赛不存在");
				return JSON.toJSONString(result);
			}

			matchData.setYn(YnEnum.Normal.getKey());
			if (matchDataService.insertEntry(matchData) > 0) {
				LOGGER.info("调用uploadMatchData 上传数据成功");
				result = RemoteResult.success();
			} else {
				LOGGER.info("调用uploadMatchData 上床数据失败");
				result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),
						BusinessCode.SERVER_INTERNAL_ERROR.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("服务器内部错误", e);
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/getMatchDataById", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getMatchDataById(Long matchId) {
		RemoteResult result = null;
		String  mathData = null;
		Map<String,Object> map = null;
		try {
			if (null == matchId || matchId < 0) {
				LOGGER.info("调用getMatchDataById 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			//从缓存取
			mathData = MyCacheUtil.getMatchStaticsHTML(matchId);
			
			if(StringUtils.isBlank(mathData)){
				MatchData query = new MatchData();
				query.setMatchId(matchId);
				query.setYn(YnEnum.Normal.getKey());
	
				List<MatchData> matchDatas = matchDataService.selectEntryList(query);
				if (CollectionUtils.isNotEmpty(matchDatas)) {
					LOGGER.info("调用 getMatchData 获取数据成功");
					map = convert2String(matchDatas.get(0));
				} else {
					LOGGER.info("调用getMatchData 获取数据失败");
					result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
				}
				result = RemoteResult.success(map);
				mathData = JSON.toJSONString(result);
				//TODO 插入redis
				MyCacheUtil.setMatchStaticsHTML(matchId, mathData);
			}	
			
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return mathData;
	}
	
	@RequestMapping(value = "/viewMatchStatics", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewMatchStatics(Long matchId, Model view) {
		String mathData = "";
		Map<String,Object> map = null;
		if (null == matchId || matchId < 0) {
			LOGGER.info("调用getMatchDataById 传入的参数错误");
			return "/matchStatics/list";
		}
		
		mathData = MyCacheUtil.getMatchStaticsHTML(matchId);
		
		if(StringUtils.isBlank(mathData)){
			MatchData query = new MatchData();
			query.setMatchId(matchId);
			query.setYn(YnEnum.Normal.getKey());
	
			List<MatchData> matchDatas = matchDataService.selectEntryList(query);
			if (CollectionUtils.isNotEmpty(matchDatas)) {
				LOGGER.info("调用 getMatchData 获取数据成功");
				map = convert2String(matchDatas.get(0));
			} 
			RemoteResult result = RemoteResult.success(map);
			mathData = JSON.toJSONString(result);
			//TODO 插入redis
			MyCacheUtil.setMatchStaticsHTML(matchId, mathData);
		}
		
		view.addAttribute("matchData", mathData);
		return "/matchStatics/list";
	}
	
	private Map<String,Object> convert2String(MatchData data){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("matchId", data.getMatchId());
		map.put("homeTeamId", data.getHomeTeamId());
		map.put("guestTeamId", data.getGuestTeamId());
		
		map.put("homeTeamData", JSON.parseObject(data.getHomeTeamData()));
		map.put("guestTeamData", JSON.parseObject(data.getGuestTeamData()));
		
		return map;
	}

}
