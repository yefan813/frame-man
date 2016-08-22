package com.frame.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.frame.domain.UserLogin;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.service.UserLoginService;
import com.frame.service.impl.APNSService;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/userLogin")
public class UserLoginController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

	@Resource
	private UserLoginService userLoginService;

	@Resource
	private APNSService aPNSService;

	/**
	 * 保存用户登录信息接口
	 * 
	 * @param userLogin
	 * @return
	 */
	@RequestMapping(value = "/saveUserLoginInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String saveUserLoginInfo(UserLogin userLogin) {
		RemoteResult result = null;
		try {
			if (userLogin == null || userLogin.getUserId() == null
					|| (userLogin.getLocation() == null || !userLogin.getLocation().contains(","))
					|| userLogin.getLoginTime() == null) {
				LOGGER.info("调用saveUsetLoginInfo 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			String[] loArr = userLogin.getLocation().split(",");
			if (null != loArr && loArr.length > 0) {
				userLogin.setLatitude(Double.valueOf(loArr[0]));
				userLogin.setLongitude(Double.valueOf(loArr[0]));
			}
			userLogin.setYn(YnEnum.Normal.getKey());
			if (userLoginService.insertEntry(userLogin) > 0) {
				LOGGER.info("用户定位保存成功,传入的参数为：[{}]", JSON.toJSONString(userLogin));
				result = RemoteResult.success();
			} else {
				LOGGER.info("用户定位失败,传入的参数为：[{}]", JSON.toJSONString(userLogin));
				result = RemoteResult.failure("0001", "用户定位失败，服务器异常");
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 注册用户deviceToken接口
	 * 
	 * @param userId
	 * @param deviceToken
	 * @return
	 */
	@RequestMapping(value = "/registDeviceToken", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String registDeviceToken(Integer userId, String deviceToken) {
		RemoteResult result = null;
		try {
			if (userId == null || StringUtils.isEmpty(deviceToken)) {
				LOGGER.info("调用registDeviceToken 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			UserLogin login = new UserLogin();
			login.setUserId(userId);
			login.setDeviceToken(deviceToken);

			if (userLoginService.registDeviceToken(login) > 0) {
				LOGGER.info("用户注册deviceToken成功,传入的参数为：[{}]", JSON.toJSONString(login));
				result = RemoteResult.success();
			} else {
				LOGGER.info("用户注册deviceToken失败,传入的参数为：[{}]", JSON.toJSONString(login));
				result = RemoteResult.failure("0001", "用户注册deviceToken失败，服务器异常");
			}
		} catch (Exception e) {
			LOGGER.info("调用registDeviceToken异常", e);
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 发送推送消息接口
	 * 
	 * @param msg
	 * @param deviceToken
	 * @return
	 */
	@RequestMapping(value = "/sendNotifi", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String sendNotifi(String msg, String deviceToken) {
		RemoteResult result = null;
		try {
			List<String> list = Lists.newArrayList();
			list.add(deviceToken);
			aPNSService.senPushNotification(list, msg);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
}
