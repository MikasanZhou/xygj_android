package com.xygj.app.jinrirong.utils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class CommonUtils {
    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     *
     * @param str 需要处理的字符串
     * @return 处理完之后的字符串
     */
    public static String addComma(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(",###");
        return decimalFormat.format(Double.parseDouble(str));
    }

}
