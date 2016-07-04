package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

/**
 * Created by garnett on 2015/11/18.
 */
public class UserAuths extends BaseDomain {

	private static final long serialVersionUID = 6723005414893784943L;

	private Integer userId; // 用户id

	private Integer identityType; // 登陆类型

	private String identity; // 第三放登录唯一标识

	private String credential; // 密码

	private Integer verified; // 是否已经验证 用于手机邮箱验证标识

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public Integer getVerified() {
		return verified;
	}

	public void setVerified(Integer verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
