package com.frame.domain.enums;

public enum BusinessCode {

	CREATE_ORDER_SUCCESS("0000", 10000, "创建订单成功", null),
	USER_ID_IS_NULL("880001", 10001, "用户ID为空", null),
	NOT_IN_TARGET_AREA("880002", 10002, "不在指定区域", null),
	PRODUCT_ID_IS_NULL("880003", 10003, "产品ID为空", null);
	
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
