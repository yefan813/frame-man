package com.frame.domain.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Result  {

    private boolean success;


    private Map<String,Object> result = new HashMap<String,Object>();


    public static final String DEFAULT_MODEL_KEY = "value";


    private String modelKey =DEFAULT_MODEL_KEY;


    private String resultCode;
    private String resultMsg;
    private String[] resultCodeParams;


    public Result(boolean success) {
        this.success = success;
    }


    public Result() {
    }


    public Object addDefaultModel(Object obj) {
        return result.put(DEFAULT_MODEL_KEY,obj);        
    }

    public Object addDefaultModel(String key,Object obj) {
        modelKey=key;
        return result.put(key,obj);
    }

  
    public Set<String> keySet() {
        return result.keySet();
    }


    public Map<String,Object> getMap() {
        return result;
    }

    public Object get() {
        return result.get(modelKey);
    }


    public Object get(String key) {
        return result.get(key);
    }

    @SuppressWarnings("rawtypes")
	public Collection values() {
        return result.values();
    }
 
    public boolean getSuccess() {
        return success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultCode(String resultCode, String... args) {
        this.resultCode = resultCode;
        this.resultCodeParams=args;
    }

    public String[] getResultCodeParams() {
        return resultCodeParams;
    }

    public void setResultCodeParams(String[] resultCodeParams) {
        this.resultCodeParams = resultCodeParams;
    }


	public String getResultMsg() {
		return resultMsg;
	}


	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
    
}
