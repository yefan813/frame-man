package com.frame.dao;

import java.util.List;

import com.frame.dao.base.BaseDao;
import com.frame.domain.User;
import com.frame.domain.UserLogin;

public interface UserLoginDao extends BaseDao<UserLogin, Integer> {
	public List<UserLogin> queryUserDeviceTokenByTeamId(Integer teamId);
}
