package com.test.trainticket.model;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ResultModel{


    /**
     * result_message : 验证码校验失败,信息为空
     * result_code : 8
     */

    private String result_message;
    private String result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }
}
