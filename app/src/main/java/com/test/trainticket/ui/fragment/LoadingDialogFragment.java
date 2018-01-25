package com.test.trainticket.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.test.trainticket.R;


/**
 * Created by Administrator on 2017/11/29.
 */

public class LoadingDialogFragment extends Dialog {

    public LoadingDialogFragment(Context context){
        super(context, R.style.LoadingDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        setContentView(inflater.inflate(R.layout.dialog_loading_layout,null));
        setCanceledOnTouchOutside(false);
    }
}


