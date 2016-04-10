package com.frame.dao;

import java.util.List;
import java.util.Map;

import com.frame.dao.base.BaseDao;
import com.frame.domain.Playground;
import com.frame.domain.Team;

public interface TeamDao extends BaseDao<Team, Long> {
	
	/**
	 * 查找球场信息
	 * @param parameters
	 * @return
	 */
	public List<Playground> getPlaygroundInfo(Map<String, Object> parameters);

}
