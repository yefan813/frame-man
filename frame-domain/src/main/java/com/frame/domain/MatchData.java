package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

public class MatchData extends BaseDomain {
	private static final long serialVersionUID = -7869400238880038556L;

	private Long matchId;				//比赛id
	private Integer homeTeamId;			//主队id
	private String homeTeamData;		//主队数据
	private Integer guestTeamId;			//客队id
	private String guestTeamData;		//客队数据

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Integer getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(Integer homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getHomeTeamData() {
		return homeTeamData;
	}

	public void setHomeTeamData(String homeTeamData) {
		this.homeTeamData = homeTeamData;
	}

	public Integer getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(Integer guestTeamId) {
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
