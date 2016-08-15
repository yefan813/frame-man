package com.frame.domain.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.Team;
import com.frame.domain.User;
import com.frame.domain.base.BaseDomain;

public class JoinTeamVO extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5279850398675950961L;
	private User initiator;
	private Team targetTeam;
	private User targetUser;
	private Integer status;
	private Integer type;

	public User getInitiator() {
		return initiator;
	}

	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	public Team getTargetTeam() {
		return targetTeam;
	}

	public void setTargetTeam(Team targetTeam) {
		this.targetTeam = targetTeam;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
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
