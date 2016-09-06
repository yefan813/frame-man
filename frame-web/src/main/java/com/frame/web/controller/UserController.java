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
import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserFriends;
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
import com.frame.service.EasemobAPIService;
import com.frame.service.ImgSysService;
import com.frame.service.TaoBaoSmsService;
import com.frame.service.UserAuthsService;
import com.frame.service.UserFriendsService;
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
	private UserLoginService userLoginService;

	@Resource
	private AppSecretService appSecretService;

	@Resource
	private TaoBaoSmsService taoBaoSmsService;

	@Resource
	private ImgSysService imgSysService;
	
	@Resource
	private UserFriendsService userFriendsService;
	
	@Resource
	private EasemobAPIService easemobAPIService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	/**
	 * 编辑用户信息接口
	 * 
	 * @param user
	 * @param imgFile
	 * @return
	 */
	@RequestMapping(value = "/editUserInfo", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String editUserInfo(User user,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
		RemoteResult result = null;
		try{
		if (null == user || user.getId() == null) {
			LOGGER.info("调用editUserInfo 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		if (imgFile != null && imgFile.getSize() > 0) {
			try {
				if (imgFile.getBytes() != null && imgFile.getBytes().length > 0) {
					Result r = ImageValidate.validate4Upload(imgFile);
					if (r.isSuccess()) {
						ImgDealMsg re = imgSysService.uploadByteImg(imgFile.getBytes(), "lanqiupai");
						if (re != null && re.isSuccess()) {
							// 上传成功
							String imgUrl = (String) re.getMsg();
							// 上传成功设置template 图片路径
							user.setAvatarUrl(imgUrl);
						} else {
							// 上传文件失败，在页面提示
							result = RemoteResult.failure("0001", "头像上传失败！");
							return dealJosnP("", result);
						}
					} else {
						result = RemoteResult.failure("0001", r.getResultCode());
						return dealJosnP("", result);
					}
				}
			} catch (Exception e) {
				LOGGER.error("失败:" + e.getMessage(), e);
				result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
			}
		}
		int res = userService.updateByKey(user);
		if(StringUtils.isNotEmpty(user.getNickName())){
			User dBUser  = userService.selectEntry(user.getId().longValue());
			result = easemobAPIService.modifyIMUserNickNameWithAdminToken(dBUser.getTel(), user.getNickName());
		}
		
		if (res > 0 && "0000".equals(result.getCode())) {
			LOGGER.info("用户编辑成功,传入的参数为：[{}]", JSON.toJSONString(user));
			result = RemoteResult.success();
			if (null != user.getAvatarUrl()) {
				user.setAvatarUrl(IMAGEPREFIX + user.getAvatarUrl());
			}
			result.setData(user);
		} else {
			LOGGER.info("用户编辑失败,传入的参数为：[{}]", JSON.toJSONString(user));
			result = RemoteResult.failure("0001", "用户编辑失败，服务器异常");
		}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 
	 * 用户注册接口
	 * 
	 * @param tel
	 * @param password
	 * @param validCode
	 * @param validDate
	 * @return
	 */
	@RequestMapping(value = "/regist", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String registUser(String tel, String password, String validCode, Long validDate) {
		RemoteResult result = null;
		try {

			if (StringUtils.isEmpty(tel) || StringUtils.isEmpty(password) || StringUtils.isEmpty(validCode)
					|| (null == validDate || validDate.longValue() <= 0)) {
				LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}],验证码[{}],验证时间【{}】", tel, password, validCode,
						validDate);
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			// 判断用户是否已经注册
			User query = new User();
			query.setTel(tel);
			query.setYn(YnEnum.Normal.getKey());
			List<User> users = userService.selectEntryList(query);
			if (CollectionUtils.isNotEmpty(users)) {
				LOGGER.info("该用户已经注册，手机号为【{}】", tel);
				result = RemoteResult.failure("0001", "该手机号已经注册");
				return JSON.toJSONString(result);
			}

			// 判断出入的validCode 是否是发送时的code
			UserValid condtion = new UserValid();
			condtion.setTel(tel);
			condtion.setYn(YnEnum.Normal.getKey());
			List<UserValid> valids = userValidService.selectEntryList(condtion);

			if (CollectionUtils.isNotEmpty(valids)) {
				boolean res = false;
				for (UserValid userValid : valids) {
					res = validUserRegist(userValid, validCode, validDate);
					if (res) {
						break;
					}
				}
				if (res) {
					// 验证成功向数据库写入一条默认数据
					User defaultUser = new User();
					defaultUser.setTel(tel);
					defaultUser.setPassword(password);
					defaultUser.setNickName(RandomStrUtils.getUniqueString(6));
					defaultUser.setYn(YnEnum.Normal.getKey());

					UserAuths userAuths = new UserAuths();
					userAuths.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
					userAuths.setIdentifier(tel);
					userAuths.setCredential(password);
					userAuths.setVerified(1);// 已验证
					userAuths.setYn(YnEnum.Normal.getKey());

					result = userService.registUser(defaultUser, userAuths);
					
					
				} else {
					result = RemoteResult.failure("0002", "验证失败,验证码失效，请重新获取验证码");
				}
			} else {
				result = RemoteResult.failure("0001", "验证失败");
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 验证调用接口的时间是否超时
	 * 
	 * @param userValid
	 * @param validCode
	 * @param validDate
	 * @return
	 */
	private static boolean validUserRegist(UserValid userValid, String validCode, Long validDate) {
		boolean result = false;
		long from = userValid.getValidDate().getTime() + 60 * 1000;
		long to = validDate;
		if (from > 0 && from > to && validCode.equals(userValid.getValidCode())) {
			result = true;
		}
		return result;
	}

	/**
	 * 
	 * 获取验证码
	 * 
	 * @param tel
	 * @param validDate
	 * @return
	 */
	@RequestMapping(value = "/getValidCode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getValidCode(String tel, Long validDate) {
		RemoteResult result = null;
		try {
			if (StringUtils.isEmpty(tel) || (validDate == null || validDate <= 0)) {
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = taoBaoSmsService.sendValidSMS(tel, validDate);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 
	 * 用户登录接口
	 * 
	 * @param userAuths
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String login(UserAuths userAuths, String nickName) {
		RemoteResult result = null;
		try {
			if (null == userAuths || userAuths.getIdentityType() == null) {
				LOGGER.error("调用login 传入的参数错误 登陆类型【{}】", userAuths.getIdentityType());
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
						BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
			}
			result = userService.login(userAuths, nickName);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/bindTel", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String bindTel(User user) {
		RemoteResult result = null;
		if(null == user || user.getId() == null || user.getTel() == null){
			LOGGER.info("调用bindTel 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = userService.bindTel(user);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String logout(User user) {
		RemoteResult result = null;

		return null;
	}

	@RequestMapping(value = "/getNearByUser", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getNearByUser(Page<User> page, UserLogin userLogin) {
		RemoteResult result = null;
		try {
			if (null == userLogin || userLogin.getUserId() == null || userLogin.getLatitude() == null
					|| userLogin.getLongitude() == null) {
				LOGGER.info("调用getNearByUser 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}

			result = userService.getNearByUser(page, userLogin);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/applyFriend", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String applyFriend(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends || userFriends.getFromUserId() == null || userFriends.getToUserId() == null) {
				LOGGER.info("调用applyFriend 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = userFriendsService.applyFriend(userFriends);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/getPendingFriends", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getPendingFriends(Page<User> page, Long userId) {
		RemoteResult result = null;
		try {
			if (null == userId || userId < 0) {
				LOGGER.info("调用getPendingFriends 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = userFriendsService.getPendingFriendList(page, userId);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping(value = "/getFriendsList", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getFriendsList(Page<User> page, Long userId) {
		RemoteResult result = null;
		try {
			if (null == userId || userId < 0) {
				LOGGER.info("调用getFriendsList 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = userFriendsService.getFriendsList(page, userId);
			
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/queryFriends", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String queryFriends(Page<User> page, Long userId, String query) {
		RemoteResult result = null;
		try {
			if (StringUtils.isEmpty(query)) {
				LOGGER.info("调用queryFriends 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = userFriendsService.queryFriends(page, userId, query);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/deleteFriends", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String deleteFriends(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends.getFromUserId() || userFriends.getFromUserId() < 0 || null == userFriends.getToUserId() || userFriends.getToUserId() < 0) {
				LOGGER.info("调用deleteFriends 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			
			result = userFriendsService.deleteFriends(userFriends);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping(value = "/agreeApplyFriends", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String agreeApplyFriends(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends.getFromUserId() || userFriends.getFromUserId() < 0 || null == userFriends.getToUserId() || userFriends.getToUserId() < 0) {
				LOGGER.info("调用agreeApplyFriends 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			
			result = userFriendsService.agreeApplyFriends(userFriends);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/refuseInvitation", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String refuseInvitation(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends.getFromUserId() || userFriends.getFromUserId() < 0 || null == userFriends.getToUserId() || userFriends.getToUserId() < 0) {
				LOGGER.info("调用refuseInvitation 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			
			result = userFriendsService.refuseInvitation(userFriends);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/isFriend", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String isFriend(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends.getFromUserId() || userFriends.getFromUserId() < 0 || null == userFriends.getToUserId() || userFriends.getToUserId() < 0) {
				LOGGER.info("调用isFriend 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			
			int res = userFriendsService.check2PIsFriend(userFriends);
			if(res > 0){
				result = RemoteResult.success();
			}else {
				result = RemoteResult.failure(BusinessCode.SERVER_INTERNAL_ERROR.getCode(), BusinessCode.SERVER_INTERNAL_ERROR.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
}
