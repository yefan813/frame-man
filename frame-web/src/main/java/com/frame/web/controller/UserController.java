package com.frame.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import com.frame.domain.img.ImageValidate;
import com.frame.domain.img.ImgDealMsg;
import com.frame.domain.img.Result;
import com.frame.service.ImgSysService;
import com.frame.service.TaoBaoSmsService;
import com.frame.service.UserService;
import com.frame.service.UserValidService;
import com.frame.service.utils.RandomStrUtils;


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
	
	@Resource
	private ImgSysService imgSysService;
	
	@Value("${img.prefix}")
	private String IMAGEPREFIX;
	
	@RequestMapping(value = "/editUserInfo", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/json;charset=UTF-8")
	public @ResponseBody String editUserInfo(User user,@RequestParam(value = "imgFile", required = false) MultipartFile imgFile){
		RemoteResult result = null;
		if(null == user){
			LOGGER.info("调用editUserInfo 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		if(imgFile != null && imgFile.getSize() > 0){
			try {
				if (imgFile.getBytes() != null && imgFile.getBytes().length > 0) {
					Result r = ImageValidate.validate4Upload(imgFile);
					if (r.isSuccess()) {
						ImgDealMsg re = imgSysService.uploadByteImg(imgFile.getBytes(), "lanqiupai");
						if (re != null && re.isSuccess()) {
							// 上传成功
							String imgUrl = (String) re.getMsg();
							//上床成功设置template 图片路径
							user.setAvatarUrl(imgUrl);
						} else { 
							// 上传文件失败，在页面提示
							result = RemoteResult.failure("0001","头像上传失败！");
							return dealJosnP("", result);
						}
					} else {
						result = RemoteResult.failure("0001",r.getResultCode());
						return dealJosnP("", result);
					}
				}
			}catch(Exception e){
				LOGGER.error("失败:" + e.getMessage(), e);
				result = RemoteResult.failure("0001","操作失败:" + e.getMessage());
			}
		}
		int res = userService.updateByTel(user);
		if(res > 0){
			LOGGER.info("用户编辑成功,传入的参数为：[{}]",JSON.toJSONString(user));
			result = RemoteResult.success();
			if(null != user.getAvatarUrl()){
				user.setAvatarUrl(IMAGEPREFIX + user.getAvatarUrl());
			}
			result.setData(user);
		}else{
			LOGGER.info("用户编辑失败,传入的参数为：[{}]",JSON.toJSONString(user));
			result = RemoteResult.failure("0001", "用户编辑失败，服务器异常");
		}
		
		return JSON.toJSONString(result);
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
				//验证成功向数据库写入一条默认数据
				User defaultUser = new User();
				defaultUser.setTel(tel);
				defaultUser.setPassword(password);
				defaultUser.setNickName(RandomStrUtils.getUniqueString(6));
				defaultUser.setYn(YnEnum.Normal.getKey());
				if(userService.insertEntry(defaultUser) > 0){
					result = RemoteResult.success();
					result.setData(defaultUser);
				}else{
					LOGGER.info("用户验证成功，插入临时用户数据失败");
					result = RemoteResult.failure("0001", "系统异常");
				}
			}else{
				result = RemoteResult.failure("0002","验证失败,验证码失效，请重新获取验证码");
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
		if(from > 0 && from > to && validCode.equals(userValid.getValidCode())){
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
