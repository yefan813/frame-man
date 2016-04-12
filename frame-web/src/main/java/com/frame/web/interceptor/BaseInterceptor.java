package com.frame.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.frame.service.utils.RSAUtils;

/**
 * 基础操作拦截器.
 * @date 2015/12/03
 */
public class BaseInterceptor implements HandlerInterceptor  {
	private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);
	private String key;	// 与passport系统交换公钥

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String encryptStr = request.getParameter("token");
		try {
			String tokenStr = RSAUtils.decryptByPublicKey(encryptStr, key);
			WeixinPassport weixinPassport = JSON.parseObject(tokenStr, WeixinPassport.class);
			// TODO log
			logger.info(request.getRequestURL() + ";\t" + JSON.toJSONString(weixinPassport));
			request.setAttribute("weixinPassport", weixinPassport);
		} catch (Exception e) {
			logger.error("解析token失败,token="+encryptStr);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}


}
