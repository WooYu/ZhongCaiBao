package com.developer.lecai.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class JsonUtil {

    /**
     * 把json解析成Bean
     * @param json
     * @return
     */
    public static <T> T parseJsonToBean(String json, Class<T> cls) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * 把json字符串变成map
     *
     * @param json
     * @return
     */
    public static HashMap<String, Object> parseJsonToMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> map = null;
        try {
            map = gson.fromJson(json, type);
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 把json字符串变成list
     *
     * @param json
     * @return
     */
    public static <T> List<T> parseJsonToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, T>>() {
        }.getType();
        List<T> list = null;
        try {
            list = gson.fromJson(json, type);
        } catch (Exception e) {
        }
        return list;
    }
    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static List<?> parseJsonToList(String json, Type type) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, type);
        return list;
    }
    /**
     * 获取json串中某个字段的值，返回String
     *
     * @param json
     * @param key
     * @return
     */
    public static String getStringValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 获取json串中某个字段的值，返回Object
     *
     * @param json
     * @param key
     * @return
     */
    public static Object getObjectValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "暂无信息";
        JSONObject jsonObject = null;
        Object value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }
    /**
     * 获取json串中某个字段的值，注意，只能获取同一层级的value
     *
     * @param json
     * @param key
     * @return
     */
    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }
    /**
     * 获取json串中某个字段的值，返回int
     *
     * @param json
     * @param key
     * @return
     */
    public static int getIntValue(String json, String key) {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonElement jEle = jsonObject.get(key);
        if(jEle == null) {
            return -100;
        }
        return jEle.getAsInt();
    }


    /**
     * 将一个bean转换为json
     *
     * @param o
     * @return
     */
    public static String parseBeanToJson(Object o) {
        Gson gson = new Gson();
        String s = gson.toJson(o);
        return s;
    }
}
