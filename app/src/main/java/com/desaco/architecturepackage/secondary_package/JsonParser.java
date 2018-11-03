package com.desaco.architecturepackage.secondary_package;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Map;

/**
 * Created by desaco on 2018/11/3.
 */

public class JsonParser<T> {
    private JsonParser() {
    }

    /**
     * // data 为 object 的情况
     * {"code":"0","message":"success","data":{}}
     * // data 为 array 的情况
     * {"code":"0","message":"success","data":[]}
     */
    private static Gson mGsonPack;

    private static Gson gsonInstance() {
        if (mGsonPack == null) {
            mGsonPack = new Gson();
        }
        return mGsonPack;
    }

//    private static Gson gson = null;
//    static {
//        if (gson == null) {
//            gson = new Gson();
//        }
//    }

    /**
     * 将Json转化为Bean对象
     *
     * @param jsonStr
     * @param cls
     * @return
     */
    public <T> T json2Bean(String jsonStr, Class<T> cls) {
        T obj = null;
        if (mGsonPack != null) {
            try {
                obj = mGsonPack.fromJson(jsonStr, cls);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public <T> String mapToJson(Map<String, T> map) {
        String jsonStr = mGsonPack.toJson(map);
        return jsonStr;
    }

    /**
     * Bean对象转化为Json
     *
     * @param cls
     * @return
     */
    public String bean2Json(Class<T> cls) {
        String json = null;
        if (mGsonPack != null) {
            try {
                json = mGsonPack.toJson(cls);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

}
