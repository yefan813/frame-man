package com.frame.web.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by isaac on 15/9/1.
 */
public class Base24Util {
    public static final String BASE24_CHARS = "BCDFGHJKMPQRTVWXY2346789";

    public static String generateBase24Code(int length) {
        return RandomStringUtils.random(length, Base24Util.BASE24_CHARS);
    }

    public static String encode2Chars(int number) {
        if (number > 529) {
            throw new UnsupportedOperationException("超出编码范围");
        }
        int firstIndex = number / Base24Util.BASE24_CHARS.length();
        int secondIndex = number % Base24Util.BASE24_CHARS.length();
        return "" + Base24Util.BASE24_CHARS.charAt(firstIndex) + Base24Util.BASE24_CHARS.charAt(secondIndex);
    }

    public static int decode2Chars(String code) {
        if (code.length() > 2) {
            throw new UnsupportedOperationException("超出编码范围");
        }
        return Base24Util.BASE24_CHARS.indexOf(code.charAt(0)) * Base24Util.BASE24_CHARS.length() + Base24Util.BASE24_CHARS.indexOf(code.charAt(1));
    }

    private static boolean verifyExcodeFormat(String excode) {
        if (excode == null || excode.length() != 10 || !"BCDF".contains("" + excode.charAt(8)) || !Base24Util.BASE24_CHARS.contains("" + excode.charAt(9))) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(decode2Chars("CD"));
//        System.out.println(verifyExcodeFormat("XPT83WY3BG"));
//        for (int i = 0; i < 600; i++) {
//            String code = encode2Chars(i);
//            int number = decode2Chars(code);
//            System.out.println(code + ":" + number + ":" + encode2Chars(number));
//        }
    }

}
