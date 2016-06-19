package com.frame.service;

import java.util.List;

import com.frame.domain.Playground;
import com.frame.domain.common.Page;
import com.frame.service.base.BaseService;
import com.frame.service.vo.PlaygroundVO;

public interface PlayGroundInfoService extends BaseService<Playground, Long> {
	
	public List<Playground> getPlaygroundInfo(Page<Playground> page,Playground playground);
	public List<PlaygroundVO> conver2PlaygroundVO(List<Playground> params,String location);
	public Page<Playground> getPlayGroundByLocation(Page<Playground> page,double longitude,
            double latitude);
}
