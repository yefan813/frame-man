package com.frame.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.frame.domain.UserLogin;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.service.UserLoginService;

@Controller
@RequestMapping(value = "/userLogin")
public class UserLoginController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

	@Resource
	private UserLoginService userLoginService;

	@RequestMapping(value = "/saveUserLoginInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String saveUserLoginInfo(UserLogin userLogin) {
		RemoteResult result = null;
		if(userLogin == null || userLogin.getUserId() == null || userLogin.getLocation()== null || userLogin.getLoginTime() == null){
			LOGGER.info("调用saveUsetLoginInfo 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		userLogin.setYn(YnEnum.Normal.getKey());
		if(userLoginService.insertEntry(userLogin) > 0){
			LOGGER.info("用户定位保存成功,传入的参数为：[{}]",JSON.toJSONString(userLogin));
			result = RemoteResult.success();
		}else{
			LOGGER.info("用户定位失败,传入的参数为：[{}]",JSON.toJSONString(userLogin));
			result = RemoteResult.failure("0001", "用户定位失败，服务器异常");
		}
		return JSON.toJSONString(result);
	}

}
