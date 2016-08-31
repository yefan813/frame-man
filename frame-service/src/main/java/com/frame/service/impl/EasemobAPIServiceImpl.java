package com.frame.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.chat.api.IMUserAPI;
import com.frame.chat.comm.ClientContext;
import com.frame.chat.comm.EasemobRestAPIFactory;
import com.frame.chat.comm.body.IMUserBody;
import com.frame.chat.comm.wrapper.ResponseWrapper;
import com.frame.service.EasemobAPIService;


@Service("easemobAPIService")
public class EasemobAPIServiceImpl implements EasemobAPIService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EasemobAPIServiceImpl.class);

	private static EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
	private static IMUserAPI userAPI = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
	
	@Override
	public ResponseWrapper createNewIMUserSingle(IMUserBody user) {
		return (ResponseWrapper) userAPI.createNewIMUserSingle(user);
	}

	@Override
	public ResponseWrapper getIMUsersByUserName(String userName) {
		return (ResponseWrapper) userAPI.getIMUsersByUserName(userName);
	}

	@Override
	public List<ResponseWrapper> getIMUsersBatch(Long limit, String cursor) {
		return null;
	}

	@Override
	public ResponseWrapper deleteIMUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteIMUserBatch(Long limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object modifyIMUserPasswordWithAdminToken(String userName, Object payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object modifyIMUserNickNameWithAdminToken(String userName, Object payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object addFriendSingle(String userName, String friendName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteFriendSingle(String userName, String friendName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getFriends(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getBlackList(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object addToBlackList(String userName, Object payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeFromBlackList(String userName, String blackListName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getIMUserStatus(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getOfflineMsgCount(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSpecifiedOfflineMsgStatus(String userName, String msgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deactivateIMUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object activateIMUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object disconnectIMUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getIMUserAllChatGroups(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getIMUserAllChatRooms(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
