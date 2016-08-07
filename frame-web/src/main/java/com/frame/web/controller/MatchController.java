package com.frame.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Match;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.MatchService;


@Controller
@RequestMapping(value = "/match")
public class MatchController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchController.class);
	
	@Resource
	private MatchService matchService;
	
	@RequestMapping(value = "/createMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String createMatch(Match match) {
		RemoteResult result = null;
		if(null == match || match.getHomeTeamId() == null ||  match.getGuestTeamId() == null ){
			LOGGER.info("调用createMatch 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		
		match.setYn(YnEnum.Normal.getKey());
		if(matchService.insertEntry(match) > 0){
			LOGGER.info("调用createMatch 创建比赛成功");
			result = RemoteResult.success(match);
		}else{
			LOGGER.info("调用createMatch 创建比赛失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return JSON.toJSONString(result);
	}
}
