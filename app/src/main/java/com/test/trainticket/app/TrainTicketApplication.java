package com.test.trainticket.app;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/1/24.
 */

public class TrainTicketApplication extends Application {

    private static TrainTicketApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initOkGo();
    }

    public static TrainTicketApplication getInstance() {
        return instance;
    }

    /**
     * 初始化okgo
     */
    private void initOkGo() {


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");

        loggingInterceptor.setColorLevel(Level.INFO);//log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);//添加OkGo默认debug日志

        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);

        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        //超时时间设置，默认60秒
        builder.readTimeout(30 * 1000, TimeUnit.MILLISECONDS); //全局的读取超时时间
        builder.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS); //全局的写入超时时间
        builder.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);//全局的连接超时时间

        OkGo.getInstance().init(this)  //必须调用初始化
                .setOkHttpClient(builder.build()) //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE) //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE) //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3); //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }


}
