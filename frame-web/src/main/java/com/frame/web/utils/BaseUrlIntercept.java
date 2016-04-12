package com.frame.web.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public  class BaseUrlIntercept implements UrlIntercept {
    /**
     * 重写的参数
     */
    protected Map<String, String[]> urlMaps = new HashMap<String, String[]>();
    /**
     * url rewirte到
     */
    protected String urlSeparate = "-";
    /**
     * 后缀
     */
    protected String urlSuffix = ".html";

    public void doIntercept(UrlUtil cusUrl) {
        String path = cusUrl.getPath();
        if (StringUtils.isNotBlank(path)) {
            if (urlMaps.containsKey(path)) {
                Object o = urlMaps.get(path);
                int start = path.lastIndexOf('.');
                int start1 = path.lastIndexOf('/');
                StringBuilder builder;

                if (start > start1) {//去掉扩展名。
                    builder = new StringBuilder(path.substring(0, start));
                } else {
                    builder = new StringBuilder(path);
                }
                if (o != null) {
                    String[] parameters = (String[]) o;
                    Map<String, Object> queryMap = cusUrl.getQuery();
                    for (String parameter : parameters) {
                        builder.append(urlSeparate);
                        if (StringUtils.isNotEmpty(parameter)) {
                            Object o1 = queryMap.get(parameter);
                            if (o1 != null) {
                                builder.append(cusUrl.encodeUrl(o1.toString()));
                            }
                        }
                        queryMap.remove(parameter);
                    }
                }
                builder.append(urlSuffix);
                cusUrl.setPath(builder.toString());
            }
        }
    }

    public void setUrlMaps(Map<String, String[]> urlMaps) {
        this.urlMaps = urlMaps;
    }

    public void setUrlSeparate(String urlSeparate) {
        this.urlSeparate = urlSeparate;
    }

    public void setUrlSuffix(String urlSuffix) {
        this.urlSuffix = urlSuffix;
    }
}

