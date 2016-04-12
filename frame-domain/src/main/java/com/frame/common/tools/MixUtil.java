package com.frame.common.tools;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class MixUtil {
	public static final String CHAR_OK = "\\.;:\\-&_≥><≤/\\s\\*\\+\\(\\)\'\\\\";
	// 去除所有空格，连续多个","，以及末尾","
	public static String receivetrim(String str, String seprector) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		str = str.trim().replaceAll(" +", "").replaceAll("\\" + seprector + "+", seprector);
		if (str.endsWith(seprector)) {
			str = str.substring(0, str.length() - 1);
		}
		if (str.startsWith(seprector)) {
			str = str.substring(1, str.length());
		}
		return str;
	}

	// 串","元素去重，如 "1,12,2,1,2"→"1,12,2"
	public static String removerepeatedchar(String str) {
		str = receivetrim(str, ",");
		if (StringUtils.isBlank(str))
			return str;
		String[] tem = str.split(",");
		StringBuilder sb = new StringBuilder();
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < tem.length; i++) {
			String s = String.valueOf(tem[i]);
			if (!list.contains(s)) {
				list.add(String.valueOf(tem[i]));
			}
		}
		for (String string : list) {
			sb.append(string).append(",");
		}
		String sbstr = new String(sb);
		if (sbstr.endsWith(",")) {
			sbstr = sbstr.substring(0, sbstr.length() - 1);
		}
		return sbstr;
	}

	public static boolean isIn(String substring, String[] source) {
		if (source == null || source.length == 0) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否包含特殊字符
	 * 
	 * @Title: isContainSpecial
	 * @Description:
	 * @param exclusion
	 *            排除的字符，eg "#@"
	 * @return
	 */
	public static boolean isContainSpecial(String str, String exclusion) {
		return !str.matches("^[A-Za-z0-9\u4e00-\u9fa5" + exclusion + "]+$");
	}


	public static boolean isJdImgServerUrl(String url) {
		if (url.matches("^(?i)g(\\d+)/(?i)M(\\d+)/(\\d+)/(\\d+)/.+\\.(png|jpg)")) {
			return true;
		}
		return false;
	}

	// 完整url地址判断
	public static boolean isURL(String url) {
		if (url.matches("http(s)?:\\/\\/([\\w-]+\\.)+[\\w-]+(\\/[\\w- .\\/?%&=]*)?")) {
			return true;
		}
		return false;
	}


	/**
	 * 通过商品title返回seoName，用于拼接商品详情页url
	 * wareId就是商品id
	 * 
	 * @param arg
	 * @return
	 */
	public static String buildSeoUrl(String title) {
		if (StringUtils.isBlank(title)) {
			return "";
		}
		title = title.replaceAll("[^a-zA-Z0-9]", "-").replaceAll("-{2,}", "-");// 重复横杆
		title = title.replaceAll("^-", "").replaceAll("-$", "");// 首横杆、尾横杆
		return title;
	}

	public static boolean isContain(String src, String reg){
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(src);
        return matcher.find();
    }
    
   
}
