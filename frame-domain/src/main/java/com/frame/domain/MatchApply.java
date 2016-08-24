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

	public static final int DEFAULT_APPLYER_IDENTITY = -1;
	
	public static final int TYPE_PERSONLY = 1;

	public static final int TYPE_TEAM = 2;

	public static final int STATUS_APPLYING = 1; // 申请
	public static final int STATUS_AGREEMENT = 2; // 同意
	public static final int STATUS_SOURCE_CANCLE = 3; // 主队取消
	public static final int STATUS_REJECT = 4; // 拒绝
	public static final int STATUS_TARGET_CANCLE = 5; // 客队取消
	public static final int STATUS_PERSION_GOING = 6; // 客队取消
	

	private Integer type;					//比赛申请类型 1个人 2 球队

	private Integer sourceIdentityId;		//发起方id

	private Integer targetIdentityId;		//目标放id
	
	private Integer parentApplyId;			//父申请id 用于保存加入个人越球id

	private Date matchTime;					//比赛时间

	private String matchAddress;			//比赛地址

	private Double longitude;				//精度

	private Double latitude;				//维度

	private Integer status;					//申请状态
	
	private Double myDistance;		//当前坐标距离

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

	public Integer getParentApplyId() {
		return parentApplyId;
	}

	public void setParentApplyId(Integer parentApplyId) {
		this.parentApplyId = parentApplyId;
	}

	public Double getMyDistance() {
		return myDistance;
	}

	public void setMyDistance(Double myDistance) {
		this.myDistance = myDistance;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
