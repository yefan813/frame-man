package com.frame.service;

import java.util.List;
import java.util.Map;

import com.frame.domain.Playground;
import com.frame.domain.common.Page;

public interface PlayGroundInfoService {
	
		public List<Playground> getPlaygroundInfo(Page<Playground> page);

}
