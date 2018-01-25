package com.test.trainticket.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.trainticket.R;
import com.test.trainticket.base.BaseActivity;


public class TitleBar extends LinearLayout implements View.OnClickListener {

    private LayoutInflater inflater;

    private Context context;

    private ImageView img_logo;// 公司图标

    private ImageView btn_back;// 回退键

    private View btn_right_view;
    private TextView btn_right;

    private View btn_right_view2;
    private TextView btn_right2;

    private TextView txt_title;


    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initView();
        TypedArray tArray = context.obtainStyledAttributes(attr, R.styleable.titlebarattr);
        txt_title.setText(tArray.getString(R.styleable.titlebarattr_titlestr));
        if (tArray.getBoolean(R.styleable.titlebarattr_ifshowlogo, false)) {
            showLogo();
        }

        if (!tArray.getBoolean(R.styleable.titlebarattr_ifshowleftbutton, true)) {
            hideLeftButton();
        }

        tArray.recycle();
    }

    private void initView() {
        inflater = LayoutInflater.from(context);

        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.title_bar, this, false);

        addView(view);
        img_logo = (ImageView) view.findViewById(R.id.titlebar_img_logo);
        btn_back = (ImageView) view.findViewById(R.id.titlebar_btn_back);
        txt_title = (TextView) view.findViewById(R.id.titlebar_txt_title);
        btn_right_view = view.findViewById(R.id.btn_right_view);
        btn_right = (TextView) view.findViewById(R.id.btn_right);

        btn_right_view2 = view.findViewById(R.id.btn_right_view2);
        btn_right2 = (TextView) view.findViewById(R.id.btn_right2);

        btn_back.setOnClickListener(this);
    }


    public interface BackListener {
        boolean onBack();
    }

    BackListener listener;

    public void setBackListener(BackListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_btn_back:
                if (listener != null) {
                    if (listener.onBack()) {
                        ((BaseActivity) context).finish();
                    }
                } else {
                    ((BaseActivity) context).finish();
                }
                break;
        }
    }

    public void showLogo() {
        img_logo.setVisibility(View.VISIBLE);
        btn_back.setVisibility(View.GONE);
    }

    public void hideLeftButton() {
        btn_back.setVisibility(View.GONE);
    }

    public void setTitle(String title) {
        txt_title.setText(title);
    }

    public void setTitle(int resid) {
        txt_title.setText(resid);
    }

    public void showRightBtn(boolean isShow) {
        btn_right.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setRightBtnText(String str) {
        btn_right.setText(str);
    }

    public void setRightClick(OnClickListener listener) {
        btn_right_view.setOnClickListener(listener);
    }

    public View getRightBtn() {
        return btn_right_view;
    }

    public void setRightBtnRes(int res) {
        btn_right.setText("");
        Drawable draw = ContextCompat.getDrawable(context, res);
        btn_right.setCompoundDrawablesWithIntrinsicBounds(null, null, draw, null);
    }

    public void setRightBtn2Res(int res) {
        btn_right2.setVisibility(View.VISIBLE);
        btn_right2.setText("");
        Drawable draw = ContextCompat.getDrawable(context, res);
        btn_right2.setCompoundDrawablesWithIntrinsicBounds(null, null, draw, null);
    }

    public void setRight2Click(OnClickListener listener) {
        btn_right_view2.setOnClickListener(listener);
    }

}

