package com.frame.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Playground;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.PlayGroundInfoService;
import com.frame.service.vo.PlaygroundVO;


@Controller
@RequestMapping(value = "/playground")
public class PlaygroundController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlaygroundController.class);
	
	@Resource
	private PlayGroundInfoService playGroundInfoService;
	
	
	@RequestMapping(value = "/listPlaygrounds", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String listPlaygrounds(Page<Playground> page,Playground playground){
		RemoteResult result = null;
		try{
			playground.setYn(YnEnum.Normal.getKey());
			Page<Playground> playgrounds = playGroundInfoService.selectPage(playground, page);
			result = RemoteResult.result(BusinessCode.SUCCESS, playgrounds.getResult());
		}catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		} 
		
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/listPlaygroundsByLocation", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String listPlaygrounds(Page<Playground> page,String location){
		RemoteResult result = null;
		List<PlaygroundVO> voList = null;
		try{
			Playground query = new Playground();
			query.setYn(YnEnum.Normal.getKey());
			Page<Playground> playgrounds = playGroundInfoService.selectPage(query, page);
			//根据location 计算羽球场之间的直线距离，然后排序
			if(CollectionUtils.isNotEmpty(playgrounds.getResult())){
				voList = playGroundInfoService.conver2PlaygroundVO(playgrounds.getResult(),location);
			}
			
			result = RemoteResult.result(BusinessCode.SUCCESS, voList);
		}catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		} 
		
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/getPlayGroundsByCityCode/{cityCode}", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getPlayGroundsByCityCode(@PathVariable String cityCode){
		LOGGER.info("调用getPlayGroundsByCityCode");
		RemoteResult result = null;
		if(null == cityCode || cityCode.length() <= 0){
			LOGGER.error("调用getPlayGroundsByCityCode传递的参数错误：", cityCode);
			result = RemoteResult.result(BusinessCode.PARAMETERS_ERROR);
			return JSON.toJSONString(result);
		}
		try{
			Playground query = new Playground();
			query.setCityCode(cityCode);
			query.setYn(YnEnum.Normal.getKey());
			List<Playground> playgrounds = playGroundInfoService.selectEntryList(query);
			if(null == playgrounds){
				result = RemoteResult.result(BusinessCode.NO_RESULTS);
				return JSON.toJSONString(result);
			}
			result = RemoteResult.result(BusinessCode.SUCCESS, playgrounds);
		}catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		} 
		
		return JSON.toJSONString(result);
	}
	
	
}
