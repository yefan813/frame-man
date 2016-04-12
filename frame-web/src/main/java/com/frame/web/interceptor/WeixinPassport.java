package com.frame.web.interceptor;

/**
 * 微信用户基础数据，包含userId, unionId、openId。微信唯一标记使用unionId
 * @author qingrong.hou@dmall.com
 * @date 2015/12/03
 */
public class WeixinPassport {
	private Long userId;
	private String unionId;
	private String openId;

	public WeixinPassport() {
	
	}
	
	public WeixinPassport(Long userId, String unionId, String openId) {
		super();
		this.userId = userId;
		this.unionId = unionId;
		this.openId = openId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
