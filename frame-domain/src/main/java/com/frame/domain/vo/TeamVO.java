package com.frame.domain.vo;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.User;
import com.frame.domain.base.BaseQuery;

public class TeamVO extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2928924502135826805L;
	private Integer id;
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
	 * 胜利次数
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

	private String teamDesc;

	private String createUserName;
	private Long createUser;
	private String address;
	private String location;
	private String feature;
	private Date created;
	private Date modified;
	private List<User> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTeamDesc() {
		return teamDesc;
	}

	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
