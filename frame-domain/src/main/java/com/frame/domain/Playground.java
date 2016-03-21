package com.frame.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.frame.domain.base.BaseDomain;

public class Playground extends BaseDomain{
	private static final long serialVersionUID = -7869400238880038556L;

	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
