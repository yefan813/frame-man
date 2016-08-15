package com.frame.web.controller;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.frame.domain.JoinTeam;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.JoinTeamVO;
import com.frame.service.JoinTeamService;


@Controller
@RequestMapping(value = "/joinTeam")
public class JoinTeamController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(JoinTeamController.class);
	
	@Resource
	private JoinTeamService joinTeamService;
	
	@RequestMapping(value = "/getInventJoinTeamByUserId", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getApplyJoinTeamByTeamId(Page<JoinTeam> page, JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || joinTeam.getTeamId() == null ){
			LOGGER.info("调用getInventJoinTeamByUserId 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		joinTeam.setOrderField("created");
		joinTeam.setOrderFieldType("DESC");
		joinTeam.setType(JoinTeam.TYPE_APPLY);
		joinTeam.setYn(YnEnum.Normal.getKey());
		Page<JoinTeam> pages = joinTeamService.selectPage(joinTeam, page);
		if(CollectionUtils.isEmpty(pages.getResult())){
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
		}else{
			result = RemoteResult.success(pages);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/getInventJoinTeamByUserId", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getInventJoinTeamByUserId(Page<JoinTeam> page, JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || joinTeam.getUserId() == null ){
			LOGGER.info("调用getInventJoinTeamByUserId 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		joinTeam.setOrderField("created");
		joinTeam.setOrderFieldType("DESC");
		joinTeam.setType(JoinTeam.TYPE_INVENT);
		joinTeam.setYn(YnEnum.Normal.getKey());
		Page<JoinTeam> pages = joinTeamService.selectPage(joinTeam, page);
		if(CollectionUtils.isEmpty(pages.getResult())){
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
		}else{
			result = RemoteResult.success(pages);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/applyJoinTeam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String applyJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || joinTeam.getUserId() == null ||  joinTeam.getTeamId() == null ){
			LOGGER.info("调用applyJoinTeam 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = joinTeamService.applyJoinTeam(joinTeam);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/agreeApplyJoinTeam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String agreeApplyJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || joinTeam.getId() == null){
			LOGGER.info("调用agreeApplyJoinTeam 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = joinTeamService.agreeApplyJoinTeam(joinTeam);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/rejectApplyJoinTeam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String rejectApplyJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || joinTeam.getId() == null){
			LOGGER.info("调用rejectApplyJoinTeam 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = joinTeamService.rejectApplyJoinTeam(joinTeam);
		return JSON.toJSONString(result);
	}
	
	
	
	
	@RequestMapping(value = "/inventJoinTeam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String inventJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || joinTeam.getUserId() == null ||  joinTeam.getTeamId() == null || joinTeam.getInitiator() == null){
			LOGGER.info("调用inventJoinTeam 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = joinTeamService.inventJoinTeam(joinTeam);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/agreeInventJoinTeam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String agreeInventJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || joinTeam.getId() == null){
			LOGGER.info("调用agreeInventJoinTeam 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = joinTeamService.agreeInventJoinTeam(joinTeam);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/rejectInventJoinTeam", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String rejectInventJoinTeam(JoinTeam joinTeam) {
		RemoteResult result = null;
		if(null == joinTeam || joinTeam.getId() == null){
			LOGGER.info("调用rejectInventJoinTeam 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = joinTeamService.rejectInventJoinTeam(joinTeam);
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping(value = "/getJoinTeamRecordById", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getJoinTeamRecordById(Long id) {
		RemoteResult result = null;
		if(null == id || id < 0){
			LOGGER.info("调用getJoinTeamRecordById 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数id错误");
			return JSON.toJSONString(result);
		}
		JoinTeam joinTeam = joinTeamService.selectEntry(id);
		JoinTeamVO vo = joinTeamService.geJoinTeamVO(joinTeam);
		if(vo != null){
			LOGGER.info("调用getJoinTeamRecordById 数据成功");
			result = RemoteResult.success(vo);
		}else{
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(),BusinessCode.NO_RESULTS.getValue());
		}
		return JSON.toJSONString(result);
	}
	
	
}
