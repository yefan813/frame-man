package com.frame.dao;

import java.util.List;

import com.frame.dao.base.BaseDao;
import com.frame.domain.User;
import com.frame.domain.UserLogin;

public interface UserDao extends BaseDao<User, Long> {

	public int updateUserByTel(User user);

	public List<User> getNearByUser(UserLogin userLogin);

	public int getNearByUserCount(UserLogin userLogin);

}
