package com.frame.domain.base;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * 带有扩展字段的基础domain类
 * @author leo zhang
 * @since 2014-03-01
 */

public class BaseFeatureDomain extends BaseDomain{

	private static final long serialVersionUID = 5195605927103879883L;

	/**键值对，扩展字段*/
	protected String features;
	
	/** 封装features字段，转换为Properties对象， 便于操作*/
	private Properties featuresObj;

	public void setFeaturesObj(Properties proObj){
		if(proObj != null && !proObj.isEmpty()) {
			
			StringWriter writer = new StringWriter();
			try {
				proObj.store(writer, "Java-Server");
			} catch (IOException e) {
				return;
			}
			
			this.features = writer.toString();
		}
	}
	
	public Properties getFeaturesObj(){
		if(featuresObj != null) {
			return featuresObj;
		}
		
		if(StringUtils.isNotBlank(this.features)) {
			Properties p = new Properties();
			try {
				p.load(new StringReader(this.features));
				return featuresObj = p;
			} catch (IOException e) {
				
			}
		}
		
		return null;
	}
	
	/**
	 * 获取 features
	 * @return
	 */
	public String getFeatures(){
		return features;
	}
	
	/**
	 * 设置 features
	 * @param features
	 */
	public void setFeatures(String features){
		this.features = features;
	}
	
}
