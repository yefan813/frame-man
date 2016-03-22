package com.frame.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.frame.web.interceptor.WeixinPassport;

@Controller
public class BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	protected HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	protected String getRemoteIp() {
		HttpServletRequest request = getRequest();
		try {
			String ip = request.getHeader("x-forwarded-for");
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("X-Real-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getRemoteAddr();
			}
			if ((ip != null) && (ip.length() != 0)
					&& (!("unknown".equalsIgnoreCase(ip)))) {
				int dotIdx = ip.indexOf(",");
				if (dotIdx == -1) {
					String[] ipToken = ip.split(" ");
					if (ipToken.length > 1) {
						return ipToken[1];
					}
				} else {
					String[] ipToken = ip.split(",");
					if (ipToken.length > 1) {
						return ipToken[0];
					}
				}
				return ip;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "";
	}

	
	public String dealJosnP(String callback, String data){
		if(data == null) data = "";
		
		// TODO log
		LOGGER.info("======jsonData======" + data);
		if(StringUtils.isBlank(callback)){
			return data;
		}else{
			return callback + "("  + data + ")";
		}
	}
	
	
	protected String getUnionId() {
		WeixinPassport ctx = (WeixinPassport) getRequest().getAttribute("weixinPassport");
		if (ctx == null) {
			return null;
		}
		return ctx.getUnionId();
	}
	
	protected String getOpenId() {
		WeixinPassport ctx = (WeixinPassport) getRequest().getAttribute("weixinPassport");
		if (ctx == null) {
			return null;
		}
		return ctx.getOpenId();
	}

	protected String getUrl(){
		HttpServletRequest request = getRequest();
		String url = request.getRequestURL().toString();
		return url;
	}
}
