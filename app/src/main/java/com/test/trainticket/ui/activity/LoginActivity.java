package com.test.trainticket.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okgo.model.Response;
import com.test.trainticket.R;
import com.test.trainticket.base.BaseActivity;
import com.test.trainticket.common.Constants;
import com.test.trainticket.model.LoginModel;
import com.test.trainticket.model.ResultModel;
import com.test.trainticket.net.IStringCallBack;
import com.test.trainticket.net.NetWorks;
import com.test.trainticket.ui.adapter.ItemAdapter;
import com.test.trainticket.utils.EditUtils;
import com.test.trainticket.utils.ImageViewUtils;
import com.test.trainticket.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.img_yzm)
    ImageView img_yzm;

    @Bind(R.id.edit_name)
    EditText edit_name;

    @Bind(R.id.edit_pwd)
    EditText edit_pwd;

    @Bind(R.id.gridView)
    GridView gridView;

    @Bind(R.id.yzm_progressbar)
    ProgressBar yzm_progressbar;

    private ItemAdapter mAdapter;

    private List<String> yanList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        yanList = new ArrayList<>();
        loadInitPage();//加载验证码

    }

    /**
     * 初始化GridView
     */
    private void setGridView() {
        mAdapter = new ItemAdapter(this, new ArrayList<String>(Arrays.asList(Constants.YZM_COORDINATES)));
        gridView.setAdapter(mAdapter);
    }


    /**
     * 验证码点击
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.gridView)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (view.getTag() != null && !TextUtils.isEmpty(view.getTag().toString())) {
            if (view.getTag().toString().equals("1")) {
                //取消
                view.setTag("0");
                view.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent));//设置透明色
                yanList.remove(Constants.YZM_COORDINATES[position]);
            } else {
                //选中
                view.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.translucent));//选中粉红色
                yanList.add(Constants.YZM_COORDINATES[position]);
                view.setTag("1");
            }
        } else {
            //选中
            view.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.translucent));//选中粉红色
            yanList.add(Constants.YZM_COORDINATES[position]);
            view.setTag("1");
        }
    }


    @OnClick({R.id.view_refresh, R.id.btn_login})
    void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.view_refresh:
                loadInitPage();
                break;
            case R.id.btn_login:
                checkYzm();
                break;
        }
    }

    /**
     * 加载页面
     */
    private void loadInitPage() {
        //设置验证码为空背景
        img_yzm.setImageResource(R.mipmap.yzm_bg);
        ImageViewUtils.matchAll(LoginActivity.this, img_yzm);

        setGridView();

        yanList.clear();//清除已选

        //清除cookie
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeAllCookie();

        yzm_progressbar.setVisibility(View.VISIBLE);
        NetWorks.get(Constants.INITPAGE_URL, new HashMap<String, String>(), new IStringCallBack() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onErrorResponse(String error) {

            }

            @Override
            public void onFinish() {
                loadYzmImg();
            }
        });

    }

    /**
     * 加载12306变态验证码
     */
    private void loadYzmImg() {

        //获取图片的需求少 这里就直接调用okgo下载图片 懒得封装方法了
        OkGo.<Bitmap>get(Constants.CAPTCHAIMAGE_URL + System.currentTimeMillis())//加上随机数 防止出现缓存
                .tag(this)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        img_yzm.setImageDrawable(new BitmapDrawable(response.body()));
                        ImageViewUtils.matchAll(LoginActivity.this, img_yzm);
                        setGridView();
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        super.onError(response);
                        Log.e("loadYzmImg", "加载12306变态验证码出错" + response.message());
                        yzm_progressbar.setVisibility(View.GONE);
                        img_yzm.setImageResource(R.mipmap.loaderror);
                        ImageViewUtils.matchAll(LoginActivity.this, img_yzm);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        yzm_progressbar.setVisibility(View.GONE);
                    }
                });
    }


    /**
     * 验证验证码
     */
    private void checkYzm() {

        if (!EditUtils.hasInput(edit_name)) {
            Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!EditUtils.hasInput(edit_pwd)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (yanList == null || yanList.size() == 0) {
            Toast.makeText(LoginActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String strYan = "";
        for (String item : yanList) {
            strYan += item + ",";
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("login_site", "E");//固定参数 不知道要干嘛的
        map.put("rand", "sjrand");//固定参数 不知道要干嘛的
        map.put("answer", strYan);//拼接出来的坐标数据

        showLoadProgress();
        NetWorks.post(Constants.CAPTCHACHECK_URL, map, new IStringCallBack() {
            @Override
            public void onResponse(String response) {
                ResultModel result = JsonUtils.getModel(response, ResultModel.class);
                if (result == null) {
                    Toast.makeText(LoginActivity.this, "验证验证码出错 请重试", Toast.LENGTH_SHORT).show();
                    loadInitPage();
                    return;
                }

                //4：成功  其他：错误
                if (result.getResult_code().equals("4")) {
                    login();
                } else {
                    Toast.makeText(LoginActivity.this, result.getResult_message(), Toast.LENGTH_SHORT).show();
                    loadInitPage();
                    closeLoadingProgressBar();
                }

            }

            @Override
            public void onErrorResponse(String error) {
                Toast.makeText(LoginActivity.this, "验证验证码请求出错:" + error, Toast.LENGTH_SHORT).show();
                loadInitPage();
                closeLoadingProgressBar();
            }

            @Override
            public void onFinish() {

            }
        });

    }


    /**
     * 登录
     */
    private void login() {

        HashMap<String, String> map = new HashMap<>();
        map.put("username", edit_name.getText().toString());
        map.put("password", edit_pwd.getText().toString());
        map.put("appid", "otn");

        showLoadProgress();
        NetWorks.post(Constants.LOGIN_URL, map, new IStringCallBack() {
            @Override
            public void onResponse(String response) {
                LoginModel result = JsonUtils.getModel(response, LoginModel.class);
                //0：成功

                //{"result_message":"登录成功","result_code":0,
                // "uamtk":"yH89oXpBLREqjUYKGp1z0nipeSHaXTkz8XwGMWwD1JbPabRbmk9190"}

                if (result == null) {
                    Toast.makeText(LoginActivity.this, "登录出错 请重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result.getResult_code().equals("0")) {
                    Toast.makeText(LoginActivity.this, "恭喜你 登录成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, result.getResult_message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onErrorResponse(String error) {
                Toast.makeText(LoginActivity.this, "登录请求出错:" + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                closeLoadingProgressBar();
            }
        });


    }


}
