package com.test.trainticket.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/30.
 */

public class NetWorks {


    public static void get(String url, HashMap<String, String> map, final IStringCallBack callBack) {
        OkGo.<String>get(url)
                .params(map)
                .headers("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/59.0.3071.115 Safari/537.36")
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


}
