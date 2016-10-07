package com.frame.chat.api.impl;

import com.frame.chat.api.ChatMessageAPI;
import com.frame.chat.api.EasemobRestAPI;
import com.frame.chat.comm.constant.HTTPMethod;
import com.frame.chat.comm.helper.HeaderHelper;
import com.frame.chat.comm.wrapper.HeaderWrapper;
import com.frame.chat.comm.wrapper.QueryWrapper;

public class EasemobChatMessage extends EasemobRestAPI implements ChatMessageAPI {

    private static final String ROOT_URI = "chatmessages";

    public Object exportChatMessages(Long limit, String cursor, String query) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        QueryWrapper queryWrapper = QueryWrapper.newInstance().addLimit(limit).addCursor(cursor).addQueryLang(query);

        return getInvoker().sendRequest(HTTPMethod.METHOD_DELETE, url, header, null, queryWrapper);
    }

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }
}
