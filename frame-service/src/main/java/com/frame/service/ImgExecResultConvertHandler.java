package com.frame.service;

import java.util.List;

import com.frame.domain.img.ImgDealMsg;


/**
 * 图片存储服务器接口执行结果的转换器
 * 目的转换为图片空间内部系统的对应结果.
 * @author heguang
 *
 */
public interface ImgExecResultConvertHandler {
	
	/**
	 * 处理批量上传图片返回结果
	 * @param result
	 * @return
	 */
	List<ImgDealMsg> handleUploadImgs(String result);
	
	/**
	 * 处理单个上传图片返回结果
	 * @param result 调用图片服务器接口返回的结果
	 * @return
	 */
	ImgDealMsg handleUploadImg(String result);
	
	/**
	 * 处理单个上传字节码类型图片返回结果
	 * @param result 调用图片服务器接口返回的结果
	 * @return
	 */
	ImgDealMsg handleUploadByteImg(String result);
	
	/**
	 * 处理批量硬删除图片返回结果
	 * @param result 调用图片服务器接口返回的结果
	 * @return
	 */
	List<ImgDealMsg> handleHardDelImgs(String result);
	
	/**
	 * 处理批量软删除图片返回结果
	 * @param result 调用图片服务器接口返回的结果
	 * @return
	 */
	List<ImgDealMsg> handleSoftDelImgs(String result);
	
	/**
	 * 处理批量取消软删除图片返回结果
	 * @param result 调用图片服务器接口返回的结果
	 * @return
	 */
	List<ImgDealMsg> handleCancelSoftDelImgs(String result);
	
	/**
	 * 处理替换图片返回结果
	 * @param result 调用图片服务器接口返回的结果
	 * @param realImgUri  被替换的图片地址
	 * @return
	 */
	ImgDealMsg handleReplaceImg(String result,String realImgUri);
	
	
}
