package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

public class JoinTeam extends BaseDomain {
	private static final long serialVersionUID = -7869400238880038556L;

	public static Integer STATUS_APPLYING_JOIN = 1;
	public static Integer STATUS_INVENT_JOIN = 2;
	public static Integer STATUS_AGREEMENT = 3;
	public static Integer STATUS_REJECT = 4;
	public static Integer STATUS_INVALID = 5;

	public static Integer TYPE_INVENT = 1;
	public static Integer TYPE_APPLY = 2;

	private Long initiator;
	private Long teamId;
	private Long userId;
	private Integer status;
	private Integer type;

	public Long getInitiator() {
		return initiator;
	}

	public void setInitiator(Long initiator) {
		this.initiator = initiator;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
