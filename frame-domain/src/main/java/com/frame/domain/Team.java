package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

public class Team extends BaseDomain{
	private static final long serialVersionUID = -7869400238880038556L;
	
	/**
	 * 球队当前人数
	 */
	private Integer currentCount;
	/**
	 * 球队需要人数
	 */
	private Integer peopleCount;
	/**
	 * 球队状态
	 */
	private Integer status;
	/**
	 * 失败次数
	 */
	private Integer lostTimes;
	/**
	 *胜利次数
	 */
	private Integer winTimes;
	/**
	 * 图片地址
	 */
	private String imgUrl;
	/**
	 * 球队名称
	 */
	private String name;
	

	public Integer getCurrentCount() {
		return currentCount;
	}


	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}




	public Integer getPeopleCount() {
		return peopleCount;
	}




	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}




	public Integer getStatus() {
		return status;
	}




	public void setStatus(Integer status) {
		this.status = status;
	}




	public Integer getLostTimes() {
		return lostTimes;
	}




	public void setLostTimes(Integer lostTimes) {
		this.lostTimes = lostTimes;
	}




	public Integer getWinTimes() {
		return winTimes;
	}




	public void setWinTimes(Integer winTimes) {
		this.winTimes = winTimes;
	}




	public String getImgUrl() {
		return imgUrl;
	}




	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
