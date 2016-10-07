package com.frame.service.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 图片类空间的demo，一般性操作参考文件空间的demo（FileBucketDemo.java）
 * <p>
 * 注意：直接使用部分图片处理功能后，将会丢弃原图保存处理后的图片
 */
public class PictureUtils {
	private String bucket;
	private String url;
	private String sk;
    private static final Logger LOGGER = LoggerFactory.getLogger(PictureUtils.class);
	
	public String getSk() {
		return sk;
	}

	public void setSk(String sk) {
		this.sk = sk;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
		/** 绑定的域名 */
		this.url = "http://" + bucket + ".b0.upaiyun.com";
	}

	// 运行前先设置好以下三个参数
	private static final String OPERATOR_NAME = "sichuanzhenrui01";
	private static final String OPERATOR_PWD = "lanqiupai";

	/** 根目录 */
	private static final String DIR_ROOT = "/";

	private static UpYun upyun = null;

	
	public String upload(File file, String path) throws IOException {

		// 初始化空间
		upyun = new UpYun(bucket, OPERATOR_NAME, OPERATOR_PWD);

		// ****** 可选设置 begin ******

		// 切换 API 接口的域名接入点，默认为自动识别接入点
		// upyun.setApiDomain(UpYun.ED_AUTO);

		// 设置连接超时时间，默认为30秒
		// upyun.setTimeout(60);

		// 设置是否开启debug模式，默认不开启
		upyun.setDebug(false);

		// ****** 可选设置 end ******

		/*
		 * 一般性操作参考文件空间的demo（FileBucketDemo.java）
		 * 
		 * 注：图片的所有参数均可以自由搭配使用
		 */
		// 1.上传文件（文件内容）
		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + path + UUID.randomUUID();
		//异步处理
		if (writeFile(file, filePath)) {
			// 2.图片做缩略图；若使用了该功能，则会丢弃原图  默认生产几张缩略图
			/*if (CollectionUtils.isNotEmpty(sizeList)) {
				for (String size : sizeList) {
					if (!gmkerl(file, filePath+"_" + size, size)){
						return null;
					}
				}
			}*/
			//图片和缩略图上传成功，返回又拍的图片访问地址
			return filePath.substring(1);
		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @throws IOException
	 */
	public boolean writeFile(File file, String filePath) throws IOException {		
		// 设置待上传文件的 Content-MD5 值
		// 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
		upyun.setContentMD5(UpYun.md5(file));

		// 设置待上传文件的"访问密钥"
		// 注意：
		// 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
		// 举例：
		// 如果缩略图间隔标志符为"!"，密钥为"sk"，上传文件路径为"/folder/test.jpg"，
		// 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!sk
		if (StringUtils.isNotEmpty(sk)) {
			upyun.setFileSecret(sk);
		}

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true);

		LOGGER.info(filePath + " 上传" + isSuccess(result));

		// 获取上传文件后的信息（仅图片空间有返回数据）
		LOGGER.info("\r\n****** " + file.getName() + " 的图片信息 *******");
		// 图片宽度
		LOGGER.info("图片宽度:" + upyun.getPicWidth());
		// 图片高度
		LOGGER.info("图片高度:" + upyun.getPicHeight());
		// 图片帧数
		LOGGER.info("图片帧数:" + upyun.getPicFrames());
		// 图片类型
		LOGGER.info("图片类型:" + upyun.getPicType());

		LOGGER.info("****************************************\r\n");

		LOGGER.info("若设置过访问密钥(bac)，且缩略图间隔标志符为'!'，则可以通过以下路径来访问图片：");
		LOGGER.info(this.url + filePath + "!bac");
		return result;
	}

	/**
	 * 图片做缩略图
	 * <p>
	 * 注意：若使用了缩略图功能，则会丢弃原图
	 * 
	 * @throws IOException
	 */
	public boolean gmkerl(File file, String filePath, String size) throws IOException {
		// 设置缩略图的参数
		Map<String, String> params = new HashMap<String, String>();

		// 设置缩略图类型，必须搭配缩略图参数值（KEY_VALUE）使用，否则无效
		params.put(UpYun.PARAMS.KEY_X_GMKERL_TYPE.getValue(),
				UpYun.PARAMS.VALUE_FIX_BOTH.getValue());

		// 设置缩略图参数值，必须搭配缩略图类型（KEY_TYPE）使用，否则无效
		params.put(UpYun.PARAMS.KEY_X_GMKERL_VALUE.getValue(), size);

		// 设置缩略图的质量，默认 95
		params.put(UpYun.PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");

		// 设置缩略图的锐化，默认锐化（true）
		params.put(UpYun.PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");

		// 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称
		// 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
		params.put(UpYun.PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "small");

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true, params);

		LOGGER.info(filePath + " 制作缩略图" + isSuccess(result));
		LOGGER.info("可以通过该路径来访问图片：" + this.url + filePath);
		return result;
	}

	private static String isSuccess(boolean result) {
		return result ? " 成功" : " 失败";
	}
}
