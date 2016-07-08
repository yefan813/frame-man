package com.frame.web.interceptor;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.frame.domain.AppSecret;
import com.frame.domain.base.YnEnum;
import com.frame.service.AppSecretService;

/**
 * 基础操作拦截器.
 * 
 * @date 2015/12/03
 */
public class BaseInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);
	private String key; // 与passport系统交换公钥

	@Resource
	private AppSecretService appSecretService;

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String apiKey = request.getParameter("apiKey");
		String timestamp = request.getParameter("timestamp");
		String sign = request.getParameter("sign");

		if (StringUtils.isEmpty(apiKey) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(sign)) {
			logger.error("接口过滤器调用，传入的参数错误，传入的参数为apiKey:【{}】，timestamp【{}】，sign：【{}】", apiKey, timestamp, sign);
			return false;
		}
		Date now = new Date();
		long appDate = Long.valueOf(timestamp);
		long afteDate = appDate + 60 * 1000;
		Date appRequestTime = new Date(afteDate);

		if (now.after(appRequestTime)) {// 超过60s 请求无效
			logger.error("请求超过60s，请求无效,请求来自apiKey：【{}】", apiKey);
			return false;
		}

		AppSecret appSecret = new AppSecret();
		appSecret.setApiKey(apiKey);
		appSecret.setYn(YnEnum.Normal.getKey());
		List<AppSecret> resList = appSecretService.selectEntryList(appSecret);
		if (CollectionUtils.isEmpty(resList)) {
			logger.error("没找到相关的apikey");
			return false;
		} else {
			logger.error("找到相关的apikey,验证参数是否被篡改");
			appSecret = resList.get(0);
		}

		// 对参数名进行字典排序
		Map paramMap = new HashMap(request.getParameterMap());
		paramMap.remove("sign");
		Map<String, String> resMap = transToMAP(paramMap);
		String codes = getSignature(resMap, appSecret.getSecretKey());

		if (sign.equals(codes)) {
			logger.error("参数验证成功！");
			return true;
		} else {
			logger.error("参数验证失败！服务器传入的sign：【{}】， 服务器的sign：【{}】", sign, codes);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * 签名生成算法
	 * 
	 * @param HashMap<String,String>
	 *            params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String
	 *            secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(Map<String, String> params, String secret) throws IOException {
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entrys = sortedParams.entrySet();

		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=").append(param.getValue());
		}
		basestring.append(secret);

		// 使用MD5对待签名串求签
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}

		// 将MD5输出的二进制结果转换为小写的十六进制

		// for (int i = 0; i < bytes.length; i++) {
		// String hex = Integer.toHexString(bytes[i] & 0xFF);
		// if (hex.length() == 1) {
		// sign.append("0");
		// }
		// sign.append(hex);
		// }
		return byteArrayToHexString(bytes);
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	private static Map<String, String> transToMAP(Map parameterMap) {
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator entries = parameterMap.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
