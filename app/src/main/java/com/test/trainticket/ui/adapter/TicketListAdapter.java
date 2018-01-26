package com.test.trainticket.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.test.trainticket.R;
import com.test.trainticket.model.TicketCsModel;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25.
 */

public class TicketListAdapter extends CommonAdapter<TicketCsModel> {

    public TicketListAdapter(Context context, int layoutId, List<TicketCsModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, TicketCsModel item, int position) {
        TextView txt_start_time = viewHolder.getView(R.id.txt_start_time);
        TextView txt_start_name = viewHolder.getView(R.id.txt_start_name);
        TextView txt_piao1 = viewHolder.getView(R.id.txt_piao1);

        TextView txt_need_time = viewHolder.getView(R.id.txt_need_time);
        TextView txt_train_code = viewHolder.getView(R.id.txt_train_code);
        TextView txt_piao2 = viewHolder.getView(R.id.txt_piao2);

        TextView txt_end_time = viewHolder.getView(R.id.txt_end_time);
        TextView txt_end_name = viewHolder.getView(R.id.txt_end_name);
        TextView txt_piao3 = viewHolder.getView(R.id.txt_piao3);

        TextView txt_tips = viewHolder.getView(R.id.txt_tips);

        txt_start_time.setText(item.queryLeftNewDTO.start_time);
        txt_start_name.setText(item.queryLeftNewDTO.from_station_name);


        txt_need_time.setText("耗时:" + item.queryLeftNewDTO.lishi);
        txt_train_code.setText(item.queryLeftNewDTO.station_train_code);


        txt_end_time.setText(item.queryLeftNewDTO.arrive_time);
        txt_end_name.setText(item.queryLeftNewDTO.to_station_name);

        TextView txt_price = viewHolder.getView(R.id.txt_price);//预定

        if (!TextUtils.isEmpty(item.buttonTextInfo) && !item.buttonTextInfo.equals("预订")) {
            txt_piao1.setVisibility(View.GONE);
            txt_piao2.setVisibility(View.GONE);
            txt_piao3.setVisibility(View.GONE);
            txt_tips.setVisibility(View.VISIBLE);
            txt_tips.setText(item.buttonTextInfo);
            txt_price.setVisibility(View.INVISIBLE);
        } else {
            txt_piao1.setText("硬座:" + item.queryLeftNewDTO.yz_num);
            txt_piao2.setText("硬卧:" + item.queryLeftNewDTO.yw_num);
            txt_piao3.setText("无座:" + item.queryLeftNewDTO.wz_num);
            txt_tips.setVisibility(View.GONE);
            txt_piao1.setVisibility(View.VISIBLE);
            txt_piao2.setVisibility(View.VISIBLE);
            txt_piao3.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(item.buttonTextInfo) && item.buttonTextInfo.equals("预订")) {
            txt_price.setVisibility(View.VISIBLE);
            if ("Y".equals(item.queryLeftNewDTO.canWebBuy)) {
                txt_price.setTextColor(Color.parseColor("#ff8000"));
            } else {
                txt_price.setTextColor(Color.parseColor("#666666"));
            }

        }


    }

}

