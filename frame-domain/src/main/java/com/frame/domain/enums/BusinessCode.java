package com.frame.domain.enums;

public enum BusinessCode {

	SERVER_INTERNAL_ERROR("0000", 10000, "服务器内部错误", null),
	SUCCESS("0001", 10000, "取数据成功", null);
	
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
