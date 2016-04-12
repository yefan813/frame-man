package com.frame.domain.img;

public class ImgDealMsg {
	
	/**图片处理状态码:0,处理失败 */
	public static final String DEAL_FAIL = "0";
	/**图片处理状态码:1,处理成功 */
	public static final String DEAL_SUCCESS = "1";
	
	/** 处理结果信息id */
	public String id;
	/** 处理结果信息msg */
	public String msg;
	/** 处理结果信息描述 */
	public String description;
	/** 被删除图片的uri*/
	public String oldUri;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setOldUri(String oldUri) {
		this.oldUri = oldUri;
	}
	
	public String getOldUri() {
		return oldUri;
	}

	/**
	 * 是否处理成功
	 * @return
	 */
	public boolean isSuccess()
	{
		return DEAL_SUCCESS.equalsIgnoreCase(id);
	}

	/**
	 * 获取处理成功后得到的名称
	 * @return
	 */
	public String getUploadedFileName()
	{
		if(isSuccess())
		{
			return msg;
		}
		
		return null;
	}
	
	public String getDealFailedReason()
	{
		if(!isSuccess())
		{
			return ERROR_CODE.getExplanationByCode(msg);
		}
		
		return "处理成功.";
	}
	
	
	/**
	 * 获取被操作图片名称,删除成功、删除失败两种情况都可以获取到.
	 * @return
	 */
	public String getOperatedImgName()
	{
		return oldUri;
	}
	
	
	public enum ERROR_CODE {
		MSG_1("1", "授权码错误或无此授权码,请重新申请或找回授权码!"), 
		MSG_2("2", "未给业务设置目录生成方式,请联系管理员!"), 
		MSG_3("3", "上传图片扩展名错误!"), 
		MSG_4("4", "上传图片过大!"), 
		MSG_5("5", "上传路径为空或图片不存在,请重新上传!"), 
		MSG_6("6", "水印位置为空!"),
		MSG_7("7", "业务名或验证码错误!"),
		MSG_8("8", "获取异常,请重试!"),
		MSG_9("9", "删除异常,请重试!"),
		MSG_10("10", "上传失败,请重试!"),
		MSG_11("11", "图片本身出现问题,请检查图片格式!"),
		MSG_21("21", "没有删除权限!"),
		MSG_22("22", "恢复软删除操作时没有软删除过!"),
		MSG_30("30", "删除异常,请重试!");
		
		public String code;
		public String explanation;

		ERROR_CODE(String code, String explanation) {
			this.code = code;
			this.explanation = explanation;
		}
		
		public String getCode() {
			return code;
		}

		public String getExplanation() {
			return explanation;
		}

		static String getExplanationByCode(String code)
		{
			for(ERROR_CODE err_code : ERROR_CODE.values())
			{
				if(err_code.getCode().equals(code))
				{
					return err_code.explanation;
				}
			}
			
			return null;
		}
	};

}
