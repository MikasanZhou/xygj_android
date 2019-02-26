package com.xygj.app.common.md5;

import android.util.Log;


/**
 * Created by Administrator on 2017/12/12.
 */

public class MakeToken {

    /**
     * @param phone   手机号
     * @param pwd     密码
     * @param ticksid 时间戳id
     * @param val     时间戳val
     * @return 私有key  私有iv   私有token
     */
    public static String getToken(String phone, String pwd, String ticksid, String val) {
        String name = MD5.getMD5(MD5.getMD5(phone)).substring(2, 32);
        //String key = "XB" + MD5.getMD5(name + val).substring(2, 32);
        //String iv = "XB" + MD5.getMD5(MD5.getMD5(pwd) + ticksid).substring(2, 16);
        String tokenKey = MD5.getMD5(name + val).substring(0, 30);
        String tokenIv = MD5.getMD5("android" + MD5.getMD5(pwd) + ticksid).substring(2, 32);
        Log.e("TAG", "key: " + tokenKey);
        Log.e("TAG", "iv: " + tokenIv);
        Log.e("TAG", "token: " + tokenKey + tokenIv);
        return tokenKey + tokenIv;
    }

    public static String getKey(String phone, String val) {
        String name = MD5.getMD5(MD5.getMD5(phone)).substring(2, 32);
        return "XB" + MD5.getMD5(name + val).substring(2, 32);
    }

    public static String getIv(String pwd, String ticksid) {
        return "XB" + MD5.getMD5(MD5.getMD5(pwd) + ticksid).substring(2, 16);
    }
}
