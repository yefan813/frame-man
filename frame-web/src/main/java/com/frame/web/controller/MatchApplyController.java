package com.frame.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.frame.domain.MatchApply;
import com.frame.domain.common.RemoteResult;
import com.frame.service.MatchApplyService;


@Controller
@RequestMapping(value = "/matchApply")
public class MatchApplyController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchApplyController.class);
	
	@Resource
	private MatchApplyService matchApplyService;
	
	
	@RequestMapping(value = "/applyMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String applyMatch(MatchApply matchApply) {
		RemoteResult result = null;
		if(null == matchApply || matchApply.getType() == null ||matchApply.getSourceIdentityId()==null || matchApply.getTargetIdentityId() == null){
			LOGGER.info("调用applyMatch 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = matchApplyService.applyMatch(matchApply);
		return JSON.toJSONString(result);
	}
}
