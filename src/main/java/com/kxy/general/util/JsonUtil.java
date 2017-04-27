package com.kxy.general.util;

import net.sf.json.JSONObject;

/**
 * Created by xiangyunkong on 26/04/2017.
 */
public final class JsonUtil {
    public static String objectToJson(Object object) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }

    public static <T> T jsonToObject(String json, Class<T> type) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return (T)JSONObject.toBean(jsonObject, type);
    }
}
