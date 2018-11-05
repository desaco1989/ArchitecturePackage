package com.desaco.architecturepackage.jar_utils.okhttp3;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by desaco on 2018/11/5.
 */

public interface OkHttpCallback {
    /**
     * 响应成功
     */
//    void onSuccess(JSONObject oriData, ServerResponse response);


    /**
     * 响应失败
     */
    void onFailure(IOException e);

}
