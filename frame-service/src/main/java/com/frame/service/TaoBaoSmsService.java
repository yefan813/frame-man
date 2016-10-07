package com.frame.service;

import com.frame.domain.common.RemoteResult;

public interface TaoBaoSmsService {
	/**
	 * 发短信接口
	 * @param phoneNum
	 * @param validDate
	 * @return
	 */
	public RemoteResult sendValidSMS(String phoneNum, Long validDate);
}
