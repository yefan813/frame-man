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
	
	public static final int TYPE_PERSONLY = 1;
	
	public static final int TYPE_TEAM = 2;

	public static final int STATUS_APPLYING = 1;				//申请
	public static final int STATUS_AGREEMENT = 2;				//同意
	public static final int STATUS_SOURCE_CANCLE = 3;			//主队取消
	public static final int STATUS_REJECT = 4;					//拒绝
	public static final int STATUS_TARGET_CANCLE = 5;			//客队取消
	
	
	
	

	private Integer type;
	
	private Integer sourceIdentityId;
	
	private Integer targetIdentityId;
	
	private Date matchTime;
	
	private String matchAddress;
	
	private Double longitude;
	
	private Double latitude;
	
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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
