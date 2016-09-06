package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.MatchDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Match;
import com.frame.domain.Team;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.MatchVO;
import com.frame.domain.vo.TeamVO;
import com.frame.service.MatchService;
import com.frame.service.TeamService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.CopyProperties;
import com.google.common.collect.Lists;

@Service("matchService")
public class MatchServiceImpl extends BaseServiceImpl<Match, Long> implements MatchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchServiceImpl.class);

	@Resource
	private MatchDao matchDao;

	@Resource
	private TeamService teamService;

	@Override
	public BaseDao<Match, Long> getDao() {
		// TODO Auto-generated method stub
		return matchDao;
	}

	@Override
	@Transactional
	public RemoteResult createMatch(Match match) {
		RemoteResult result = null;
		if (null == match) {
			result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
					BusinessCode.PARAMETERS_ERROR.getValue());
			return result;
		}
		MatchVO vo = new MatchVO();
		vo.setAddress(match.getAddress());
		int res = matchDao.insertEntryCreateId(match);
		if (res <= 0) {
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),
					BusinessCode.SERVER_INTERNAL_ERROR.getValue());
			return result;
		}
		vo.setId(match.getId());

		TeamVO sourceTeam = teamService.getTeamById(match.getHomeTeamId());
		if (null != sourceTeam) {
			vo.setHomeTeam(sourceTeam);
		}

		TeamVO targetTeam = teamService.getTeamById(match.getGuestTeamId());
		if (null != targetTeam) {
			vo.setGuestTeam(targetTeam);
		}

		result = RemoteResult.success(vo);
		return result;
	}

	@Override
	public RemoteResult getMatchByTeamId(Page<MatchVO> page, Long teamId) {
		RemoteResult result = null;
		try {

			if (null == teamId || teamId < 0) {
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
						BusinessCode.PARAMETERS_ERROR.getValue());
				return result;
			}
			List<MatchVO> voList = Lists.newArrayList();

			List<Match> matchs = matchDao.getMatchByTeamId(page, teamId);
			if (CollectionUtils.isEmpty(matchs)) {
				LOGGER.info("没找到相关数据");
				result = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
				return result;
			}

			for (Match match : matchs) {
				MatchVO vo = new MatchVO();
				
				CopyProperties.copy(match, vo);
				TeamVO sourceTeam = teamService.getTeamById(match.getHomeTeamId());
				vo.setHomeTeam(sourceTeam);
				
				TeamVO targetTeam = teamService.getTeamById(match.getGuestTeamId());
				vo.setGuestTeam(targetTeam);
				vo.setId(match.getId());
				voList.add(vo);

			}
			int totalCount = matchDao.getMatchByTeamIdCount(teamId);

			page.setResult(voList);
			page.setTotalCount(totalCount);
			result = RemoteResult.success(page);
		} catch (Exception e) {
			LOGGER.error("调用getMatchByTeamId异常",e);
		}
		return result;
	}

	@Override
	public int getMatchByTeamIdCount(Long teamId) {
		return matchDao.getMatchByTeamIdCount(teamId);
	}

}
