package com.frame.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.frame.domain.img.ImgDealException;
import com.frame.domain.img.ImgDealMsg;
import com.frame.service.ImgExecResultConvertHandler;
import com.frame.service.ImgSysService;
import com.frame.service.utils.ImageUpload;
import com.frame.service.utils.LogHelper;


@Service("imgSysService")
public class ImgSysServiceImpl implements ImgSysService {

	/** 图片服务器连接超时时间 */
	private Integer conTimeout;
	/** 图片服务器方法调用超时时间 */
	private Integer callTimeout;
	@Resource private ImageUpload imageUpload;

	public ImgSysServiceImpl() {
	}

	public ImgSysServiceImpl(String aucode, Integer conTimeout, Integer callTimeout,
			ImgExecResultConvertHandler imgExecResultConvertHandler) {
		this.conTimeout = conTimeout;
		this.callTimeout = callTimeout;

		//if (null != this.conTimeout)
			//ImageUpload.setConTimeout(this.conTimeout.intValue());
		//if (null != this.callTimeout)
			//ImageUpload.setCallTime(this.callTimeout.intValue());
	}

	public ImgDealMsg uploadImg(File file) throws ImgDealException {
		try {
			// 上传单个图片
			return imageUpload.uploadFile(file);
		} catch (Exception e) {
			LogHelper.image_interface.error("ImgSysServiceImpl#uploadImg exec failed:", e);
			throw new ImgDealException(e);
		} finally {
		}
	}

	public ImgDealMsg uploadByteImg(byte[] img, String path) throws ImgDealException {
		try {
			return imageUpload.uploadFile(img, path);
		} catch (Exception e) {
			LogHelper.image_interface.error("ImgSysServiceImpl#uploadByteImg exec failed:", e);
			throw new ImgDealException(e);
		} finally {
		}
	}

	public List<ImgDealMsg> uploadImgs(List<File> imgs) throws ImgDealException {
		try {
			// 批量上传图片
			return imageUpload.uploadFile(imgs);
		} catch (Exception e) {
			LogHelper.image_interface.error("ImgSysServiceImpl#uploadImgs exec failed:", e);
			throw new ImgDealException(e);
		} finally {
		}
	}



	@SuppressWarnings("unused")
	private String list2string(List<String> list, String flag) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String s : list) {
			if (i == 0) {
				sb.append(s);
			} else {
				sb.append(flag).append(s);
			}
			i++;
		}

		return sb.toString();
	}
	
	public Integer getConTimeout() {
		return conTimeout;
	}

	public void setConTimeout(Integer conTimeout) {
		this.conTimeout = conTimeout;
	}

	public Integer getCallTimeout() {
		return callTimeout;
	}

	public void setCallTimeout(Integer callTimeout) {
		this.callTimeout = callTimeout;
	}
}
