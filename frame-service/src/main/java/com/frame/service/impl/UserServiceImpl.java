package com.frame.service.impl;

import java.rmi.Remote;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.frame.chat.comm.body.IMUserBody;
import com.frame.dao.UserDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.AppSecret;
import com.frame.domain.MatchApply;
import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserLogin;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.AppSecretService;
import com.frame.service.EasemobAPIService;
import com.frame.service.UserAuthsService;
import com.frame.service.UserLoginService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import com.frame.service.utils.RandomStrUtils;
import com.google.common.collect.Lists;


@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private AppSecretService appSecretService;
	
	@Resource
	private UserAuthsService UserAuthsService;
	
	@Resource
	private UserLoginService userLoginService;
	
	@Resource
	private EasemobAPIService easemobAPIService;
	
	@Resource
	private UserAuthsService userAuthsService;
	
	@Value("${img.prefix}")
	private String IMAGEPREFIX;

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
	public RemoteResult registUser(User user, UserAuths userAuths) throws Exception {
		RemoteResult res = null;
		int appRes = 0;
		int result = 0;
		//注册环信用户
		if( user.getId() == null || StringUtils.isNotEmpty(user.getTel())){
			RemoteResult remoteResult = easemobAPIService.createNewIMUserSingle(user);
			if(remoteResult != null && "0000".equals(remoteResult.getCode())){
				LOGGER.info("用户调用环信借口创建环信用户成功");
			}
		}
		
		//插入用户基本信息
		if(null != user && user.getId() != null){
			result = userDao.updateByKey(user);//更新默认用户
		}else if(null != user && user.getId() == null){
			result = userDao.insertEntryCreateId(user);//插入默认用户
		}
		user = userDao.selectEntry(user.getId().longValue());
		
		if(null != user && result <= 0){
			LOGGER.error("registUser服务器内部错误,插入数据库失败");
			res = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
			return res;
		}
		
		//生成用户授权信息
		int userAuthsRes= 0;
		userAuths.setUserId(user.getId().intValue());
		
		if(null != userAuths && userAuths.getId() != null){
			userAuthsRes = UserAuthsService.updateByKey(userAuths);//更新授权信息
		}else if(null != userAuths && userAuths.getId() == null){
			userAuthsRes = UserAuthsService.insertEntry(userAuths);//插入授权信息
		}
		
		if(userAuthsRes <= 0){
			LOGGER.error("registUser服务器内部错误,插入数据库失败");
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
			LOGGER.error("registUser服务器内部错误,插入数据库失败");
			res = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(),BusinessCode.SERVER_INTERNAL_ERROR.getValue());
			return res;
		}
		AppSecret secret = new AppSecret();
		secret.setUserId(appSecret.getUserId());
		secret.setApiKey(appSecret.getApiKey());
		secret.setSecretKey(appSecret.getSecretKey());
		
		
		UserLogin condition = new UserLogin();
		condition.setUserId(appSecret.getUserId());
		userLoginService.insertEntry(condition);
		
		if(StringUtils.isEmpty(user.getTel())){
			res = RemoteResult.result(BusinessCode.NO_TEL_INFO,secret);
			return res;
		}
		res = RemoteResult.success(secret);
		return res;
	}
	@Override
	public RemoteResult getNearByUser(Page<User> page, UserLogin userLogin) {
		RemoteResult remoteResult = null; 
		userLogin.setStartIndex(page.getStartIndex());
		userLogin.setEndIndex(page.getEndIndex());
		
		List<User> res = userDao.getNearByUser(userLogin);
		//TODO image 加入前缀
		if(CollectionUtils.isNotEmpty(res)){
			for (User user : res) {
				user.setAvatarUrl(IMAGEPREFIX + user.getAvatarUrl());
			}
			remoteResult = RemoteResult.success(res);
		}else{
			remoteResult = RemoteResult.failure(BusinessCode.NO_RESULTS.getCode(), BusinessCode.NO_RESULTS.getValue());
			return remoteResult;
		}
		int total = userDao.getNearByUserCount(userLogin);
		
		page.setResult(res);
		page.setTotalCount(total);
		
		return remoteResult;
	}

	@Override
	public List<User> getUserJoinPersionApplyRecord(MatchApply matchApply) {
		return userDao.getUserJoinPersionApplyRecord(matchApply);
	}

	@Override
	public List<User> getTeamUserByTeamId(Long teamId) {
		return userDao.getTeamUserByTeamId(teamId);
	}

	@Override
	public RemoteResult bindTel(User user) {
		RemoteResult result;
		if(null == user || user.getId() == null || user.getTel() == null){
			LOGGER.info("调用bindTel 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return result;
		}
		user.setPassword(user.getTel());
		int res = updateByKey(user);
		
		if(res > 0){
			User dataUser = selectEntry(user.getId().longValue());
			//调用环信创建用户
			result = easemobAPIService.createNewIMUserSingle(dataUser);
			if("0000".equals(result.getCode())){
				result.setData(dataUser);
			}else{
				return result;
			}
			
		}else{
			result = RemoteResult.failure("0001", "绑定失败");
		}
		return result;
	}

	@Override
	@Transactional
	public RemoteResult login(UserAuths userAuths, String nickName) throws Exception {
		RemoteResult result = null;
		if (null == userAuths || userAuths.getIdentityType() == null) {
			LOGGER.error("调用login 传入的参数错误 登陆类型【{}】", userAuths.getIdentityType());
			result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
					BusinessCode.PARAMETERS_ERROR.getValue());
			return result;
		}
		Date now = new Date();
		if (userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_TEL
				|| userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_EMAIL
				|| userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_USERNAME) {
			if (userAuths.getIdentifier() == null || userAuths.getCredential() == null) {
				LOGGER.error("站内 调用login 传入的参数错误，无用户登陆类型，密码");
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
						BusinessCode.PARAMETERS_ERROR.getValue());
				return result;
			}
			userAuths.setYn(YnEnum.Normal.getKey());
			List<UserAuths> resList = userAuthsService.selectEntryList(userAuths);
			if (CollectionUtils.isNotEmpty(resList)) {
				LOGGER.info("调用登陆方法找到用户，返回appkey secret");
				UserAuths oldData = resList.get(0);

				AppSecret query = new AppSecret();
				query.setUserId(oldData.getUserId());
				query.setYn(YnEnum.Normal.getKey());
				List<AppSecret> appSecrets = appSecretService.selectEntryList(query);
				if (CollectionUtils.isNotEmpty(appSecrets)) {
					AppSecret secret = new AppSecret();
					secret.setUserId(appSecrets.get(0).getUserId());
					secret.setApiKey(appSecrets.get(0).getApiKey());
					secret.setSecretKey(appSecrets.get(0).getSecretKey());

					UserLogin condition = new UserLogin();
					condition.setUserId(appSecrets.get(0).getUserId());
					userLoginService.insertEntry(condition);

					result = RemoteResult.success(secret);
					return result;
				} else {
					LOGGER.error("站内 调用login找不到相关的蜜月信息");
					result = RemoteResult.failure("0001", "找不到相关的密钥信息，请联系管理员");
					return result;
				}

			}
		}

		if (userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_QQ
				|| userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_WEICHAT
				|| userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_WEIBO) {
			if (userAuths.getIdentifier() == null || StringUtils.isEmpty(nickName)) {
				LOGGER.error("第三方登录调用login 传入的参数错误，无用户第三方唯一标识");
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
						BusinessCode.PARAMETERS_ERROR.getValue());
				return result;
			}
			// 第三方登录直接更新或者新建一条记录
			UserAuths query = new UserAuths();
			query.setIdentityType(userAuths.getIdentityType());
			query.setIdentifier(userAuths.getIdentifier());
			query.setYn(YnEnum.Normal.getKey());
			List<UserAuths> resList = userAuthsService.selectEntryList(query);
			if (CollectionUtils.isNotEmpty(resList)) {
				UserAuths oldData = resList.get(0);
				oldData.setCredential(userAuths.getCredential());

				User user = new User();
				user.setId(oldData.getUserId());
				user.setNickName(nickName);
				result = registUser(user, oldData);
			} else {
				User defaultUser = new User();
				defaultUser.setNickName(nickName);
				defaultUser.setYn(YnEnum.Normal.getKey());

				UserAuths newData = new UserAuths();
				newData.setIdentityType(userAuths.getIdentityType());
				newData.setIdentifier(userAuths.getIdentifier());
				newData.setCredential(userAuths.getCredential());
				newData.setVerified(1);// 已验证
				newData.setYn(YnEnum.Normal.getKey());
				result = registUser(defaultUser, newData);
			}
		}
		return result;
	}

	@Override
	public List<User> queryFriendsByTelOrNickName(List<Long> userIds, String query ) {
		if(CollectionUtils.isEmpty(userIds) || StringUtils.isEmpty(query)){
			return null;
		}
		List<User> users = Lists.newArrayList();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userIds", userIds);
		map.put("query", query);
		
		users = userDao.queryFriendsByTelOrNickName(map);
		return users;
	}

	@Override
	@Transactional
	public RemoteResult editUserInfo(User user) throws Exception {
		RemoteResult result = null;
		if(null == user){
			return RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
		}
		User dBUser  = selectEntry(user.getId().longValue());
		
		int res = updateByKey(user);
		
		if(StringUtils.isNotEmpty(user.getNickName())){
			if(dBUser == null || StringUtils.isEmpty(dBUser.getTel())){
				result =  RemoteResult.failure(BusinessCode.NO_TEL_INFO.getCode(), BusinessCode.NO_TEL_INFO.getValue());
			}
			result = easemobAPIService.modifyIMUserNickNameWithAdminToken(dBUser.getTel(), user.getNickName());
			if(result == null || !"0000".equals(result.getCode())){
				throw new Exception("调用环信modifyIMUserNickNameWithAdminToken借口失败");
			}
		}
		
		if (res > 0 ) {
			LOGGER.info("用户编辑成功,传入的参数为：[{}]", JSON.toJSONString(user));
			result = RemoteResult.success(dBUser);
			if (null != user.getAvatarUrl()) {
				user.setAvatarUrl(IMAGEPREFIX + user.getAvatarUrl());
			}
			result.setData(user);
		} else {
			LOGGER.info("用户编辑失败,传入的参数为：[{}]", JSON.toJSONString(user));
			result = RemoteResult.failure("0001", "用户编辑失败，服务器异常");
		}
		return null;
	}

}
