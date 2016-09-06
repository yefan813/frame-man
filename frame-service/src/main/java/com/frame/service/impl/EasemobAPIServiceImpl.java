package com.frame.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.chat.api.IMUserAPI;
import com.frame.chat.comm.ClientContext;
import com.frame.chat.comm.EasemobRestAPIFactory;
import com.frame.chat.comm.body.IMUserBody;
import com.frame.chat.comm.body.IMUsersBody;
import com.frame.chat.comm.body.ModifyNicknameBody;
import com.frame.chat.comm.body.ResetPasswordBody;
import com.frame.chat.comm.body.UserNamesBody;
import com.frame.chat.comm.wrapper.BodyWrapper;
import com.frame.chat.comm.wrapper.ResponseWrapper;
import com.frame.domain.User;
import com.frame.domain.common.RemoteResult;
import com.frame.service.EasemobAPIService;
import com.google.common.collect.Lists;


@Service("easemobAPIService")
public class EasemobAPIServiceImpl implements EasemobAPIService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EasemobAPIServiceImpl.class);

	
	
	@Override
	public RemoteResult createNewIMUserSingle(User user) {
		if(user == null || user.getTel() == null 
			|| user.getPassword() == null || StringUtils.isEmpty(user.getNickName())){
			LOGGER.error("调用环信createNewIMUserSingle传入参数错误");
			return RemoteResult.failure("0001", "调用环信createNewIMUserSingle传入参数错误");
		}
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		
		IMUserBody body = new IMUserBody(user.getTel(), user.getPassword(), user.getNickName());
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.createNewIMUserSingle(body);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}
	
	@Override
	public RemoteResult createNewIMUserBatch(List<User> body) {
		if(CollectionUtils.isEmpty(body)){
			LOGGER.error("调用环信createNewIMUserBatch传入参数错误");
			return RemoteResult.failure("0001", "调用环信createNewIMUserBatch传入参数错误");
		}
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		
		List<IMUserBody> users = new ArrayList<IMUserBody>();
		for (User user : body) {
			if(user == null || user.getTel() == null 
					|| user.getPassword() == null || StringUtils.isEmpty(user.getNickName())){
					LOGGER.error("调用环信createNewIMUserBatch传入参数错误");
					continue;
				}
			users.add(new IMUserBody(user.getTel(), "123456", user.getNickName()));
		}
		BodyWrapper usersBody = new IMUsersBody(users);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.createNewIMUserBatch(usersBody);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public User getIMUsersByUserName(String userName) {
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.getIMUsersByUserName(userName);
		
		User user = null;
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			user = new User();
		}else{
			
		}
		
		
		return user;
	}

	@Override
	public List<User> getIMUsersBatch(Long limit, String cursor) {
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.getIMUsersBatch(limit, cursor);
		List<User> users = Lists.newArrayList();
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			
		}
		
		
		return users;
	}

	@Override
	public RemoteResult deleteIMUserByUserName(String userName) {
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.deleteIMUserByUserName(userName);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public RemoteResult deleteIMUserBatch(Long limit) {
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.deleteIMUserBatch(limit);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public RemoteResult modifyIMUserPasswordWithAdminToken(String userName, String newpassword) {
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(newpassword)){
			return RemoteResult.failure("0001", "调用modifyIMUserPasswordWithAdminToken借口失败");
		}
		
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		
		ResetPasswordBody resetPasswordBody = new ResetPasswordBody(newpassword);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.modifyIMUserPasswordWithAdminToken(userName, resetPasswordBody);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public RemoteResult modifyIMUserNickNameWithAdminToken(String userName, String nickname) {
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(nickname)){
			return RemoteResult.failure("0001", "调用modifyIMUserNickNameWithAdminToken借口失败");
		}
		
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		
		ModifyNicknameBody body = new ModifyNicknameBody(nickname);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.modifyIMUserNickNameWithAdminToken(userName, body);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public RemoteResult addFriendSingle(String userName, String friendName) {
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(friendName)){
			return RemoteResult.failure("0001", "调用modifyIMUserNickNameWithAdminToken借口失败");
		}
		
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.addFriendSingle(userName, friendName);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public RemoteResult deleteFriendSingle(String userName, String friendName) {
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(friendName)){
			return RemoteResult.failure("0001", "调用modifyIMUserNickNameWithAdminToken借口失败");
		}
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.deleteFriendSingle(userName, friendName);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public List<User> getFriends(String userName) {
		if(StringUtils.isEmpty(userName)){
			return null;
		}
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.getFriends(userName);
		List<User> users = Lists.newArrayList();
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			//
			
			return users;
		}else{
			return users;
		}
	}

	@Override
	public List<User> getBlackList(String userName) {
		if(StringUtils.isEmpty(userName)){
			LOGGER.error("0001", "调用getBlackList借口失败");
			return null;
		}
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.getBlackList(userName);
		List<User> users = Lists.newArrayList();
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			//
			
			return users;
		}else{
			return users;
		}
	}

	@Override
	public RemoteResult addToBlackList(String userName, List<User> users) {
		if(StringUtils.isEmpty(userName) || CollectionUtils.isEmpty(users) ){
			LOGGER.error("0001", "调用addToBlackList借口失败");
			return null;
		}
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		List<String> names = Lists.newArrayList();
		for(User user : users){
			names.add(user.getTel());
		}
		UserNamesBody userNamesBody = new UserNamesBody((String[]) names.toArray());
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.addToBlackList(userName, userNamesBody);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public RemoteResult removeFromBlackList(String userName, String blackListName) {
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(blackListName)){
			LOGGER.error("0001", "调用removeFromBlackList借口失败");
			return null;
		}
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ResponseWrapper responseWrapper = (ResponseWrapper) userAPI.removeFromBlackList(userName, blackListName);
		if(null != responseWrapper && responseWrapper.getResponseStatus() == 200){
			return RemoteResult.success();
		}else{
			return RemoteResult.failure("0001", "调用环信借口失败");
		}
	}

	@Override
	public Object getIMUserStatus(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getOfflineMsgCount(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSpecifiedOfflineMsgStatus(String userName, String msgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteResult deactivateIMUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteResult activateIMUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean disconnectIMUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getIMUserAllChatGroups(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getIMUserAllChatRooms(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
