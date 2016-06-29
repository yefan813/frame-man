package com.frame.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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
import com.frame.domain.User;
import com.frame.domain.UserValid;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.service.TaoBaoSmsService;
import com.frame.service.UserService;
import com.frame.service.UserValidService;


@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private UserValidService userValidService;
	
	@Resource
	private TaoBaoSmsService taoBaoSmsService;
	
	@RequestMapping(value = "/editUserInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String editUserInfo(User user,@RequestParam(value = "imgFile", required = false) MultipartFile imgFile){
		RemoteResult result = null;
		
		
		
		return null;
	}
	
	
	@RequestMapping(value = "/regist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String registUser(String tel, String password, String validCode, Long validDate){
		RemoteResult result = null;
		
		if( StringUtils.isEmpty(tel) || StringUtils.isEmpty(password) || StringUtils.isEmpty(validCode) || (null == validDate || validDate.longValue() <= 0)){
			LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}],验证码[{}],验证时间【{}】",tel,password,validCode,validDate);
			result = RemoteResult.failure("0001","传入参数错误");
			return JSON.toJSONString(result);
		}
		//判断出入的validCode 是否是发送时的code
		UserValid condtion = new UserValid();
		condtion.setTel(tel);
		condtion.setYn(YnEnum.Normal.getKey());
		List<UserValid> valids = userValidService.selectEntryList(condtion);
		if(CollectionUtils.isNotEmpty(valids)){
			boolean res = false;
			for (UserValid userValid : valids) {
				res = validUserRegist(userValid,validCode,validDate);
				if(res){
					break;
				}
			}
			if(res){
				result = RemoteResult.success();
			}else{
				result = RemoteResult.failure("0001","验证失败");
			}
			
		}else{
			result = RemoteResult.failure("0001","验证失败");
		}
		return JSON.toJSONString(result);
	}
	
	private static boolean validUserRegist(UserValid userValid, String validCode, Long validDate){
		boolean result = false;
		long from =  userValid.getValidDate().getTime() + 60 * 1000;
		long to = validDate;
		if(from > 0 && from < to && validCode.equals(userValid.getValidCode())){
			result = true;
		}
		return result;
	}
	
	@RequestMapping(value = "/getValidCode", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String registUser(String tel,Long validDate){
		RemoteResult result = null;
		if(StringUtils.isEmpty(tel) || (validDate == null || validDate <= 0)){
			result = RemoteResult.failure("0001","传入参数错误");
			return JSON.toJSONString(result);
		}
		result = taoBaoSmsService.sendValidSMS(tel,validDate);
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
