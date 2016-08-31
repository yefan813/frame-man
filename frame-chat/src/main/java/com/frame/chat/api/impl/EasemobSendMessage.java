package com.frame.chat.api.impl;

import com.frame.chat.api.EasemobRestAPI;
import com.frame.chat.api.SendMessageAPI;
import com.frame.chat.comm.constant.HTTPMethod;
import com.frame.chat.comm.helper.HeaderHelper;
import com.frame.chat.comm.wrapper.BodyWrapper;
import com.frame.chat.comm.wrapper.HeaderWrapper;

public class EasemobSendMessage extends EasemobRestAPI implements SendMessageAPI {
    private static final String ROOT_URI = "/messages";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object sendMessage(Object payload) {
        String  url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        BodyWrapper body = (BodyWrapper) payload;

        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
