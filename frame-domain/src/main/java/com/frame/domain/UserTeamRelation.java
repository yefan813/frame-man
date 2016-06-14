package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

public class UserTeamRelation extends BaseDomain{
	private static final long serialVersionUID = -7869400238880038556L;
	private Long userId;
	private String userName;
	private Long teamId;
	private String teamName;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}






	public Long getTeamId() {
		return teamId;
	}






	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}






	public String getTeamName() {
		return teamName;
	}






	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}






	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
