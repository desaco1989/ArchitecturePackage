//package com.desaco.architecturepackage.secondary_package;
//
//import com.google.gson.Gson;
//
///**
// * Created by desaco on 2018/11/3.
// */
//
//public class GsonUtils {
//    private GsonUtils() {
//    }
//
//    /**
//     * // data 为 object 的情况
//     {"code":"0","message":"success","data":{}}
//     // data 为 array 的情况
//     {"code":"0","message":"success","data":[]}
//     */
//    private static Gson mGsonPack;
//
//    private static Gson gsonInstance() {
//        if (mGsonPack == null) {
//            mGsonPack = new Gson();
//        }
//        return mGsonPack;
//    }
//
////    public <T> json2Bean(String json, Class<?> clazz) {
////        return mGsonPack.fromJson(json, clazz);
////    }
//
//    public String bean2Json(Class<?> clazz) {
//        String json = mGsonPack.toJson(clazz);
//        return json;
//    }
//
//}
