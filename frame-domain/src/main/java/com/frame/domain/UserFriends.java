package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

/**
 * Created by garnett on 2015/11/18.
 */
public class UserFriends extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5316180681560575045L;
	public static final int STATUS_WAITING = 1;
	public static final int STATUS_ACCEPTED = 2;
	public static final int STATUS_REJECT = 3;
	public static final int STATUS_CANCLE = 4;
	

	private Long fromUserId; // 用户id

	private Long toUserId; // 登陆类型

	private Integer status; // 第三放登录唯一标识

	private Long actionUserId; // 密码

	
	
	

	public Long getFromUserId() {
		return fromUserId;
	}





	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}





	public Long getToUserId() {
		return toUserId;
	}





	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}





	public Integer getStatus() {
		return status;
	}





	public void setStatus(Integer status) {
		this.status = status;
	}





	public Long getActionUserId() {
		return actionUserId;
	}





	public void setActionUserId(Long actionUserId) {
		this.actionUserId = actionUserId;
	}





	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
