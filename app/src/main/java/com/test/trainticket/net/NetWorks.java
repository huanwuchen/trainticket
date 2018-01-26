package com.test.trainticket.net;

import android.app.Activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/30.
 */

public class NetWorks {

    public static final String UA = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/50.0.2661.102 Safari/537.36";

    public static void get(Activity mActivity, String url, HashMap<String, String> map, final IStringCallBack callBack) {

        HttpHeaders httpHeaders = getHeaders();
        OkGo.<String>get(url)
                .params(map)
                .headers(httpHeaders)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        callBack.onResponse(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBack.onErrorResponse(response.body());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        callBack.onFinish();
                    }
                });

    }

    public static void post(String url, HashMap<String, String> map, final IStringCallBack callBack) {
        OkGo.<String>post(url)
                .params(map)
                .headers("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/59.0.3071.115 Safari/537.36")
                .headers("Accept", "application/json, text/javascript, */*; q=0.01")
                .headers("Referer", "https://kyfw.12306.cn/otn/login/init")
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        callBack.onResponse(response.body());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBack.onErrorResponse(response.body());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        callBack.onFinish();
                    }
                });

    }


    public static HttpHeaders getHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", UA);
        headers.put("Connection", "keep-alive");
        headers.put("Host", "kyfw.12306.cn");
        headers.put("Connection", "keep-alive");
        headers.put("Referer", "https://kyfw.12306.cn/otn/leftTicket/init");
        return headers;
    }


}
