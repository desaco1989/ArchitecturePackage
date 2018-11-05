package com.desaco.architecturepackage.jar_utils.okhttp3;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Config;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by desaco on 2018/11/5.
 * <p>
 * 支持HTTP2/SPDY（SPDY是Google开发的基于TCP的传输层协议，用以最小化网络延迟，提升网络速度，优化用户的网络使用体验。）
 * socket自动选择最好路线，并支持自动重连，拥有自动维护的socket连接池，减少握手次数，减少了请求延迟，共享Socket,减少对服务器的请求次数。
 * 基于Headers的缓存策略减少重复的网络请求。
 * 拥有Interceptors轻松处理请求与响应（自动处理GZip压缩）。
 * <p>
 * OkHttp是一款优秀的HTTP框架，它支持get请求和post请求，支持基于Http的文件上传和下载，支持加载图片，支持下载文件透明的GZIP压缩，
 * 支持响应缓存避免重复的网络请求，支持使用连接池来降低响应延迟问题。
 */

public class OkHttpManager {


    private static final String TAG = OkHttpManager.class.getSimpleName();
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    //下面两个随便用哪个都可以
    //    private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("image/jpg");
    private static volatile OkHttpManager mInstance;//单利引用
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    /**
     * 初始化OkHttpManager
     */
    public OkHttpManager(Context context) {
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
        okHttpHandler = new Handler(context.getMainLooper());
    }


    /**
     * 获取单例引用
     *
     * @return
     */
    public static OkHttpManager getInstance(Context context) {
        OkHttpManager inst = mInstance;
        if (inst == null) {
            synchronized (OkHttpManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new OkHttpManager(context.getApplicationContext());
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                //addHeader，可添加多个请求头  header，唯一，会覆盖
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0")
                .header("sid", "eyJhZGRDaGFubmVsIjoiYXBwIiwiYWRkUHJvZHVjdCI6InFia3BsdXMiLCJhZGRUaW1lIjoxNTAzOTk1NDQxOTEzLCJyb2xlIjoiUk9MRV9VU0VSIiwidXBkYXRlVGltZSI6MTUwMzk5NTQ0MTkxMywidXNlcklkIjoxNjQxMTQ3fQ==.b0e5fd6266ab475919ee810a82028c0ddce3f5a0e1faf5b5e423fb2aaf05ffbf");
        return builder;
    }

    /**
     * 1.1 okHttp get同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    public void getBySyn(String actionUrl, Map<String, Object> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key).toString(), "utf-8")));
                pos++;
            }
            //补全请求地址  这里的Config.SERVER_ADDRESS改成自己的BaseUrl
            String requestUrl = String.format("%s%s?%s", "Config.SERVER_ADDRESS", actionUrl, tempParams.toString());
            //创建一个请求
            Request request = addHeaders().url(requestUrl).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            final Response response = call.execute();
            response.body().string();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 1.2 okHttp post同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    public void postBySyn(String actionUrl, Map<String, Object> paramsMap) {
        try {
            //处理参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key).toString(), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s%s", "Config.SERVER_ADDRESS", actionUrl);
            //生成参数
            String params = tempParams.toString();
            //创建一个请求实体对象 RequestBody
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            //创建一个请求
            final Request request = addHeaders().url(requestUrl).post(body).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            //请求执行成功
            if (response.isSuccessful()) {
                //获取返回数据 可以是String，bytes ,byteStream
                Log.e(TAG, "response ----->" + response.body().string());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 1.3 okHttp post同步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private void postBySynWithForm(String actionUrl, Map<String, Object> paramsMap) {
        try {
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key, paramsMap.get(key).toString());
            }
            //生成表单实体对象
            RequestBody formBody = builder.build();
            //补全请求地址
            String requestUrl = String.format("%s%s", "Config.SERVER_ADDRESS", actionUrl);
            //创建一个请求
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            if (response.isSuccessful()) {
                Log.e(TAG, "response ----->" + response.body().string());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 2.1 okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @return
     */
    public void getByAsyn(String actionUrl, Map<String, Object> paramsMap, final OkHttpCallback callBack) {
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key).toString(), "utf-8")));
                pos++;
            }
            //http://117.78.49.154:18083/userproduct/api/4.0.0/config/getConfig
            String requestUrl = String.format("%s%s?%s", "Config.SERVER_ADDRESS", actionUrl, tempParams.toString());
            Log.i(TAG, "requestUrl=" + requestUrl);
            final Request request = addHeaders().url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    onFailedCallBack(e, callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            Log.i(TAG, "onResponse response=" + response);
                            String responseStr = response.body().string();
                            Log.i(TAG, "onResponse responseStr=" + responseStr);
                            JSONObject oriData = new JSONObject(responseStr.trim());
//                            ServerResponse serverResponse = ServerResponse.parseFromResponse(oriData);
//                            //回调
//                            onSuccessCallBack(oriData, serverResponse, callBack);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 2.2 okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @return
     */
    public void postByAsyn(String actionUrl, Map<String, Object> paramsMap, final OkHttpCallback callBack) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key).toString(), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            String requestUrl = String.format("%s%s", "Config.SERVER_ADDRESS", actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    onFailedCallBack(e, callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            String responseStr = response.body().string();
                            Log.i(TAG, "onResponse responseStr=" + responseStr);
                            JSONObject oriData = new JSONObject(responseStr.trim());
//                            ServerResponse serverResponse = ServerResponse.parseFromResponse(oriData);
//                            onSuccessCallBack(oriData, serverResponse, callBack);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 2.3 okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @return
     */
    public void postByAsynWithForm(String actionUrl, Map<String, Object> paramsMap, final OkHttpCallback callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key).toString());
            }
            RequestBody formBody = builder.build();
            String requestUrl = String.format("%s%s", "Config.SERVER_ADDRESS", actionUrl);
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    onFailedCallBack(e, callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            String responseStr = response.body().string();
                            Log.i(TAG, "onResponse responseStr=" + responseStr);
                            JSONObject oriData = new JSONObject(responseStr.trim());
//                            ServerResponse serverResponse = ServerResponse.parseFromResponse(oriData);
//                            onSuccessCallBack(oriData, serverResponse, callBack);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    //上传图片 带参数
    public void postFiles(String actionUrl, Map<String, Object> paramsMap, Map<String, File> fileMap, final OkHttpCallback callBack) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        //入参-字符串
        for (Map.Entry entry : paramsMap.entrySet()) {
            requestBody.addFormDataPart(entry.getKey().toString(), entry.getValue().toString());
        }
        //入参-文件
        for (Map.Entry entry : fileMap.entrySet()) {
            File file = (File) entry.getValue();
            RequestBody fileBody = RequestBody.create(MEDIA_TYPE_FILE, file);
            String fileName = file.getName();
            requestBody.addFormDataPart(entry.getKey().toString(), fileName, fileBody);
        }

        Request request = addHeaders().url(actionUrl).post(requestBody.build()).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                onFailedCallBack(e, callBack);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse response=" + response);
                if (response.isSuccessful()) {
                    try {
                        String responseStr = response.body().string();
                        Log.i(TAG, "onResponse responseStr=" + responseStr);
                        JSONObject oriData = new JSONObject(responseStr.trim());
//                        ServerResponse serverResponse = ServerResponse.parseFromResponse(oriData);
//                        onSuccessCallBack(oriData, serverResponse, callBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
