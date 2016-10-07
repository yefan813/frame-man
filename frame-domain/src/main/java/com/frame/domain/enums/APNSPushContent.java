package com.frame.domain.enums;

public enum APNSPushContent {
	APPLY_FRIEND("001", ""),
	ACCEPT_FRIEND("002", ""),
	REJECT_FRIEND("003", "");
	
	
	private String key;
	private String value;
	
	APNSPushContent(String key, String value){
		setKey(key);
		setValue(value);
	}
	
	public boolean isSuccess(){
		if ("0000".equals(this.key)) {
			return true;
		}
		return false;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
