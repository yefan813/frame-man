package com.frame.domain.img;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class ImageValidate {

	/** 合法的图片文件后缀名列表（正则） */
	public static final String VALID_FILE_TYPE = "jpg|jpeg|png";
	/** 上传文件的最大大小，单位为byte */
	public static final int FILE_MAX_SIZE = 1 * 1024 * 1024;
	/** 合法的最大图片宽度 单位：像素 */
	public static final int IMG_WIDTH_MAX = 800;
	/** 合法的最大图片高度 单位：像素 */
	public static final int IMG_HEIGHT_MAX = 800;
	/** 合法的最小图片宽度 单位：像素 */
	public static final int IMG_WIDTH_MIN = 400;
	/** 合法的最小图片高度 单位：像素 */
	public static final int IMG_HEIGHT_MIN = 400;
	
	/** 商家logo的最大大小，单位为byte */
	public static final int VENDER_LOGOG_MAX_SIZE = 15 * 1024;
	
	/** 合法的最大图片宽度 单位：像素 */
	public static final int IMG_WIDTH_CHECK = 750;
	/** 合法的最大图片高度 单位：像素 */
	public static final int IMG_HEIGHT_CHECK = 400;
	
	/**
	 * 为图片上传做验证
	 * 
	 * @return 如果验证失败，将返回表示验证失败原因的错误码， 验证成功则返回UploadImgInfo对象
	 */
	public static Result validate4Upload(MultipartFile imgFile) {
		Result r = new Result(false);
		
//		r.setSuccess(true);
//		return r;
		
		BufferedImage bufferedImage;
		try {
			// 没有取到上传文件对象，可能是上传过程中网络中断导致
			if (imgFile == null) {
				r.setResultCode("上传文件失败!");
				return r;
			}
			String fileType = cutFileType(imgFile.getOriginalFilename()); // 文件名后缀
			bufferedImage = ImageIO.read(imgFile.getInputStream());
			if (imgFile.getSize() > FILE_MAX_SIZE) { // 文件大小
				r.setResultCode("上传图片尺寸太大!");
				return r;
			} else if (!isValidFileType(fileType)) { // 文件类型
				r.setResultCode("无效的文件类型!");
				return r;
			} /*else if (!checkImgSizeProduct(r, bufferedImage.getWidth(), bufferedImage.getHeight())) { // 图片尺寸
				return r;
			} */else {
				// 图片验证合法， 组装uploadImgInfo对象返回
				r.setSuccess(true);
				return r;
			}
		} catch (IOException e) {
			r.setResultCode("upload.exception");
			return r;
		}
	}
	
	/**
	 * 为图片上传做验证
	 * 
	 * @return 
	 */
	public static Result validate4Vender(MultipartFile imgFile) throws IOException {
		Result r = new Result(false);
		
		// 没有取到上传文件对象，可能是上传过程中网络中断导致
		if (imgFile == null) {
			r.setResultCode("ware.publish.exception");
			return r;
		}
		
		if (imgFile.getSize() > VENDER_LOGOG_MAX_SIZE) { // 文件大小
			r.setResultCode("文件超过15K限制");
			return r;
		} 		
		r.setSuccess(true);
		return r;
	}
	
	public static Result validate4Brand(MultipartFile imgFile) {
		Result r = new Result(false);
		try {
			// 没有取到上传文件对象，可能是上传过程中网络中断导致
			if (imgFile == null) {
				r.setResultCode("请选择品牌logo");
				return r;
			}
			
			String fileType = cutFileType(imgFile.getOriginalFilename()); // 文件名后缀
			if (imgFile.getSize() > FILE_MAX_SIZE) { // 文件大小
				r.setResultCode("品牌logo大小不能超过1M");
				return r;
			} else if (!isValidFileType(fileType)) { // 文件类型
				r.setResultCode("品牌logo图片类型仅支持jpg|jpeg|png");
				return r;
			} else {
				// 图片验证合法， 组装uploadImgInfo对象返回
				r.setSuccess(true);
				return r;
			}
		} catch (Exception e) {
			r.setResultCode("品牌logo验证失败");
			return r;
		}
	}

	public static boolean checkImgSize(Result result, int width, int height) {
		if (width < IMG_WIDTH_MIN || width > IMG_WIDTH_MAX) {
			result.setResultCode("upload.invalid.size");
			return false;
		}
		if (height < IMG_HEIGHT_MIN || height > IMG_HEIGHT_MAX) {
			result.setResultCode("upload.invalid.size");
			return false;
		}

		return true;
	}
	
	public static boolean checkImgSizeProduct(Result result, int width, int height) {
		if (width != IMG_WIDTH_CHECK) {
			result.setResultCode("图片尺寸不正确，请上传750*400尺寸的图片");
			return false;
		}
		if (height != IMG_HEIGHT_CHECK) {
			result.setResultCode("图片尺寸不正确，请上传750*400尺寸的图片");
			return false;
		}

		return true;
	}

	/**
	 * 验证文件类型是否合法
	 * 
	 * @param fileType
	 *            表示文件类型的字符串
	 * @return ""
	 */
	public static boolean isValidFileType(String fileType) {
		return Pattern.compile(VALID_FILE_TYPE, Pattern.CASE_INSENSITIVE).matcher(fileType).matches();
	}

	/**
	 * 截取文件后缀名 后缀==文件名中最后一个点后面的字符串
	 * 
	 * @param fileName
	 *            文件名
	 * @return 后缀
	 */
	public static String cutFileType(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}
}
