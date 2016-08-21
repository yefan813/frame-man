package com.frame.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

public class Match extends BaseDomain {
	private static final long serialVersionUID = -7869400238880038556L;

	public static Integer STATUS_CREAT = 0;
	public static Integer STATUS_GOING = 1;
	public static Integer STATUS_PAUSE = 2;
	public static Integer STATUS_END = 3;
	public static Integer STATUS_WAIT = 4;

	private Long homeTeamId;				//主队id
	private String homeTeamName;			//主队名
	private Long guestTeamId;				//客队id
	private String guestTeamName;			//客队名
	private Integer homeTeamScore;			//主队比分
	private Integer guestTeamScore;			//客队比分
	private Integer status;					//状态
	private Date matchTime;				//开始比赛时间
	private String address;					//比赛地址


	public Long getHomeTeamId() {
		return homeTeamId;
	}


	public void setHomeTeamId(Long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}


	public String getHomeTeamName() {
		return homeTeamName;
	}


	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}


	public Long getGuestTeamId() {
		return guestTeamId;
	}


	public void setGuestTeamId(Long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}


	public String getGuestTeamName() {
		return guestTeamName;
	}


	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}


	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}


	public void setHomeTeamScore(Integer homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}


	public Integer getGuestTeamScore() {
		return guestTeamScore;
	}


	public void setGuestTeamScore(Integer guestTeamScore) {
		this.guestTeamScore = guestTeamScore;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Date getMatchTime() {
		return matchTime;
	}


	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
