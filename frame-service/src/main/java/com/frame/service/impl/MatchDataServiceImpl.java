package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.dao.MatchDataDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Match;
import com.frame.domain.MatchData;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.MatchDataService;
import com.frame.service.MatchService;
import com.frame.service.base.BaseServiceImpl;


@Service("matchDataService")
public class MatchDataServiceImpl extends BaseServiceImpl<MatchData, Long> implements MatchDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchDataServiceImpl.class);
	
	
	@Resource
	private MatchDataDao matchDataDao;
	
	@Resource
	private MatchService matchService;
	

	@Override
	public BaseDao<MatchData, Long> getDao() {
		// TODO Auto-generated method stub
		return matchDataDao;
	}
	
	
	@Override
	public RemoteResult uploadMatchData(MatchData matchData) {
		RemoteResult result = null;
		
		//查找match是否存在
		Match match = matchService.selectEntry(matchData.getMatchId());
		if(match == null){
			LOGGER.info("调用uploadMatchData 比赛不存在");
			result = RemoteResult.failure("0001", "比赛不存在");
			return result;
		}
		//查找比赛数据是否存在
		matchData.setMatchId(matchData.getMatchId());
		matchData.setYn(YnEnum.Normal.getKey());
		//插入新的数据
		if (insertEntry(matchData) > 0) {
			LOGGER.info("调用uploadMatchData insertEntry 上传数据成功");
			result = RemoteResult.success();
		} else {
			LOGGER.info("调用uploadMatchData insertEntry数据失败");
			result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),
					BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return result;
	}

}
