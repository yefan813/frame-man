package com.frame.domain.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.frame.domain.enums.BusinessCode;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RemoteResult {
	
	private static final String SUCCESS_CODE = "0000";
	
	private String errCode;
	private String errMsg;
	private Object data;
	
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	private RemoteResult(String errCode) {
		super();
		this.errCode = errCode;
	}
	
	private RemoteResult(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	private RemoteResult(String errCode, String errMsg, Object data) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.data = data;
	}
	
	private RemoteResult(String errCode, Object data) {
		super();
		this.errCode = errCode;
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
