package com.frame.service.cache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RedisObject {
	public static final int TYPE_USER = 1;
	public static final int TYPE_MAC = 2;
	public static final int TYPE_INST = 3;
	private String name;
	private String key;
	private String field;
	private String value;
	private String server;
	public RedisObject(String name, String key, String field) {
		super();
		this.name = name;
		this.key = key;
		this.field = field;
	}
	public RedisObject(String name, String key, String field, String value, String server) {
		super();
		this.name = name;
		this.key = key;
		this.field = field;
		this.value = value;
		this.server = server;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String toString() {
		StringBuffer str = new StringBuffer(this.getClass().getSimpleName() + ":[");
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field f : fields) {
			String fieldName = f.getName();
			String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			try {
				Method getter = this.getClass().getDeclaredMethod(getterName);
				Object value = getter.invoke(this);
				str.append(fieldName).append("=").append(value).append(", ");
			} catch (Exception e) {
				continue;
			}
		}
		if (str.length() > 1) {
			str.delete(str.length() - 2, str.length());
			str.append("]");
		}
		return str.toString();
	}
}
