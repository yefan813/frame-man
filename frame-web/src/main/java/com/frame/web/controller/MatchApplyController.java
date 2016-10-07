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
import com.frame.domain.vo.MatchApplyVO;
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
	 * 个人赛申请接口
	 * 
	 * @param matchApply
	 * @return
	 */
	@RequestMapping(value = "/persionApplyMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String persionApplyMatch(MatchApply matchApply) {
		RemoteResult result = null;
		try {
			if (matchApply.getSourceIdentityId() == null || matchApply.getLatitude() == null || matchApply.getLongitude() == null) {
				LOGGER.info("调用applyMatch 传入的参数SourceIdentityI错误");
				result = RemoteResult.failure("0001", "传入参数SourceIdentityI错误");
				return JSON.toJSONString(result);
			}
			matchApply.setType(MatchApply.TYPE_PERSONLY);
			matchApply.setParentApplyId(MatchApply.DEFAULT_APPLYER_IDENTITY);
			matchApply.setStatus(MatchApply.STATUS_APPLYING);
			matchApply.setYn(YnEnum.Normal.getKey());
			result = matchApplyService.applyMatch(matchApply);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 球队比赛申请接口
	 * 
	 * @param matchApply
	 * @return
	 */
	@RequestMapping(value = "/teamApplyMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String teamApplyMatch(MatchApply matchApply) {
		RemoteResult result = null;
		try {
			if (matchApply.getSourceIdentityId() == null || matchApply.getTargetIdentityId() == null) {
				LOGGER.info("调用applyMatch 传入的参数SourceIdentityI,TargetIdentityId错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			matchApply.setType(MatchApply.TYPE_TEAM);
			matchApply.setStatus(MatchApply.STATUS_APPLYING);
			matchApply.setYn(YnEnum.Normal.getKey());
			result = matchApplyService.applyMatch(matchApply);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 比赛申请接口，支持个人，球队
	 * 
	 * @param matchApply
	 * @return
	 */
	@RequestMapping(value = "/applyMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String applyMatch(MatchApply matchApply) {
		RemoteResult result = null;
		try {
			if (null == matchApply || matchApply.getType() == null) {
				LOGGER.info("调用applyMatch 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			if (matchApply.getType() != null) {
				if (matchApply.getType() == MatchApply.TYPE_PERSONLY && matchApply.getSourceIdentityId() == null) {
					LOGGER.info("调用applyMatch 传入的参数SourceIdentityI错误");
					result = RemoteResult.failure("0001", "传入参数SourceIdentityI错误");
					return JSON.toJSONString(result);
				} else if (matchApply.getType() == MatchApply.TYPE_TEAM
						&& (matchApply.getSourceIdentityId() == null || matchApply.getTargetIdentityId() == null)) {
					LOGGER.info("调用applyMatch 传入的参数SourceIdentityI,TargetIdentityId错误");
					result = RemoteResult.failure("0001", "传入参数错误");
					return JSON.toJSONString(result);
				}
			}

			if (matchApply.getType() == MatchApply.TYPE_PERSONLY) {
				matchApply.setParentApplyId(MatchApply.DEFAULT_APPLYER_IDENTITY);
			}
			matchApply.setStatus(MatchApply.STATUS_APPLYING);
			matchApply.setYn(YnEnum.Normal.getKey());
			result = matchApplyService.applyMatch(matchApply);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 根据userId 查找个人约球记录
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/joinPersionMatchApply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String joinPersionMatchApply(Integer persionApplyId, Integer userId) {
		RemoteResult result = null;
		try {
			if (userId == null || persionApplyId == null) {
				LOGGER.info("调用joinPersionMatchApply 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			result = matchApplyService.joinPersionMatchApply(persionApplyId, userId);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 根据userId 查找个人约球记录
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/persionMatchApply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String queryPersionMatchApply(Integer userId) {
		RemoteResult result = null;
		try {
			if (userId == null) {
				LOGGER.info("调用mineApplyMatch 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}

			List<MatchApplyVO> voList = matchApplyService.queryPersionMatchApply(userId);
			if (CollectionUtils.isEmpty(voList)) {
				LOGGER.info("调用mineApplyMatch ,无数据");
				result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
			} else {
				result = RemoteResult.success(voList);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/mineTeamApplyMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String mineTeamApplyMatch(Integer userId) {
		RemoteResult result = null;
		try {
			if (userId == null) {
				LOGGER.info("调用mineTeamApplyMatch 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			List<MatchApplyVO> voList = matchApplyService.queryMineTeamApplyMatch(userId);
			if (CollectionUtils.isEmpty(voList)) {
				LOGGER.info("调用mineTeamApplyMatch ,无数据");
				result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
			} else {
				result = RemoteResult.success(voList);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/mineTeamInventMatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String mineTeamInventMatch(Integer userId) {
		RemoteResult result = null;
		try {
			if (userId == null) {
				LOGGER.info("调用mineTeamInventMatch 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			List<MatchApplyVO> voList = matchApplyService.queryMineTeamInventMatch(userId);
			if (CollectionUtils.isEmpty(voList)) {
				LOGGER.info("调用mineTeamInventMatch ,无数据");
				result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
			} else {
				result = RemoteResult.success(voList);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/agreeMatchAply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String agreeMatchAply(Integer id) {
		RemoteResult result = null;
		try {
			if (id == null) {
				LOGGER.info("调用agreeMatchAply 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			MatchApply condition = new MatchApply();
			condition.setId(id);
			condition.setStatus(MatchApply.STATUS_AGREEMENT);
			if (matchApplyService.update(condition) > 0) {
				result = RemoteResult.success();
			} else {
				result = RemoteResult.failure(BusinessCode.FAILED.getCode(), BusinessCode.FAILED.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/regectMatchAply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String regectMatchAply(Integer id) {
		RemoteResult result = null;
		try {
			if (id == null) {
				LOGGER.info("调用regectMatchAply 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			MatchApply condition = new MatchApply();
			condition.setId(id);
			condition.setStatus(MatchApply.STATUS_REJECT);
			if (matchApplyService.update(condition) > 0) {
				result = RemoteResult.success();
			} else {
				result = RemoteResult.failure(BusinessCode.FAILED.getCode(), BusinessCode.FAILED.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/sourceTeamCancle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String sourceTeamCancle(Integer id) {
		RemoteResult result = null;
		try {
			if (id == null) {
				LOGGER.info("调用sourceTeamCancle 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			MatchApply condition = new MatchApply();
			condition.setId(id);
			condition.setStatus(MatchApply.STATUS_SOURCE_CANCLE);
			if (matchApplyService.update(condition) > 0) {
				result = RemoteResult.success();
			} else {
				result = RemoteResult.failure(BusinessCode.FAILED.getCode(), BusinessCode.FAILED.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/targetTeamCancle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String targetTeamCancle(Integer id) {
		RemoteResult result = null;
		try {
			if (id == null) {
				LOGGER.info("调用targetTeamCancle 传入的参数type错误");
				result = RemoteResult.failure("0001", "传入参数type错误");
				return JSON.toJSONString(result);
			}
			MatchApply condition = new MatchApply();
			condition.setId(id);
			condition.setStatus(MatchApply.STATUS_TARGET_CANCLE);
			condition.setYn(YnEnum.Normal.getKey());
			if (matchApplyService.update(condition) > 0) {
				result = RemoteResult.success();
			} else {
				result = RemoteResult.failure(BusinessCode.FAILED.getCode(), BusinessCode.FAILED.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/listPersionApplyMatchByLocation", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String listPersionApplyMatchByLocation(Page<MatchApply> page, String location) {
		RemoteResult result = null;
		double lng = 9999d;
		double lat = 9999d;
		try {
			if (null == location || !location.contains(",")) {
				result = RemoteResult.result(BusinessCode.PARAMETERS_ERROR);
				return JSON.toJSONString(result);
			}
			try {
				String[] locations = location.split(",");
				if (locations.length > 0) {
					lng = Double.valueOf(locations[0]);
					lat = Double.valueOf(locations[1]);
				}

				Page<MatchApplyVO> matchApplys = matchApplyService.getPerionApplyByLocation(page, lng, lat);
				if (matchApplys != null && CollectionUtils.isNotEmpty(matchApplys.getResult())) {
					LOGGER.info("调用listPersionApplyMatchByLocation ，获取数据成功");
					result = RemoteResult.success(matchApplys.getResult());
				} else {
					LOGGER.info("调用listPersionApplyMatchByLocation ，无数据");
					result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(),
							BusinessCode.NO_RESULTS.getValue());
				}

			} catch (Exception e) {
				LOGGER.error("列表异常", e);
				System.out.println("列表异常" + e);
				result = RemoteResult.result(BusinessCode.SERVER_INTERNAL_ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/getMatchApplyById", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getMatchApplyById(Integer id) {
		RemoteResult result = null;
		try {
			if (id == null) {
				LOGGER.info("调用getMatchApplyById 传入的参数id错误");
				result = RemoteResult.failure("0001", "传入参数id错误");
				return JSON.toJSONString(result);
			}
			MatchApplyVO condition = null;
			MatchApply matchApply = matchApplyService.selectEntry(id.longValue());
			if (null != matchApply) {
				condition = matchApplyService.getMatchApplyById(matchApply);
			}
			if (null == condition) {
				result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
			} else {
				result = RemoteResult.success(condition);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
}
