package com.frame.domain.vo;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

/**
 * @author yefan
 *
 */
public class MatchVO extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7700319957579609642L;

	private Object homeTeam;

	private Object guestTeam;

	private Date matchTime;

	private String address;

	private Double longitude;

	private Double latitude;

	private Integer status;

	private Integer homeTeamScore;

	private Integer guestTeamScore;


	public Object getHomeTeam() {
		return homeTeam;
	}


	public void setHomeTeam(Object homeTeam) {
		this.homeTeam = homeTeam;
	}


	public Object getGuestTeam() {
		return guestTeam;
	}


	public void setGuestTeam(Object guestTeam) {
		this.guestTeam = guestTeam;
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


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
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


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
