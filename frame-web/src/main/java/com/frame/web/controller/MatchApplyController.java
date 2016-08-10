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
import com.frame.domain.MatchApply;
import com.frame.domain.Playground;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.TeamApplyRecordVO;
import com.frame.domain.vo.UserApplyRecordVO;
import com.frame.service.MatchApplyService;
import com.frame.service.vo.PlaygroundVO;


@Controller
@RequestMapping(value = "/matchApply")
public class MatchApplyController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchApplyController.class);
	
	@Resource
	private MatchApplyService matchApplyService;
	
	
	/**
	 * 比赛申请接口，支持个人，球队
	 * @param matchApply
	 * @return
	 */
	@RequestMapping(value = "/applyMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String applyMatch(MatchApply matchApply) {
		RemoteResult result = null;
		
		if(null == matchApply || matchApply.getType() == null ){
			LOGGER.info("调用applyMatch 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		if(matchApply.getType() != null){
			if(matchApply.getType() == MatchApply.TYPE_PERSONLY && matchApply.getSourceIdentityId()==null){
				LOGGER.info("调用applyMatch 传入的参数SourceIdentityI错误");
				result = RemoteResult.failure("0001", "传入参数SourceIdentityI错误");
				return JSON.toJSONString(result);
			}
			else if(matchApply.getType() == MatchApply.TYPE_TEAM &&( matchApply.getSourceIdentityId()==null || matchApply.getTargetIdentityId() == null)){
				LOGGER.info("调用applyMatch 传入的参数SourceIdentityI,TargetIdentityId错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
		}
		
		if(matchApply.getType() == MatchApply.TYPE_PERSONLY){
			matchApply.setParentApplyId(MatchApply.DEFAULT_APPLYER_IDENTITY);
		}
		matchApply.setStatus(MatchApply.STATUS_APPLYING);
		matchApply.setYn(YnEnum.Normal.getKey());
		result = matchApplyService.applyMatch(matchApply);
		return JSON.toJSONString(result);
	}
	
	
	/**
	 * 根据userId 查找个人约球记录
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/joinPersionMatchApply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String joinPersionMatchApply(Integer persionApplyId ,Integer userId){
		RemoteResult result = null;
		if(userId == null || persionApplyId == null){
			LOGGER.info("调用joinPersionMatchApply 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		MatchApply originalApply = matchApplyService.selectEntry(persionApplyId.longValue());
		if(null == originalApply){
			result = RemoteResult.failure("0001", "没找到此个人约球记录");
			return JSON.toJSONString(result);
		}
		MatchApply matchApply = new MatchApply();
		matchApply.setSourceIdentityId(originalApply.getSourceIdentityId());
		matchApply.setTargetIdentityId(userId);
		matchApply.setParentApplyId(persionApplyId);
		matchApply.setYn(YnEnum.Normal.getKey());
		List<UserApplyRecordVO> voList = matchApplyService.queryPersionMatchApply(userId);
		if(CollectionUtils.isEmpty(voList)){
			LOGGER.info("调用mineApplyMatch ,无数据");
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
		}else{
			result = RemoteResult.success(voList);
		}
		return JSON.toJSONString(result);
	}
	
	
	/**
	 * 根据userId 查找个人约球记录
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/persionMatchApply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String queryPersionMatchApply(Integer userId) {
		RemoteResult result = null;
		if(userId == null ){
			LOGGER.info("调用mineApplyMatch 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		
		List<UserApplyRecordVO> voList = matchApplyService.queryPersionMatchApply(userId);
		if(CollectionUtils.isEmpty(voList)){
			LOGGER.info("调用mineApplyMatch ,无数据");
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
		}else{
			result = RemoteResult.success(voList);
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/mineTeamApplyMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String mineTeamApplyMatch(Integer userId) {
		RemoteResult result = null;
		if(userId == null){
			LOGGER.info("调用mineTeamApplyMatch 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		List<TeamApplyRecordVO> voList = matchApplyService.queryMineTeamApplyMatch(userId);
		if(CollectionUtils.isEmpty(voList)){
			LOGGER.info("调用mineTeamApplyMatch ,无数据");
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
		}else{
			result = RemoteResult.success(voList);
		}
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping(value = "/mineTeamInventMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String mineTeamInventMatch(Integer userId) {
		RemoteResult result = null;
		if(userId == null){
			LOGGER.info("调用mineTeamInventMatch 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		List<TeamApplyRecordVO> voList = matchApplyService.queryMineTeamInventMatch(userId);
		if(CollectionUtils.isEmpty(voList)){
			LOGGER.info("调用mineTeamInventMatch ,无数据");
			result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
		}else{
			result = RemoteResult.success(voList);
		}
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping(value = "/agreeMatchAply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String agreeMatchAply(Integer id) {
		RemoteResult result = null;
		if(id == null){
			LOGGER.info("调用agreeMatchAply 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		MatchApply condition = new MatchApply();
		condition.setId(id);
		condition.setStatus(MatchApply.STATUS_AGREEMENT);
		if(matchApplyService.update(condition) > 0){
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure(BusinessCode.FAILED.getCode(), BusinessCode.FAILED.getValue());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/regectMatchAply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String regectMatchAply(Integer id) {
		RemoteResult result = null;
		if(id == null){
			LOGGER.info("调用regectMatchAply 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		MatchApply condition = new MatchApply();
		condition.setId(id);
		condition.setStatus(MatchApply.STATUS_REJECT);
		if(matchApplyService.update(condition) > 0){
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure(BusinessCode.FAILED.getCode(), BusinessCode.FAILED.getValue());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/sourceTeamCancle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String sourceTeamCancle(Integer id) {
		RemoteResult result = null;
		if(id == null){
			LOGGER.info("调用sourceTeamCancle 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		MatchApply condition = new MatchApply();
		condition.setId(id);
		condition.setStatus(MatchApply.STATUS_SOURCE_CANCLE);
		if(matchApplyService.update(condition) > 0){
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure(BusinessCode.FAILED.getCode(), BusinessCode.FAILED.getValue());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/targetTeamCancle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String targetTeamCancle(Integer id) {
		RemoteResult result = null;
		if(id == null){
			LOGGER.info("调用targetTeamCancle 传入的参数type错误");
			result = RemoteResult.failure("0001", "传入参数type错误");
			return JSON.toJSONString(result);
		}
		MatchApply condition = new MatchApply();
		condition.setId(id);
		condition.setStatus(MatchApply.STATUS_TARGET_CANCLE);
		if(matchApplyService.update(condition) > 0){
			result = RemoteResult.success();
		}else{
			result = RemoteResult.failure(BusinessCode.FAILED.getCode(), BusinessCode.FAILED.getValue());
		}
		return JSON.toJSONString(result);
	}
	@RequestMapping(value = "/listPersionApplyMatchByLocation", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String listPersionApplyMatchByLocation(Page<MatchApply> page,String location){
		RemoteResult result = null;
		double lng = 9999d;double lat = 9999d;
		
		if(null == location || !location.contains(",")){
			result = RemoteResult.result(BusinessCode.PARAMETERS_ERROR);
			return JSON.toJSONString(result);
		}
		try{
			String[] locations = location.split(",");
			if(locations.length > 0){
				lng = Double.valueOf(locations[0]);
				lat = Double.valueOf(locations[1]);
			}
			
			Page<MatchApply> matchApplys = matchApplyService.getPerionApplyByLocation(page, lng, lat,MatchApply.TYPE_PERSONLY);
			if(matchApplys != null && CollectionUtils.isNotEmpty(matchApplys.getResult())){
				LOGGER.info("调用listPersionApplyMatchByLocation ，获取数据成功");
				result = RemoteResult.success( matchApplys.getResult());
			}else{
				LOGGER.info("调用listPersionApplyMatchByLocation ，无数据");
				result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
			}
			
		}catch (Exception e) {
			LOGGER.error("列表异常", e);
			System.out.println("列表异常" + e);
			result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
		} 
		
		return JSON.toJSONString(result);
	}
}
