package com.frame.domain.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.frame.domain.enums.BusinessCode;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RemoteResult {
	
	private static final String SUCCESS_CODE = "0000";
	
	private String code;
	private String msg;
	private Object data;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	private RemoteResult(String errCode) {
		super();
		this.code = errCode;
	}
	
	private RemoteResult(String errCode, String errMsg) {
		super();
		this.code = errCode;
		this.msg = errMsg;
	}
	
	private RemoteResult(String errCode, String errMsg, Object data) {
		super();
		this.code = errCode;
		this.msg = errMsg;
		this.data = data;
	}
	
	private RemoteResult(String errCode, Object data) {
		super();
		this.code = errCode;
		this.data = data;
	}
	
	public static RemoteResult failure(String code){
		return new RemoteResult(code);
	}
	
	public static RemoteResult result(BusinessCode businessCode){
		return new RemoteResult(businessCode.getCode(), businessCode.getValue());
	}
	
	public static RemoteResult result(BusinessCode businessCode, Object data){
		return new RemoteResult(businessCode.getCode(), businessCode.getValue(), data);
	}
	
	public static RemoteResult failure(String code, String msg){
		return new RemoteResult(code, msg);
	}
	
	public static RemoteResult success(){
		return new RemoteResult(SUCCESS_CODE);
	}
	
	public static RemoteResult success(Object data){
		return new RemoteResult(SUCCESS_CODE, data);
	}
}
