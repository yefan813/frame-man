package com.frame.service;

import java.util.List;
import java.util.Map;

import com.frame.domain.Playground;
import com.frame.domain.common.Page;
import com.frame.service.base.BaseService;

public interface PlayGroundInfoService extends BaseService<Playground, Long> {
	
		public List<Playground> getPlaygroundInfo(Page<Playground> page,Playground playground);

}
