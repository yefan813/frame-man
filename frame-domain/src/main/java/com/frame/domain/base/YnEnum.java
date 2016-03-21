package com.frame.domain.base;

public enum YnEnum {

	Normal(1, "启用"), 
	Deleted(-1, "已删除");

	private int key;
	private String value;

	YnEnum(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public static String getValue(int key) {
		for (YnEnum ft : YnEnum.values()) {
			if (ft.getKey() == key) {
				return ft.getValue();
			}
		}
		return null;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
