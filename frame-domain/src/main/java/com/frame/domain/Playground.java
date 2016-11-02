package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.alibaba.fastjson.annotation.JSONField;
import com.frame.domain.base.BaseDomain;

public class Playground extends BaseDomain {
	private static final long serialVersionUID = -7869400238880038556L;

	private String name;			//名称
	private String address;			//地址
	private String location;		//坐标经纬度
	private Double longitude;		//经度
	private Double latitude;		//维度
	private String distance;		//距离
	private Double myDistance;		//当前坐标距离
	private String tel;				//电话
	private String pcode;			//
	private String pname;			//
	private String cityCode;		//城市code
	private String cityName;		//城市名称
	private String adCode;			//
	private String adName;			//
	private String parkingType;		//停车
	private String indoorMap;		//室内
	
	@JSONField(name="id")  
	private String pId;

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
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
