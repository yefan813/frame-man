package com.frame.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Team;
import com.frame.domain.User;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.service.UserService;


@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/editUserInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String editUserInfo(User user,@RequestParam(value = "imgFile", required = false) MultipartFile imgFile){
		RemoteResult result = null;
		
		
		
		return null;
	}
	
	
	@RequestMapping(value = "/regist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String registUser(User user){
		RemoteResult result = null;
		if(null == user){
			result = RemoteResult.failure("0001","传入参数错误");
			return JSON.toJSONString(result);
		}
		if( StringUtils.isEmpty(user.getTel()) || StringUtils.isEmpty(user.getPassword())){
			LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}]",user.getTel(),user.getPassword());
			result = RemoteResult.failure("0001","传入参数错误");
			return JSON.toJSONString(result);
		}
		
		
		return JSON.toJSONString(result);
	}
	
	

	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String login(User user){
		RemoteResult result = null;
		
		
		
		return null;
	}
	
	
	
	@RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String logout(User user){
		RemoteResult result = null;
		
		
		
		return null;
	}
	
	
	
	
	
}
