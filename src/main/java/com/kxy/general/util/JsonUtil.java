package com.kxy.general.util;

import net.sf.json.JSONObject;

/**
 *
 * Created by xiangyunkong on 26/04/2017.
 */
public final class JsonUtil {
    /**
     * do not init utility class.
     */
    private JsonUtil() {
    }

    /**
     * covert java object to json string.
     * @param object java object
     * @return json string
     */
    public static String objectToJson(Object object) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }

    /**
     * convert json string to java object with type type.
     * @param json json string
     * @param type java object type
     * @param <T> java object type class
     * @return java object
     */
    public static <T> T jsonToObject(String json, Class<T> type) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return (T) JSONObject.toBean(jsonObject, type);
    }
}
