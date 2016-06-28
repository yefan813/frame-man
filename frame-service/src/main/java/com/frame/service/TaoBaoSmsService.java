package com.frame.service;

import com.frame.domain.common.RemoteResult;

public interface TaoBaoSmsService {
	public RemoteResult sendValidSMS(String phoneNum,Long validDate);
}
