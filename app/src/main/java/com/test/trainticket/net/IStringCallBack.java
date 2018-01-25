package com.test.trainticket.net;

/**
 * Created by Administrator on 2017/11/30.
 */

public interface IStringCallBack {
    void onResponse(String response);

    void onErrorResponse(String error);

    void onFinish();
}
