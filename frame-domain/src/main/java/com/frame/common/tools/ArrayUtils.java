package com.frame.common.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ArrayUtils extends org.apache.commons.lang.ArrayUtils {

    /**
     * 将字符串拆分成整形数组。以“,”为拆分标记
     *
     * @param str 需要拆分的串
     * @param
     * @return
     */
    public static Integer[] splitInt(String str) {
        return splitInt(str, ",");
    }

    /**
     * 将字符串拆分成整形数组。以“,”为拆分标记
     * @param str
     * @param delim
     * @return
     */
    public static Integer[] splitInt(String str, String delim) {
        if (str != null && str.length() > 0) {
            List<Integer> integers = new ArrayList<Integer>();
            for (StringTokenizer tokenizer = new StringTokenizer(str.trim(), delim); tokenizer.hasMoreTokens();) {
                String s = tokenizer.nextToken();
                try {
                    integers.add(Integer.parseInt(s.trim()));
                } catch (Exception e) {
                    //不要这个
                }
            }
            Integer[] rc = new Integer[integers.size()];
            integers.toArray(rc);
            return rc;
        }
        return null;
    }

    /**
     * 合并数组。以“,”合并
     *
     * @param ints 需要合并的对象，
     * @return
     */
    public static String join(Integer[] ints) {
        return join(ints, ",");
    }

    /**
     * 重载合并数组。以“,”合并
     *
     * @param objs 需要合并的对象，
     * @return
     */
    public static String join(Object[] objs) {
        return join(objs, ",");
    }


    /**
     * 合并数组
     *
     * @param ints 需要合并的对象
     * @param delim 合并标记
     * @return 合并后的对象
     */
    public static String join(Integer[] ints, String delim) {
        if (ints != null) {
            StringBuilder builder = new StringBuilder();
            for (Integer anInt : ints) {
                builder.append(',');
                builder.append(anInt);
            }
            if (builder.length() > 0) {
                return builder.substring(1);
            }
            return builder.toString();
        }
        return null;
    }

    public static String join(Object[] objs, String delim) {
        if (objs != null) {
            StringBuilder builder = new StringBuilder();
            for (Object anObj : objs) {
                builder.append(',');
                builder.append(anObj);
            }
            if (builder.length() > 0) {
                return builder.substring(1);
            }
            return builder.toString();
        }
        return null;
    }
}