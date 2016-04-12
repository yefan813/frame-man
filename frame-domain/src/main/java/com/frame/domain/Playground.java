package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

public class Playground extends BaseDomain{
	private static final long serialVersionUID = -7869400238880038556L;
	
	private String id;
	private String name;
	private String address;
	private String location;
	private String tel;
	private String pcode;
	private String pname;
	private String cityCode;
	private String cityName;
	private String adCode;
	private String adName;
	private String parkingType;
	private String indoorMap;
	
	
	



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





	public String getPname() {
		return pname;
	}





	public void setPname(String pname) {
		this.pname = pname;
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





	public String getAdCode() {
		return adCode;
	}





	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}





	public String getAdName() {
		return adName;
	}





	public void setAdName(String adName) {
		this.adName = adName;
	}





	public String getParkingType() {
		return parkingType;
	}





	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
	}





	public String getIndoorMap() {
		return indoorMap;
	}





	public void setIndoorMap(String indoorMap) {
		this.indoorMap = indoorMap;
	}





	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
