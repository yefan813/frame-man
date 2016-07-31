package com.frame.domain.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.Team;

/**
 * @author yefan
 *
 */
public class TeamApplyRecordVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4665532042979313802L;

	private Integer id;
	
	private Integer type;

	private Team sourceIdentity;

	private Team targetIdentity;

	private Date matchTime;

	private String matchAddress;

	private Integer status;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Team getSourceIdentity() {
		return sourceIdentity;
	}

	public void setSourceIdentity(Team sourceIdentity) {
		this.sourceIdentity = sourceIdentity;
	}

	public Team getTargetIdentity() {
		return targetIdentity;
	}

	public void setTargetIdentity(Team targetIdentity) {
		this.targetIdentity = targetIdentity;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
