package com.frame.service;

import java.io.File;
import java.util.List;

import com.frame.domain.img.ImgDealException;
import com.frame.domain.img.ImgDealMsg;

public interface ImgSysService{

	/**
	 * 单个文件上传
	 * 
	 * @param srcFile
	 *            图片文件
	 * 
	 * @return {@link ImgUploadMsg} 图片上传结果对象
	 */
	ImgDealMsg uploadImg(File file) throws ImgDealException;

	/**
	 * 单个文件上传
	 * @param path
	 * @param srcFile
	 *            图片文件
	 * 
	 * @return {@link ImgUploadMsg} 图片上传结果对象
	 */
	ImgDealMsg uploadByteImg(byte[] img, String path) throws ImgDealException;

	/**
	 * 批量文件上传
	 * 
	 * @param srcFile
	 *            图片文件
	 * 
	 * @return {@link ImgUploadMsg} 图片上传结果对象
	 */
	List<ImgDealMsg> uploadImgs(List<File> imgs) throws ImgDealException;
}
