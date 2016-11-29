package com.frame.service.task;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.domain.MatchApply;
import com.frame.domain.base.YnEnum;
import com.frame.service.MatchApplyService;

public class DeletePersonApplyDate {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeletePersonApplyDate.class);

	@Resource
	private MatchApplyService matchApplyService;
	
	
	public void work() {
		LOGGER.info("start delete person out date apply");
		List<MatchApply> matchApplies = matchApplyService.selectPersionOutDateApply();
		deletePersonApply(matchApplies);
		LOGGER.info("delete person out date apply Done .......");
	}

	private void deletePersonApply(List<MatchApply> matchApplies){
		if(CollectionUtils.isEmpty(matchApplies)){
			LOGGER.error("delete person outdate apply pass invalid parammeters");
			return;
		}
		try {
			for (MatchApply matchApply : matchApplies) {
				matchApply.setYn(YnEnum.Deleted.getKey());
				
				matchApplyService.updateByKey(matchApply);
			}
			
		} catch (Exception e) {
			LOGGER.error("delete person out date apply occur a Exception", e);
		}
	}
	
}
