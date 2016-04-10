package com.frame.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.domain.common.RemoteResult;



@Controller
@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
public class IndexController{
	
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody RemoteResult index(HttpServletRequest request,Model view){
		RemoteResult msg = RemoteResult.success();
		msg.setMsg("Hello weixin gourpon!");
		return msg;
	}
    
}
