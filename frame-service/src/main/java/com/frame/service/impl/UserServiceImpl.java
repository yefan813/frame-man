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
import com.frame.domain.UserAuths;
import com.frame.domain.UserLogin;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.AppSecretService;
import com.frame.service.UserAuthsService;
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
	
	@Resource
	private UserAuthsService UserAuthsService;

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
	public RemoteResult registUser(User user, UserAuths userAuths) {
		RemoteResult res = null;
		int appRes = 0;
		int result = 0;
		//插入用户基本信息
		if(null != user && user.getId() != null){
			result = userDao.updateByKey(user);//更新默认用户
		}else if(null != user && user.getId() == null){
			result = userDao.insertEntryCreateId(user);//插入默认用户
		}
		if(null != user && result <= 0){
			res = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
			return res;
		}
		
		//生成用户授权信息
		int userAuthsRes= 0;
		if(null != user){
			userAuths.setUserId(user.getId().intValue());
		}
		if(null != userAuths && userAuths.getId() != null){
			userAuthsRes = UserAuthsService.updateByKey(userAuths);//更新授权信息
		}else if(null != userAuths && userAuths.getId() == null){
			userAuthsRes = UserAuthsService.insertEntry(userAuths);//插入授权信息
		}
		
		if(userAuthsRes <= 0){
			res = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
			return res;
		}
		
		//生成用户唯一的appKey 和 secretKey
		AppSecret appSecret = new AppSecret();
		appSecret.setYn(YnEnum.Normal.getKey());
		appSecret.setUserId(user.getId().intValue());
		List<AppSecret> resList = appSecretService.selectEntryList(appSecret);
		if(CollectionUtils.isNotEmpty(resList)){
			appSecret = resList.get(0);
			appRes = 1;
		}else{
			appSecret.setApiKey(RandomStrUtils.getUniqueString(10));
			appSecret.setSecretKey(RandomStrUtils.getUniqueString(16));
			appRes = appSecretService.insertEntry(appSecret);
		}
		if(appRes <= 0){
			res = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
			return res;
		}
		AppSecret secret = new AppSecret();
		secret.setUserId(appSecret.getUserId());
		secret.setApiKey(appSecret.getApiKey());
		secret.setSecretKey(appSecret.getSecretKey());
		res = RemoteResult.success(secret);
		
		return res;
	}
	@Override
	public List<User> getNearByUser(UserLogin userLogin) {
		return userDao.getNearByUser(userLogin);
	}

}
