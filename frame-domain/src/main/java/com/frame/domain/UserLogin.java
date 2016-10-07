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

	private Integer userId; // 用户id

	private String loginIp; // 登录ip

	private String address;
	private Double latitude;
	private Double longitude;
	private Date loginTime; // 用户登录ip

	private String location; // 用户定位坐标

	private Double myDistance;

	private String deviceToken;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getMyDistance() {
		return myDistance;
	}

	public void setMyDistance(Double myDistance) {
		this.myDistance = myDistance;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
