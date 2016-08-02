package com.frame.web.controller;

import java.util.Date;
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
import com.frame.domain.AppSecret;
import com.frame.domain.Playground;
import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserLogin;
import com.frame.domain.UserValid;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.img.ImageValidate;
import com.frame.domain.img.ImgDealMsg;
import com.frame.domain.img.Result;
import com.frame.service.AppSecretService;
import com.frame.service.ImgSysService;
import com.frame.service.TaoBaoSmsService;
import com.frame.service.UserAuthsService;
import com.frame.service.UserLoginService;
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
	private UserAuthsService userAuthsService;
	
	@Resource
	private UserLoginService userLoginService;
	
	
	@Resource
	private AppSecretService appSecretService;
	
	@Resource
	private TaoBaoSmsService taoBaoSmsService;
	
	@Resource
	private ImgSysService imgSysService;
	
	@Value("${img.prefix}")
	private String IMAGEPREFIX;
	
	/**
	 *	编辑用户信息接口 
	 * @param user
	 * @param imgFile
	 * @return
	 */
	@RequestMapping(value = "/editUserInfo", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/json;charset=UTF-8")
	public @ResponseBody String editUserInfo(User user,@RequestParam(value = "imgFile", required = false) MultipartFile imgFile){
		RemoteResult result = null;
		if(null == user || user.getId() == null){
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
							//上传成功设置template 图片路径
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
		int res = userService.updateByKey(user);
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
	
	
	/**
	 * 
	 * 用户注册接口
	 * @param tel
	 * @param password
	 * @param validCode
	 * @param validDate
	 * @return
	 */
	@RequestMapping(value = "/regist", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String registUser(String tel, String password, String validCode, Long validDate){
		RemoteResult result = null;
		
		if( StringUtils.isEmpty(tel) || StringUtils.isEmpty(password) || StringUtils.isEmpty(validCode) || (null == validDate || validDate.longValue() <= 0)){
			LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}],验证码[{}],验证时间【{}】",tel,password,validCode,validDate);
			result = RemoteResult.failure("0001","传入参数错误");
			return JSON.toJSONString(result);
		}
		//判断用户是否已经注册
		User query = new User();
		query.setTel(tel);
		query.setYn(YnEnum.Normal.getKey());
		List<User> users = userService.selectEntryList(query);
		if(CollectionUtils.isNotEmpty(users)){
			LOGGER.info("该用户已经注册，手机号为【{}】",tel);
			result = RemoteResult.failure("0001","该手机号已经注册");
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
				
				UserAuths userAuths = new UserAuths();
				userAuths.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
				userAuths.setIdentifier(tel);
				userAuths.setCredential(password);
				userAuths.setVerified(1);//已验证
				userAuths.setYn(YnEnum.Normal.getKey());
				
				result = userService.registUser(defaultUser,userAuths);
			}else{
				result = RemoteResult.failure("0002","验证失败,验证码失效，请重新获取验证码");
			}
		}else{
			result = RemoteResult.failure("0001","验证失败");
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 验证调用接口的时间是否超时
	 * @param userValid
	 * @param validCode
	 * @param validDate
	 * @return
	 */
	private static boolean validUserRegist(UserValid userValid, String validCode, Long validDate){
		boolean result = false;
		long from =  userValid.getValidDate().getTime() + 60 * 1000;
		long to = validDate;
		if(from > 0 && from > to && validCode.equals(userValid.getValidCode())){
			result = true;
		}
		return result;
	}
	
	/**
	 * 
	 * 获取验证码
	 * @param tel
	 * @param validDate
	 * @return
	 */
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
	

	/**
	 * 
	 * 用户登录接口
	 * @param userAuths
	 * @return
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String login(UserAuths userAuths){
		RemoteResult result = null;
		if(null == userAuths || userAuths.getIdentityType() == null){
			LOGGER.error("调用login 传入的参数错误 登陆类型【{}】",userAuths.getIdentityType());
			result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
			return JSON.toJSONString(result);
		}
		Date now = new Date();
		if(userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_TEL || userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_EMAIL || userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_USERNAME){
			if(userAuths.getIdentifier() == null || userAuths.getCredential() == null){
				LOGGER.error("站内 调用login 传入的参数错误，无用户登陆类型，密码");
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
			}
			userAuths.setYn(YnEnum.Normal.getKey());
			List<UserAuths> resList = userAuthsService.selectEntryList(userAuths);
			if(CollectionUtils.isNotEmpty(resList)){
				LOGGER.info("调用登陆方法找到用户，返回appkey secret");
				UserAuths oldData = resList.get(0);
				
				AppSecret query = new AppSecret();
				query.setUserId(oldData.getUserId());
				query.setYn(YnEnum.Normal.getKey());
				List<AppSecret> appSecrets = appSecretService.selectEntryList(query);
				if(CollectionUtils.isNotEmpty(appSecrets)){
					AppSecret secret = new AppSecret();
					secret.setUserId(appSecrets.get(0).getUserId());
					secret.setApiKey(appSecrets.get(0).getApiKey());
					secret.setSecretKey(appSecrets.get(0).getSecretKey());
					
					UserLogin condition = new UserLogin();
					condition.setUserId(appSecrets.get(0).getUserId());
					userLoginService.insertEntry(condition);
					
					result = RemoteResult.success(secret);
					return JSON.toJSONString(result);
				}else{
					LOGGER.error("站内 调用login找不到信管的蜜月信息");
					result = RemoteResult.failure("0001", "找不到相关的密钥信息，请联系管理员");
					return JSON.toJSONString(result);
				}
				
			}
		}
		
		if(userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_QQ || userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_WEICHAT || userAuths.getIdentityType() == UserAuths.IDENTITY_RYPE_WEIBO){
			if(userAuths.getIdentifier() == null){
				LOGGER.error("第三方登录调用login 传入的参数错误，无用户第三方唯一标识");
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
			}
			//第三方登录直接更新或者新建一条记录
			UserAuths query = new UserAuths();
			query.setIdentityType(userAuths.getIdentityType());
			query.setIdentifier(userAuths.getIdentifier());
			List<UserAuths> resList = userAuthsService.selectEntryList(userAuths);
			if(CollectionUtils.isNotEmpty(resList)){
				UserAuths oldData = resList.get(0);
				oldData.setCredential(userAuths.getCredential());
				
				User user = new User();
				user.setId(oldData.getUserId());
				result = userService.registUser(user,oldData);
			}else{
				User defaultUser = new User();
				defaultUser.setNickName(RandomStrUtils.getUniqueString(6));
				defaultUser.setYn(YnEnum.Normal.getKey());
				
				UserAuths newData = new UserAuths();
				newData.setIdentityType(userAuths.getIdentityType());
				newData.setIdentifier(userAuths.getIdentifier());
				newData.setCredential(userAuths.getCredential());
				newData.setVerified(1);//已验证
				newData.setYn(YnEnum.Normal.getKey());
				result = userService.registUser(defaultUser,newData);
			}
			
		}
		return JSON.toJSONString(result);
	}
	
	
	
	@RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String logout(User user){
		RemoteResult result = null;
		
		
		
		return null;
	}
	
	@RequestMapping(value = "/getNearByUser", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getNearByUser(Page<User> page,UserLogin userLogin) {
		RemoteResult result = null;
		if (null == userLogin || userLogin.getUserId() == null || userLogin.getLatitude() == null || userLogin.getLongitude() == null) {
			LOGGER.info("调用getNearByUser 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		
		result = userService.getNearByUser(page,userLogin);
		return JSON.toJSONString(result);
	}
}
