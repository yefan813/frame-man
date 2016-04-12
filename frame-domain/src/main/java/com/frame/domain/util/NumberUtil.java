package com.frame.domain.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by garnett on 2015/9/25.
 */
public class NumberUtil {
    /**
     * 转换输入的double数据为两位小数的double
     *
     * @param value
     * @return
     */
    public static Double convert2FractDigitDouble(Double value) {
        if (value == null) {
            return value;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String formatedValue = df.format(value);
        return Double.valueOf(formatedValue);
    }


}
