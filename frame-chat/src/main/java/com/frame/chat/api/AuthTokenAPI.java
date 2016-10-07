package com.frame.chat.api;

public interface AuthTokenAPI{	
	Object getAuthToken(String clientId, String clientSecret);
}
