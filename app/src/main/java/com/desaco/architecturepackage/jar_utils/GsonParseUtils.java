package com.desaco.architecturepackage.jar_utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by desaco on 2018/11/4.
 */


/**
 * Created by desaco on 2018/11/3.
 * <p>
 * Gson的反射 泛型等，泛型擦除
 * Jackson就是一个很有名的XML/JSON解析库。Gson与这些库的不同之处，表现在它的两个重要设计目标：
 * 即使你无法修改源代码，你也能通过Gson对代码中的类做解析和变换。 充分支持Java泛型。
 * <p>
 * android 中Gson的使用和封装-https://blog.csdn.net/qq_29293605/article/details/80854500
 * Android Gson的使用总结- https://www.cnblogs.com/zhaoyanjun/p/5842601.html
 * Android Gson使用详解- https://www.jianshu.com/p/0444693c2639
 */

public class GsonParseUtils<T> {
    private GsonParseUtils() {
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

    public void useGson() {
        Gson gson = new GsonBuilder()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting()
                .create();
    }

    public void getGson() {
        GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();
        mGsonPack = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();//.setFieldNamingStrategy(new AnnotateNaming()).create();
    }

    //把json 字符串转化成list
    public static <T> List<T> stringToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

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

    public String parseMapToJson(Map<?, ?> map) {
        try {
            return mGsonPack.toJson(map);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 把一个json字符串变成对象
     *
     * @param json
     * @param cls
     * @return
     */
    public <T> T parseJsonToBean(String json, Class<T> cls) {
        T t = null;
        try {
            t = mGsonPack.fromJson(json, cls);
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
    public HashMap<String, Object> parseJsonToMap(String json) {
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> map = null;
        try {
            map = mGsonPack.fromJson(json, type);
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public List<?> parseJsonToList(String json, Type type) {
        List<?> list = mGsonPack.fromJson(json, type);
        return list;
    }

    /**
     * 获取json串中某个字段的值，注意，只能获取同一层级的value
     *
     * @param json
     * @param key
     * @return
     */
    public String getFieldValue(String json, String key) {
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
     * 格式化json
     *
     * @param uglyJSONString
     * @return
     */
    public String jsonFormatter(String uglyJSONString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }

//    public <T> json2Bean(String json, Class<?> clazz) {
//        return mGsonPack.fromJson(json, clazz);
//    }
//    public String bean2Json(Class<?> clazz) {
//        String json = mGsonPack.toJson(clazz);
//        return json;
//    }

}
