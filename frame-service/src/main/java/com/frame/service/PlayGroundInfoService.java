package com.frame.service;

import java.util.List;

import com.frame.domain.Playground;
import com.frame.domain.common.Page;
import com.frame.service.base.BaseService;
import com.frame.service.vo.PlaygroundVO;

/**
 * @author yefan
 *
 */
public interface PlayGroundInfoService extends BaseService<Playground, Long> {

	/**
	 * 查找球场信息
	 * @param page
	 * @param playground
	 * @return
	 */
	public List<Playground> getPlaygroundInfo(Page<Playground> page, Playground playground);

	/**
	 * 提取球场信息
	 * @param params
	 * @param location
	 * @return
	 */
	public List<PlaygroundVO> conver2PlaygroundVO(List<Playground> params, String location);

	/**
	 * 更具经度纬度查看球场
	 * @param page
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public Page<Playground> getPlayGroundByLocation(Page<Playground> page, double longitude, double latitude);
}
