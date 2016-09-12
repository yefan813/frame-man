package com.frame.domain;


import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

/**
 * Created by garnett on 2015/11/18.
 */
public class UserValid extends BaseDomain {

	private static final long serialVersionUID = 6723005414893784943L;

	private String tel; // 姓名

	private String validCode; // 验证码

	private Date validDate; // 验证时间

	private Long expireTime; // 过期时间

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
