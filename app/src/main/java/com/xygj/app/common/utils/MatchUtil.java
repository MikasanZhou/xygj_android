package com.xygj.app.common.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的验证工具
 * Created by xuyougen on 2016/7/30 0030.
 */

public class MatchUtil {

    /**
     * 判断文是否为空
     * @param text
     * @return
     */
    public static boolean isTextEmpty(String text) {
        return text == null || text.length() == 0;
    }

    /**
     * 验证手机号
     *
     * @param data
     * @return
     */
    public static boolean isPhone(String data) {
        //"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(data)) return false;
        else return data.matches(telRegex);
    }

    /**
     * 验证密码格式
     *
     * @param pwd
     * @return
     */
    public static boolean isLegalPwd(String pwd) {
        String pwdRegex = "[a-zA-Z\\d]{6,20}";
        return !TextUtils.isEmpty(pwd) && pwd.matches(pwdRegex);
    }

    public static boolean isTelephone(CharSequence data) {
        Pattern pattern = Pattern.compile("^(0[0-9]{2,3})?([2-9][0-9]{6,7})+(-[0-9]{1,4})?$|(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\\\d{8}$)");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    /**
     * 验证邮箱
     *
     * @param data
     * @return
     */
    public static boolean isEmail(CharSequence data) {
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-,_,\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(data);
        return m.find();
    }

    /**
     * 密码检查
     *
     * @param data
     * @return
     */
    public static boolean isPasswordChecked(CharSequence data) {
        //8-20位，数字、字母、-
        Pattern p = Pattern.compile("^(([a-z0-9A-Z]+[_]?)+){6,20}$");
        Matcher m = p.matcher(data);
        return m.find();
    }


    public static boolean isEqualsPhone(String str) {
        //8-20位，数字、字母、-
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0,6])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 校验安全问题答案
     *
     * @param data
     * @return
     */
    public static boolean isAnswerChecked(CharSequence data) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9.。？?-——,，\u4E00-\u9FA5]{1,60}$");
        Matcher m = p.matcher(data);
        return m.find();
    }

    /**
     * 校验安全问题
     *
     * @param data
     * @return
     */
    public static boolean isQuestionChecked(CharSequence data) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9\\.。？?\\-——,，\u4E00-\u9FA5]{1,60}$");
        Matcher m = p.matcher(data);
        return m.find();
    }

    /**
     * 银行卡校验
     *
     * @param data
     * @return
     */
    public static boolean isBankCardChecked(CharSequence data) {
        Pattern p = Pattern.compile("^([0-9]{16}|[0-9]{19})$");
        Matcher m = p.matcher(data);
        return m.find();
    }

    /**
     * 校验数字，若有小数位，只允许小数点后两位
     *
     * @param data
     * @return
     */
    public static boolean isDigit(CharSequence data) {
        Pattern p = Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$");
        Matcher m = p.matcher(data);
        return m.find();
    }

    /**
     * 检查长度，包含指定的长度
     *
     * @param data
     * @param length
     * @return
     */
    public static boolean isLengthChecked(CharSequence data, int length) {
        return data.length() <= length;
    }

    /**
     * 检查长度范围，包含指定的长度
     *
     * @param data
     * @param minLength
     * @param maxLength
     * @return
     */
    public static boolean isLengthRangeChecked(CharSequence data, int minLength, int maxLength) {
        return data.length() >= minLength && data.length() <= maxLength;
    }

    /**
     * 身份证验证开始
     */
    public static boolean IDCardValidate(String IDStr) {
        Pattern p = Pattern.compile("^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$");
        Matcher m = p.matcher(IDStr);
        return m.find();
    }

}

