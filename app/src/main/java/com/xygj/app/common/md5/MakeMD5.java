package com.xygj.app.common.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by limiaomiao on 17/3/15.
 */

public class MakeMD5 {

    /**
     * 对字符串进行32位签名
     * @param value
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String makeMD5(String value) {
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digester.reset();
        return bytes2HexString(digester.digest(value.getBytes()));
    }

    /**
     * 二进制转十六进制String
     * @param bytes
     * @return
     */
    private static String bytes2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            //方法一
            int val = ((int)bytes[i]) & 0xff;
            if(val < 16) {
                sb.append("0"); //当转换十进制，会忽略掉前面的"0"
            }
            sb.append(Integer.toHexString(val));
            //方法二
            /*String s = Integer.toHexString(b[i] & 0xff);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s);*/
        }
        return sb.toString().toUpperCase();
    }
}
