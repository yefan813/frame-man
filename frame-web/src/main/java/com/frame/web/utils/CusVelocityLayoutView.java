package com.frame.web.utils;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.context.Context;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

public class CusVelocityLayoutView extends VelocityLayoutView {

	/**
     * 存放公共url的map
     */
    private Map<String, UrlUtil> velocityUrl;

    /**
     * 页面工具类
     */
    private Map<String, Object> velocityTools;

    /**
     * 渲染页面公共内容
     *
     * @param context
     * @param response
     * @throws Exception
     */
    protected void doRender(Context context, HttpServletResponse response) throws Exception {
        //将公共url merge到页面
        mergeUrl(context, velocityUrl);
        //渲染工具类到页面
        merge(context, velocityTools);
        //渲染页面内容
        super.doRender(context, response);
    }


    /**
     * 将公共url注入到context
     *
     * @param context
     * @param map
     */
    private void mergeUrl(Context context, Map<String, UrlUtil> map) {
        if (map != null) {
            for (Map.Entry<String, UrlUtil> stringCusUrlEntry : map.entrySet()) {
                String key = stringCusUrlEntry.getKey();
                UrlUtil org = stringCusUrlEntry.getValue();
                UrlUtil value = org.clone();
                value.setJdUrl(org);
                context.put(key, value);
            }
        }
    }

    private void merge(Context context, Map<String, Object> map) {
        if (map != null) {
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                context.put(stringObjectEntry.getKey(), stringObjectEntry.getValue());
            }
        }
    }

    public void setVelocityUrl(Map<String, UrlUtil> velocityUrl) {
        this.velocityUrl = velocityUrl;
    }

    public void setVelocityTools(Map<String, Object> velocityTools) {
        this.velocityTools = velocityTools;
    }
}
