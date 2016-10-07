package com.frame.web.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 同时支持商品图片绝对路径和相对路径的展示
 * 1、数据库里面是绝对路径的直接展示
 * 2、数据库是相对路径的需要拼接
 */
public class IceUrl {
	private String domain;
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTarget(String path) {
		if (StringUtils.isEmpty(path)) return StringUtils.EMPTY;
		//外链以http开头，直接返回外链
		if (path.startsWith("http")) {
			return path;
		}
		//相对地址，拼接domain
		if (StringUtils.isNotBlank(domain)) {
			return domain + path;
		}
		//开发模式，直接返回相对路径
		return path;
	}
	
	public String getTarget(String path, String size) {
		if (StringUtils.isEmpty(path)) return StringUtils.EMPTY;
		//外链以http开头，直接返回外链
		if (path.startsWith("http")) {
			return path;
		}
		//相对地址，拼接domain
		if (StringUtils.isNotBlank(domain)) {
			return domain + path+ "_" +size;
		}
		//开发模式，直接返回相对路径
		return path;
	}	
}
