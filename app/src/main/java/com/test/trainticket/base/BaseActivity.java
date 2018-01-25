package com.test.trainticket.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.test.trainticket.ui.fragment.LoadingDialogFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    // 数据加载时显示的progress
    private LoadingDialogFragment mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
    }



    /**
     * 显示自定义等待层
     */
    protected void showLoadProgress() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialogFragment(this);
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏自定义等待层
     */
    protected void closeLoadingProgressBar() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

}
