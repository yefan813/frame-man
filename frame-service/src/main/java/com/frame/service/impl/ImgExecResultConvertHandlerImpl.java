package com.frame.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.frame.domain.img.ImgDealMsg;
import com.frame.service.ImgExecResultConvertHandler;

@Service("imgExecResultConvertHandler")
public class ImgExecResultConvertHandlerImpl implements ImgExecResultConvertHandler {

	public ImgDealMsg handleUploadImg(String result) {
		List<ImgDealMsg> msgList = convertToBean(result);
		if (null != msgList) {
			ImgDealMsg imgDealMsg = msgList.get(0);
			dealUploadSuccessImg(imgDealMsg);
			return imgDealMsg;
		}
		return null;
	}

	private void dealUploadSuccessImg(ImgDealMsg imgDealMsg) {
		if (imgDealMsg.isSuccess()) {
			// 上传操作需要组装图片的url地址
			imgDealMsg.setMsg(imgDealMsg.getMsg());
		}
	}

	/**
	 * 根据hash获取分布的服务器
	 */
	static int indexFor(int h, int length) {
		return h & (length - 1);
	}

	public List<ImgDealMsg> handleUploadImgs(String result) {
		List<ImgDealMsg> msgList = convertToBean(result);
		if (null != msgList) {
			for (ImgDealMsg imgDealMsg : msgList) {
				dealUploadSuccessImg(imgDealMsg);
			}
		}

		return msgList;
	}

	public ImgDealMsg handleUploadByteImg(String result) {
		List<ImgDealMsg> msgList = convertToBean(result);
		if (null != msgList) {
			ImgDealMsg imgDealMsg = msgList.get(0);
			dealUploadSuccessImg(imgDealMsg);
			return imgDealMsg;
		}

		return null;
	}

	public List<ImgDealMsg> handleHardDelImgs(String result) {
		return dealOperateImgs(result);
	}

	public List<ImgDealMsg> handleSoftDelImgs(String result) {
		return dealOperateImgs(result);
	}

	private List<ImgDealMsg> dealOperateImgs(String result) {
		List<ImgDealMsg> dealMsgList = convertToBean(result);
		return dealMsgList;
	}

	public List<ImgDealMsg> handleCancelSoftDelImgs(String result) {
		return dealOperateImgs(result);
	}

	public ImgDealMsg handleReplaceImg(String result, String realImgUri) {
		List<ImgDealMsg> msgList = convertToBean(result);
		if (null != msgList) {
			ImgDealMsg dealMsg = msgList.get(0);
			dealMsg.setOldUri(realImgUri);
			return dealMsg;
		}

		return null;
	}

	public List<ImgDealMsg> handleResult(String result) {
		return convertToBean(result);
	}

	private List<ImgDealMsg> convertToBean(String jsonStr) {
		JSONArray jsonArray = JSONArray.parseArray(jsonStr);
		if (jsonArray != null && jsonArray.size() > 0) {
			List<ImgDealMsg> resultList = new ArrayList<ImgDealMsg>();
			for (int i = 0; i < jsonArray.size(); i++) {
				ImgDealMsg dealMsg = JSONObject.parseObject(jsonArray.getJSONObject(i).toJSONString(), ImgDealMsg.class);
				if (dealMsg != null) {
					resultList.add(dealMsg);
				}
			}
			return resultList;
		}

		return null;
	}

}
