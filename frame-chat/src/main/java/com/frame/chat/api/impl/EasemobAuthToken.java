package com.frame.chat.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.chat.api.AuthTokenAPI;
import com.frame.chat.api.EasemobRestAPI;
import com.frame.chat.comm.body.AuthTokenBody;
import com.frame.chat.comm.constant.HTTPMethod;
import com.frame.chat.comm.helper.HeaderHelper;
import com.frame.chat.comm.wrapper.BodyWrapper;
import com.frame.chat.comm.wrapper.HeaderWrapper;

public class EasemobAuthToken extends EasemobRestAPI implements AuthTokenAPI{
	
	public static final String ROOT_URI = "/token";
	
	private static final Logger log = LoggerFactory.getLogger(EasemobAuthToken.class);
	
	@Override
	public String getResourceRootURI() {
		return ROOT_URI;
	}

	public Object getAuthToken(String clientId, String clientSecret) {
		String url = getContext().getSeriveURL() + getResourceRootURI();
		BodyWrapper body = new AuthTokenBody(clientId, clientSecret);
		HeaderWrapper header = HeaderHelper.getDefaultHeader();
		
		return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
	}
}
