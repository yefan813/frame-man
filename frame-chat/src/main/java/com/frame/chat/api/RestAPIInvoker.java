package com.frame.chat.api;

import java.io.File;

import com.frame.chat.comm.wrapper.BodyWrapper;
import com.frame.chat.comm.wrapper.HeaderWrapper;
import com.frame.chat.comm.wrapper.QueryWrapper;
import com.frame.chat.comm.wrapper.ResponseWrapper;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header, QueryWrapper query);
}
