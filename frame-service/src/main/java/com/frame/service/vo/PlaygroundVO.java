package com.frame.service.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PlaygroundVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2668393795569587572L;
	private String id;
	private String name;
	private String address;
	private String location;
	private String tel;
	private String pcode;
	private String cityCode;
	private String cityName;
	private Long dictance;

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}


	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getDictance() {
		return dictance;
	}

	public void setDictance(Long dictance) {
		this.dictance = dictance;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
