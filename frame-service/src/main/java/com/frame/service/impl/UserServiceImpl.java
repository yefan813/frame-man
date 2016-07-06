package com.frame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frame.dao.UserDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.AppSecret;
import com.frame.domain.User;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.AppSecretService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.RandomStrUtils;


@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private AppSecretService appSecretService;

	@Override
	public BaseDao<User, Long> getDao() {
		// TODO Auto-generated method stub
		return userDao;
	}

	@Override
	public int updateByTel(User user) {
		return userDao.updateUserByTel(user);
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public RemoteResult registUser(User user) {
		RemoteResult res = null;
		int appRes = 0;
		int result = userDao.insertEntry(user);
		
		AppSecret appSecret = new AppSecret();
		appSecret.setUserId(user.getId().intValue());
		
		List<AppSecret> resList = appSecretService.selectEntryList(appSecret);
		if(CollectionUtils.isNotEmpty(resList)){
			appSecret = resList.get(0);
		}else{
			appSecret.setUserName(user.getName());
			appSecret.setApiKey(RandomStrUtils.getUniqueString(10));
			appSecret.setSecretKey(RandomStrUtils.getUniqueString(16));
			appRes = appSecretService.insertEntry(appSecret);
		}
		
		
		if(result > 0 && appRes > 0){
			AppSecret returnData = new AppSecret();
			returnData.setApiKey(appSecret.getApiKey());
			returnData.setSecretKey(appSecret.getSecretKey());
			res = RemoteResult.success(returnData);
		}else{
			res = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
		}
		return res;
	}
	

}
