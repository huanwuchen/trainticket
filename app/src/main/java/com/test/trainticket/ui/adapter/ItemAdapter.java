package com.test.trainticket.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.test.trainticket.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ItemAdapter extends BaseAdapter {

    private List<String> yanSol;
    private Context mContext;


    public ItemAdapter(Context mContext, List<String> yanSol) {
        this.yanSol = yanSol;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return yanSol.size();
    }

    @Override
    public String getItem(int position) {
        return yanSol.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView=View.inflate(mContext, R.layout.item_view,null);
        return convertView;
    }
}
