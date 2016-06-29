package com.frame.service.utils;

import java.util.Random;

/**
 * 生成随机字符串
 */
public class RandomStrUtils {

	/**
	 * 生成唯一字符串字典
	 */
	private static String[] strArr = new String[] { "a", "b", "c", "d", "e",
			"f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9" };

	
	/**
	 * 获取唯一字符串 最低10个 最高16个
	 * @param len  获取多少长度的字符串
	 * @return
	 * @throws Exception
	 */
	public static String getUniqueString(int len) {
		try {
			Random ran = new Random();
			int num = 0;
			int dn = Math.abs(ran.nextInt() % 7) + len;
			String returnString = "";
			String str = "";
			for (int i = 0; i < dn;) {
				num = ran.nextInt(61);
				str = strArr[num];
				if (!(returnString.indexOf(str) >= 0)) {
					returnString += str;
					i++;
				}
			}
			return returnString;
		} catch (Exception e) {
			return null;
		}
	}
}
