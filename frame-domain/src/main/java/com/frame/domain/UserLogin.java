package com.frame.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

/**
 * Created by garnett on 2015/11/18.
 */
public class UserLogin extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -345112426717796512L;

	/**
	 * 
	 */

	private Integer userId; // 用户id

	private String loginIp; // 用户名ip

	private Date loginTime; // 用户登录ip

	private String location; // 用户定位坐标

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
