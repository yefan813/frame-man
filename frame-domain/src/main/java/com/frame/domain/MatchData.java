package com.frame.domain;

import java.sql.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

public class MatchData extends BaseDomain {
	private static final long serialVersionUID = -7869400238880038556L;

	private Long matchId;				//比赛id
	private Long homeTeamId;			//主队id
	private String homeTeamData;		//主队数据
	private Long guestTeamId;			//客队id
	private String guestTeamData;		//客队数据


	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Long getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(Long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getHomeTeamData() {
		return homeTeamData;
	}

	public void setHomeTeamData(String homeTeamData) {
		this.homeTeamData = homeTeamData;
	}

	public Long getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(Long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public String getGuestTeamData() {
		return guestTeamData;
	}

	public void setGuestTeamData(String guestTeamData) {
		this.guestTeamData = guestTeamData;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
