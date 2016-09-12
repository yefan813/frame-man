package com.frame.service.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogHelper {

	/**
	 * 新增/编辑商品公共验证
	 */
	public static final Log common_ware = LogFactory.getLog("common_ware");
	/**
	 * mongodb相关
	 */
	public static final Log mongodb_rel = LogFactory.getLog("mongodb_rel");
	/**
	 * 外链转换
	 */
	public static final Log convert_url = LogFactory.getLog("convert_url");
	/**
	 * 图片接口
	 */
	public static final Log image_interface = LogFactory.getLog("image_interface");
	/**
	 * 价格推送接口
	 */
	public static final Log pubprice_interface = LogFactory.getLog("pubprice_interface");
	/**
	 * 商家接口
	 */
	public static final Log vender_interface = LogFactory.getLog("vender_interface");
	/**
	 * 类目接口
	 */
	public static final Log cat_interface = LogFactory.getLog("vender_interface");
	 /**
     * shop端商品管理
     */
    public static final Log ware_manager = LogFactory.getLog("ware_manager");

}
