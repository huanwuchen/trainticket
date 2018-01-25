package com.test.trainticket.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/20.
 */

public class JsonUtils {

    /**
     * 对象转化为json fastjson 使用方式
     *
     * @return
     */
    public static String toString(Object object) {
        if (object == null) {
            return "";
        }
        try {
            return JSON.toJSONString(object);
        } catch (com.alibaba.fastjson.JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * json转化为对象  fastjson 使用方式
     *
     * @return
     */
    public static <T> T getModel(String jsonData, Class<T> clazz) {
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        try {
            return JSON.parseObject(jsonData, clazz);
        } catch (com.alibaba.fastjson.JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json String
     * @param cls class
     * @param <T> T模型
     * @return List<T>
     */
    public static <T> List<T> getList(String json, Class<T> cls) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON.parseArray(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }


    /**
     * json转化为List  fastjson 使用方式
     */
    public static List getList(String jsonData) {
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        List arrayList = null;
        try {
            arrayList = JSON.parseObject(jsonData, new TypeReference<ArrayList>() {
            });
        } catch (Exception e) {
        }
        return arrayList;

    }


    /**
     * json转化为Map  fastjson 使用方式
     */
    public static Map getMap(String jsonData) {
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        Map map = null;
        try {
            map = JSON.parseObject(jsonData, new TypeReference<Map>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject getJsonObj(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static JSONArray getJsonArr(String jsonArray) {
        try {
            return new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public static String toString(JSONArray jsonArray, int index) {
        try {
            return jsonArray.getJSONArray(index).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray().toString();
    }

    public static String toObjString(JSONArray jsonArray, int index) {
        try {
            return jsonArray.getJSONObject(index).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray().toString();
    }

    public static boolean hasKey(String json, String key) {
        JSONObject jsonObject = getJsonObj(json);
        return jsonObject.has(key);
    }

    public static String getValue(String json, String key) {
        JSONObject jsonObject = getJsonObj(json);
        String value = jsonObject.optString(key, "");
        return getStringNull(value);
    }

    public static int getIntValue(String json, String key) {
        JSONObject jsonObject = getJsonObj(json);
        return jsonObject.optInt(key, 0);
    }

    public static String getValue(JSONObject jsonObject, String key) {
        String value = jsonObject.optString(key, "");
        return getStringNull(value);
    }

    public static double getDoubleValue(String json, String key) {
        JSONObject jsonObject = getJsonObj(json);
        return jsonObject.optDouble(key, 0);
    }

    public static long getLongValue(String json, String key) {
        JSONObject jsonObject = getJsonObj(json);
        return jsonObject.optLong(key, 0);
    }

    public static boolean isEmpty(JSONObject jsonObject) {
        return 0 == jsonObject.length();
    }

    private static String getStringNull(String value) {
        if (value == null || "null".equals(value)) {
            return "";
        } else {
            return value;
        }
    }


}
