package com.frame.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

/**
 * @author yefan
 *
 */
public class MatchApply extends BaseDomain {
	private static final long serialVersionUID = -7869400238880038556L;

	private Integer type;
	
	private Integer sourceIdentityId;
	
	private Integer targetIdentityId;
	
	private Date matchTime;
	
	private String matchAddress;
	
	private Integer status;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSourceIdentityId() {
		return sourceIdentityId;
	}

	public void setSourceIdentityId(Integer sourceIdentityId) {
		this.sourceIdentityId = sourceIdentityId;
	}

	public Integer getTargetIdentityId() {
		return targetIdentityId;
	}

	public void setTargetIdentityId(Integer targetIdentityId) {
		this.targetIdentityId = targetIdentityId;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public String getMatchAddress() {
		return matchAddress;
	}

	public void setMatchAddress(String matchAddress) {
		this.matchAddress = matchAddress;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
