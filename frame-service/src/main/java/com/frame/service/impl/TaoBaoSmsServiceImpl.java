package com.frame.service.impl;

import java.sql.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.frame.domain.UserValid;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.service.TaoBaoSmsService;
import com.frame.service.UserValidService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Service("taobaoSmsService")
public class TaoBaoSmsServiceImpl implements TaoBaoSmsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaoBaoSmsServiceImpl.class);

	@Value("${taobaosms_url}")
	private String TAOBAOSMS_URL;

	@Value("${taobaosms_appKey}")
	private String TAOBAOSMS_APPKEY;

	@Value("${taobaosms_secretKey}")
	private String TAOBAOSMS_SECRET;
	
	@Value("${taobaosms_freeSignName}")
	private String TAOBAOSMS_SIGNFREENAME;
	
	@Resource
	private UserValidService userValidService;

	@Override
	public RemoteResult sendValidSMS(String phoneNum,Long validDate) {
		RemoteResult msg = null;
		
		TaobaoClient client = new DefaultTaobaoClient(TAOBAOSMS_URL, TAOBAOSMS_APPKEY, TAOBAOSMS_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		String validCode = getRandomPwd();
		
		//将手机号以及生成的验证码存入数据库 后续验证时使用
		UserValid valid = new UserValid();
		valid.setTel(phoneNum);
		valid.setValidCode(validCode);
		valid.setValidDate(new Date(validDate));
		valid.setExpireTime(60l);
		valid.setYn(YnEnum.Normal.getKey());
		int res = userValidService.save(valid);
		if(res > 0){
			LOGGER.info("生成验证码 存入数据库~~~");
			req.setExtend("123456");
			req.setSmsType("normal");
			req.setSmsFreeSignName(TAOBAOSMS_SIGNFREENAME);
			req.setSmsParamString("{\"validCode\":\""+ validCode + "\"");
			req.setRecNum(phoneNum);
			req.setSmsTemplateCode("SMS_11235037");
			AlibabaAliqinFcSmsNumSendResponse rsp = null;
			try {
				rsp = client.execute(req);
				if(rsp != null && rsp.isSuccess()){
					LOGGER.info("调用sendValidSMS 发送短信成功 发送的手机号码为[{}],验证码为[{}]",phoneNum,validCode);
					msg = RemoteResult.success();
				}else{
					LOGGER.info("调用阿里大鱼发送短息失败:",rsp.getMsg());
					msg = RemoteResult.failure("0001", "调用阿里大鱼发送短息失败");
				}
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				LOGGER.error("调用阿里大鱼发送短息失败,失败的信息为:",e);
				e.printStackTrace();
			}
		}else{
			LOGGER.info("生成验证码 存入数据库 失败");
			msg = RemoteResult.failure("0001", "验证码存入数据库失败");
		}

		return msg;
	}

	public static String getRandomPwd() {
		Random rd = new Random();
		String n = "";
		int getNum;
		do {
			getNum = Math.abs(rd.nextInt()) % 10 + 48;// 产生数字0-9的随机数
			// getNum = Math.abs(rd.nextInt())%26 + 97;//产生字母a-z的随机数
			char num1 = (char) getNum;
			String dn = Character.toString(num1);
			n += dn;
		} while (n.length() < 6);
		System.out.println("随机的6位密码是：" + n);
		return n;
	}

}
