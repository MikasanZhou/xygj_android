package com.xygj.app.common.utils;

import org.json.JSONObject;

import java.util.Map;

/**
 * Json相关工具类
 * Created by Administrator on 2018/3/6.
 */

public class JsonUtils {
    /**
     * 获取map对应的Json字符串，网络请求时使用
     * @param map 待转换的map
     * @return 结果字符串
     */
    public static String getJsonStr(Map<String, Object> map) {
        return new JSONObject(map).toString();
    }
}
