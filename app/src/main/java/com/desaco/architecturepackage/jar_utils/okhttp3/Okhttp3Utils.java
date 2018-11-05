//package com.desaco.architecturepackage.jar_utils.okhttp3;
//
//import android.util.Log;
//import android.widget.Toast;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Cache;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
///**
// * Created by desaco on 2018/11/5.
// * Okhttp3 的Socket。
// * volley是一个简单的异步http库，仅此而已。缺点是不支持同步，这点会限制开发模式；不能post大数据，所以不适合用来上传文件
// * android-async-http。与volley一样是异步网络库，但volley是封装的httpUrlConnection，它是封装的httpClient，而android平台不推荐用HttpClient了，所以这个库已经不适合android平台了。
// * okhttp是高性能的http库，支持同步、异步，而且实现了spdy、http2、websocket协议，api很简洁易用，和volley一样实现了http协议的缓存。
// *
// * https://github.com/henrymorgen/MoonOkHttp3
// */
//
//public class Okhttp3Utils {
//
//    public static final MediaType MEDIA_TYPE_MARKDOWN
//            = MediaType.parse("text/x-markdown; charset=utf-8");
//    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//
//    private OkHttpClient mOkHttpClient;
//
//    private void initOkHttpClient() {
//        File sdcache = getExternalCacheDir();
//        int cacheSize = 10 * 1024 * 1024;
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .connectTimeout(15, TimeUnit.SECONDS)
//                .writeTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
//                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
//        mOkHttpClient = builder.build();
//    }
//
//    /**
//     * get异步请求
//     */
//    private void getAsynHttp() {
//
//        Request.Builder requestBuilder = new Request.Builder().url("http://www.baidu.com");
//        requestBuilder.method("GET", null);
//        Request request = requestBuilder.build();
//        Call mcall = mOkHttpClient.newCall(request);
//        mcall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (null != response.cacheResponse()) {
//                    String str = response.cacheResponse().toString();
//                    Log.i("wangshu", "cache---" + str);
//                } else {
//                    response.body().string();
//                    String str = response.networkResponse().toString();
//                    Log.i("wangshu", "network---" + str);
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * post异步请求
//     */
//    private void postAsynHttp() {
//        RequestBody formBody = new FormBody.Builder()
//                .add("size", "10")
//                .build();
//        Request request = new Request.Builder()
//                .url("http://api.1-blog.com/biz/bizserver/article/list.do")
//                .post(formBody)
//                .build();
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i("wangshu", str);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//        });
//    }
//
//    /**
//     * 异步上传文件
//     */
//    private void postAsynFile() {
//        File file = new File("/sdcard/wangshu.txt");
//        Request request = new Request.Builder()
//                .url("https://api.github.com/markdown/raw")
//                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
//                .build();
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("wangshu", response.body().string());
//            }
//        });
//    }
//
//
//    /**
//     * 异步下载文件
//     */
//    private void downAsynFile() {
//        String url = "http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg";
//        Request request = new Request.Builder().url(url).build();
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                InputStream inputStream = response.body().byteStream();
//                FileOutputStream fileOutputStream = null;
//                try {
//                    fileOutputStream = new FileOutputStream(new File("/sdcard/wangshu.jpg"));
//                    byte[] buffer = new byte[2048];
//                    int len = 0;
//                    while ((len = inputStream.read(buffer)) != -1) {
//                        fileOutputStream.write(buffer, 0, len);
//                    }
//                    fileOutputStream.flush();
//                } catch (IOException e) {
//                    Log.i("wangshu", "IOException");
//                    e.printStackTrace();
//                }
//
//                Log.d("wangshu", "文件下载成功");
//            }
//        });
//    }
//
//    private void sendMultipart() {
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "wangshu")
//                .addFormDataPart("image", "wangshu.jpg",
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
//                .build();
//
//        Request request = new Request.Builder()
//                .header("Authorization", "Client-ID " + "...")
//                .url("https://api.imgur.com/3/image")
//                .post(requestBody)
//                .build();
//
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("wangshu", response.body().string());
//            }
//        });
//    }
//}
