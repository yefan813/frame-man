package com.frame.domain;


import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

/**
 * Created by garnett on 2015/11/18.
 */
public class AppSecret extends BaseDomain {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4402189344781237566L;

	private Integer userId; 				// 用户id

	private String userName; 				//用户名

	private String apiKey; 					// apikey

	private String secretKey; 				// secret


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getApiKey() {
		return apiKey;
	}


	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	public String getSecretKey() {
		return secretKey;
	}


	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
