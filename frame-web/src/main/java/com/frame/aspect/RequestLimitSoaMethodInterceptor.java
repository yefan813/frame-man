package com.frame.aspect;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.cusAnnotion.RequestLimit;
import com.frame.service.utils.MyCacheUtil;
import com.frame.web.utils.NetworkUtil;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016年11月12日 类说明
 */
public class RequestLimitSoaMethodInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyCacheUtil.class);

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object reslut = null;
		// 拦截的实体类
		Object target = pjp.getTarget();
		// 拦截的方法名称
		String methodName = pjp.getSignature().getName();
		// 拦截的放参数类型
		Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();
		Class[] clazzs = target.getClass().getInterfaces();
		// 1.获取类
		Class clazz = target.getClass();
		if (clazzs != null && clazzs.length > 0) {
			clazz = clazzs[0];
		}
		// 2.获取方法
		Method m = clazz.getMethod(methodName, parameterTypes);
		// 3.获取request、callback
		Object[] args = pjp.getArgs();
		HttpServletRequest request = null;
		if (args != null && args.length > 0) {
			if (args[0] instanceof HttpServletRequest) {
				request = (HttpServletRequest) args[0];
				if (request != null) {
					String callback = request.getParameter("callback");
				}
			}
		}
		// RequestLimit判断
		String reequestLimitRes = this.RequestLimitCheck(m, request);
		if ("fail".equals(reequestLimitRes)) {
			return JSON.toJSONString(RemoteResult.failure("0001", "你访问的太频繁请稍后再试."));// 返回值改为自己的格式
		} else {
			reslut = pjp.proceed();
		}

		return reslut;
	}

	private String RequestLimitCheck(Method m, HttpServletRequest request) throws IOException {
		// ip、user_phone+uri 两个维度的访问限制
		if (m != null && m.isAnnotationPresent(RequestLimit.class)) {
			RequestLimit requestLimit = m.getAnnotation(RequestLimit.class);
			// 失效时间、访问次数
			int ipTime = (int) (requestLimit.ipTime() / 1000);
			int ipCount = requestLimit.ipCount();
			int uriTime = (int) (requestLimit.uriTime() / 1000);
			int uriCount = requestLimit.uriCount();

			// ip、user_phone、uri
			String ip_key = NetworkUtil.getIpAddress(request);// ip:获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
			String user_phone = request.getParameter("tel");// 手机号
			String uri = request.getRequestURI().toString();// uri:如果字符串太长,可以做个映射
			String user_uri_key = user_phone + uri;
			// 先从缓存中(以Memcached为例)获取两个key对应的value值,value值为访问限制次数
			List<String> keyCollections = new ArrayList<String>();
			keyCollections.add(ip_key);
			keyCollections.add(user_uri_key);
			Map<String, String> valueMap = MyCacheUtil.getLimitValue(keyCollections);// 一次读取出多个key_value
			Integer ipNumCache = 0;// ip访问次数
			Integer userUriNumCache = 0;// 手机号+uri访问次数
			if (valueMap != null && valueMap.size() > 0) {
				String ipNumCacheFlag = valueMap.get(ip_key);
				if (StringUtils.isNotBlank(ipNumCacheFlag)) {
					ipNumCache = Integer.parseInt(ipNumCacheFlag);
				}
				String userUriNumCacheFlag = valueMap.get(user_uri_key);
				if (StringUtils.isNotBlank(userUriNumCacheFlag)) {
					userUriNumCache = Integer.parseInt(userUriNumCacheFlag);
				}
			}
			// ip限制判断
			if (ipNumCache == 0) {
				MyCacheUtil.setLimit(ip_key, 1, ipTime);
			} else if (ipNumCache >= ipCount) {
				LOGGER.info("request_limit:用户IP[" + ip_key + "],超过了限定的次数[" + ipCount + "]");
				return "fail";
			} else {
				MyCacheUtil.incrLimit(ip_key, 1);// 自增
			}
			// user_phone、uri限制判断
			if (userUriNumCache == 0) {
				MyCacheUtil.setLimit(user_uri_key, 1, uriTime);
			} else if (userUriNumCache >= uriCount) {
				LOGGER.info("request_limit:用户手机号[" + user_phone + "],访问地址[" + uri + "],超过了限定的次数[" + uriCount + "]");
				return "fail";
			} else {
				MyCacheUtil.incrLimit(user_uri_key, 1);
			}
		}
		return "success";
	}
}
