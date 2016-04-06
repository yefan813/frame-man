package com.frame.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Playground;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.PlayGroundInfoService;


@Controller
@RequestMapping(value = "/playground")
public class PlaygroundController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlaygroundController.class);
	
	@Resource
	private PlayGroundInfoService playGroundInfoService;
	
	
	@RequestMapping(value = "/listPlaygrounds", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String queryGroupons(Page<Playground> page){
		RemoteResult result = null;
		try{
			Playground query = new Playground();
			query.setYn(YnEnum.Normal.getKey());
			Page<Playground> playgrounds = playGroundInfoService.selectPage(query, page);
			result = RemoteResult.result(BusinessCode.SUCCESS, playgrounds.getResult());
		}catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		} 
		
		return JSON.toJSONString(result);
	}
}
