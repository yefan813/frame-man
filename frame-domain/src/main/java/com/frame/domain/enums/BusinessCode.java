package com.frame.domain.enums;

public enum BusinessCode {
	FAILED("999", 999, "获取数据失败", null),
	SUCCESS("0000", 10000, "取数据成功", null),
	SERVER_INTERNAL_ERROR("0001", 10001, "服务器内部错误", null),
	PARAMETERS_ERROR("0002", 10002, "传递参数错误", null),
	NO_TEL_INFO("0004", 10004, "没有电话号码不能注册环信", null),
	NO_REGIST("0006", 10006, "此用户没有注册", null),
	NO_FRIEND("0005", 10005, "两人不是好友关系", null),
	IS_FRIEND("0007", 10007, "两人是好友关系", null),
	NO_RESULTS("0003", 10003, "无返回数据", null),
	NO_MATCH_DATA("0008", 10008, "未找到匹配的数据", null);
	
	
	private String code;
	private int key;
	private String value;
	private Object data;
	
	BusinessCode(String code, int key, String value, Object data){
		setCode(code);
		setKey(key);
		setValue(value);
		setData(data);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isSuccess(){
		if ("0000".equals(this.code)) {
			return true;
		}
		return false;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
