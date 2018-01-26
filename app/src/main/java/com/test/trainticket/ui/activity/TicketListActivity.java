package com.test.trainticket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.test.trainticket.R;
import com.test.trainticket.base.BaseActivity;
import com.test.trainticket.common.Constants;
import com.test.trainticket.model.LeftTicketModel;
import com.test.trainticket.model.QueryLeftNewDTOModel;
import com.test.trainticket.model.ResultModel;
import com.test.trainticket.model.StationDao;
import com.test.trainticket.model.TicketCsModel;
import com.test.trainticket.net.IStringCallBack;
import com.test.trainticket.net.NetWorks;
import com.test.trainticket.ui.adapter.TicketListAdapter;
import com.test.trainticket.utils.AppUtils;
import com.test.trainticket.utils.EditUtils;
import com.test.trainticket.utils.JsonUtils;
import com.test.trainticket.utils.StationUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 余票列表查询
 */
public class TicketListActivity extends BaseActivity {

    @Bind(R.id.txt_date)
    TextView txt_date;

    @Bind(R.id.edit_from)
    EditText edit_from;

    @Bind(R.id.edit_end)
    EditText edit_end;

    @Bind(R.id.cb_type)
    CheckBox cb_type;

    @Bind(R.id.listview)
    ListView listview;

    private TicketListAdapter ticketListAdapter;

    private TimePickerView birthdayOptions;

    StationUtils stationUtils;

    private List<TicketCsModel> ticketCsModels;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket_list;
    }

    @Override
    protected void initView() {
        ticketCsModels = new ArrayList<>();
        ticketListAdapter = new TicketListAdapter(TicketListActivity.this, R.layout.activity_ticket_list_item,
                ticketCsModels);
        listview.setAdapter(ticketListAdapter);

        stationUtils = new StationUtils();
        initBirthdayOptions();
        loadStations();
        // queryLogin();
    }


    public void queryLogin() {
        showLoadProgress();
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", "otn");
        NetWorks.post("https://kyfw.12306.cn/passport/web/auth/uamtk", map, new IStringCallBack() {
            @Override
            public void onResponse(String response) {
                ResultModel result = JsonUtils.getModel(response, ResultModel.class);

                if (result == null) {
                    Toast.makeText(TicketListActivity.this, "登录出错 正在重新刷新验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result.getResult_code().equals("1")) {
                    Toast.makeText(TicketListActivity.this, result.getResult_message(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TicketListActivity.this, LoginActivity.class);
                    intent.putExtra("flag", 1);
                    startActivity(intent);
                }else {

                }

            }

            @Override
            public void onErrorResponse(String error) {

            }

            @Override
            public void onFinish() {
                closeLoadingProgressBar();
            }
        });


    }




    int successCount = 0;

    private void queryTicket(String strDate, String strFrom, String strTo, String strCodes) {

        //https://kyfw.12306.cn/otn/leftTicket/queryZ
        // ?leftTicketDTO.train_date=2018-02-21&leftTicketDTO.from_station=GZG&leftTicketDTO.to_station=SZQ&purpose_codes=ADULT

        HashMap<String, String> map = new HashMap<>();
        map.put("leftTicketDTO.train_date", strDate);//日期
        map.put("leftTicketDTO.from_station", strFrom);//出发地 赣州
        map.put("leftTicketDTO.to_station", strTo);//目的地 深圳
        map.put("purpose_codes", strCodes);//是否学生票 普通：ADULT  学生：0X00


        String url = "https://kyfw.12306.cn/otn/leftTicket/queryZ" + "?leftTicketDTO.train_date=" + strDate +
                "&leftTicketDTO.from_station=" + strFrom + "&leftTicketDTO.to_station=" +
                strTo + "&purpose_codes=" + strCodes;

        NetWorks.get(this, url, new HashMap<String, String>(), new IStringCallBack() {
            @Override
            public void onResponse(String response) {

                LeftTicketModel model = JsonUtils.getModel(response, LeftTicketModel.class);

                if (model == null) {
                    if (successCount < 10) {
                        successCount++;
                        query();
                    } else {
                        closeLoadingProgressBar();
                        ticketCsModels.clear();
                        ticketListAdapter.notifyDataSetChanged();
                        Toast.makeText(TicketListActivity.this, "查询余票列表失败 请重试", Toast.LENGTH_SHORT).show();
                        successCount = 0;
                    }
                    return;
                }

                closeLoadingProgressBar();
                successCount = 0;

                if (model.getData() == null || model.getData().getMap() == null ||
                        model.getData().getResult() == null || model.getData().getResult().size() == 0) {
                    Toast.makeText(TicketListActivity.this, "没有查询到余票信息", Toast.LENGTH_SHORT).show();
                    ticketCsModels.clear();
                    ticketListAdapter.notifyDataSetChanged();
                }

                try {
                    List<TicketCsModel> tempList = parseData(model.getData().getResult(), model.getData().getMap());
                    Log.e("tempList", JsonUtils.toString(tempList));
                    ticketCsModels.clear();
                    ticketCsModels.addAll(tempList);
                    ticketListAdapter.notifyDataSetChanged();
                    //返回顶部
                    listview.setSelectionAfterHeaderView();
                    listview.smoothScrollToPosition(0);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e("ticketListAdapter", "解析余票列表数据出错");
                    Toast.makeText(TicketListActivity.this, "解析余票列表数据出错 请重试", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onErrorResponse(String error) {
                closeLoadingProgressBar();
            }

            @Override
            public void onFinish() {

            }
        });


    }

    @OnClick({R.id.txt_date, R.id.btn_query, R.id.txt_conversion})
    void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.txt_date:
                AppUtils.hideInput(this);
                if (birthdayOptions != null) {
                    birthdayOptions.show();
                }
                break;
            case R.id.btn_query:
                AppUtils.hideInput(this);
                query();
                break;
            case R.id.txt_conversion:
                AppUtils.hideInput(this);
                String from_station = edit_from.getText().toString().trim();
                String to_station = edit_end.getText().toString().trim();
                edit_from.setText(to_station);
                edit_end.setText(from_station);
                break;
        }
    }

    private void query() {

        if (!EditUtils.hasInput(edit_from)) {
            Toast.makeText(this, "出发地不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!EditUtils.hasInput(edit_end)) {
            Toast.makeText(this, "目的地不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        String from_station = edit_from.getText().toString().trim();
        StationDao fromDao = stationUtils.query(from_station);

        if (fromDao == null) {
            Toast.makeText(this, "输入的出发地有错误 请检查", Toast.LENGTH_SHORT).show();
            return;
        }

        String to_station = edit_end.getText().toString().trim();
        StationDao toDao = stationUtils.query(to_station);

        if (toDao == null) {
            Toast.makeText(this, "输入的目的地有错误 请检查", Toast.LENGTH_SHORT).show();
            return;
        }

        from_station = fromDao.getCode();
        to_station = toDao.getCode();
        showLoadProgress();
        loadInitPage(txt_date.getText().toString(), from_station, to_station,
                cb_type.isChecked() ? "0X00" : "ADULT");


    }


    /**
     * 加载页面
     */
    private void loadInitPage(final String strDate, final String strFrom, final String strTo, final String strCodes) {

  /*      CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeAllCookie();
*/
        NetWorks.get(this, Constants.INITPAGEQUERY_URL, new HashMap<String, String>(), new IStringCallBack() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onErrorResponse(String error) {

            }

            @Override
            public void onFinish() {
                queryTicket(strDate, strFrom, strTo, strCodes);
            }
        });

    }


    private void loadStations() {

        StationDao stationDao = stationUtils.query("北京");

        if (stationDao != null) {
            return;
        }

        showLoadProgress();
        NetWorks.get(this, "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.9046", new HashMap
                <String, String>(), new IStringCallBack() {
            @Override
            public void onResponse(final String response) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setStations(response);
                    }
                }).start();

            }

            @Override
            public void onErrorResponse(String error) {

            }

            @Override
            public void onFinish() {
                closeLoadingProgressBar();
            }
        });
    }

    private void setStations(String response) {
        String stations = response.replaceAll("\\s+", "");

        Pattern pName = Pattern.compile("[\u4e00-\u9fa5]+");
        Pattern pUpper = Pattern.compile("[A-Z]+");
        Matcher mName = pName.matcher(stations);
        Matcher mUpper = pUpper.matcher(stations);

        while (mName.find() && mUpper.find()) {

            String name = mName.group(0);
            String upper = mUpper.group(0);

            StationDao station = new StationDao(null, name, upper);
            stationUtils.insert(station);
        }

        Log.d("TicketListActivity", "插入数据库成功");
    }


    /**
     * 初始化
     */
    private void initBirthdayOptions() {

        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month;
        int date = t.monthDay;

        txt_date.setText(year + "-0" + (month + 1) + "-" + date);

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(year, month, date);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2018, 12, 31);
        //时间选择器
        birthdayOptions = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                txt_date.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private List<TicketCsModel> parseData(List<String> ct, HashMap<String, String> cv) {

        List<TicketCsModel> csModelList = new ArrayList<>();
        for (int cr = 0; cr < ct.size(); cr++) {
            TicketCsModel cw = new TicketCsModel();
            String[] cq = ct.get(cr).split("[|]");
            cw.secretHBStr = cq[36];
            cw.secretStr = cq[0];
            cw.buttonTextInfo = cq[1];
            QueryLeftNewDTOModel cu = new QueryLeftNewDTOModel();
            cu.train_no = cq[2];
            cu.station_train_code = cq[3];
            cu.start_station_telecode = cq[4];
            cu.end_station_telecode = cq[5];
            cu.from_station_telecode = cq[6];
            cu.to_station_telecode = cq[7];
            cu.start_time = cq[8];
            cu.arrive_time = cq[9];
            cu.lishi = cq[10];
            cu.canWebBuy = cq[11];
            cu.yp_info = cq[12];
            cu.start_train_date = cq[13];
            cu.train_seat_feature = cq[14];
            cu.location_code = cq[15];
            cu.from_station_no = cq[16];
            cu.to_station_no = cq[17];
            cu.is_support_card = cq[18];
            cu.controlled_train_flag = cq[19];
            cu.gg_num = !TextUtils.isEmpty(cq[20]) ? cq[20] : "--";
            cu.gr_num = !TextUtils.isEmpty(cq[21]) ? cq[21] : "--";
            cu.qt_num = !TextUtils.isEmpty(cq[22]) ? cq[22] : "--";
            cu.rw_num = !TextUtils.isEmpty(cq[23]) ? cq[23] : "--";
            cu.rz_num = !TextUtils.isEmpty(cq[24]) ? cq[24] : "--";
            cu.tz_num = !TextUtils.isEmpty(cq[25]) ? cq[25] : "--";
            cu.wz_num = !TextUtils.isEmpty(cq[26]) ? cq[26] : "--";
            cu.yb_num = !TextUtils.isEmpty(cq[27]) ? cq[27] : "--";
            cu.yw_num = !TextUtils.isEmpty(cq[28]) ? cq[28] : "--";
            cu.yz_num = !TextUtils.isEmpty(cq[29]) ? cq[29] : "--";
            cu.ze_num = !TextUtils.isEmpty(cq[30]) ? cq[30] : "--";
            cu.zy_num = !TextUtils.isEmpty(cq[31]) ? cq[31] : "--";
            cu.swz_num = !TextUtils.isEmpty(cq[32]) ? cq[32] : "--";
            cu.srrb_num = !TextUtils.isEmpty(cq[33]) ? cq[33] : "--";
            cu.yp_ex = cq[34];
            cu.seat_types = cq[35];
            cu.exchange_train_flag = cq[36];
            cu.from_station_name = cv.get(cq[6]);
            cu.to_station_name = cv.get(cq[7]);
            cw.queryLeftNewDTO = cu;
            csModelList.add(cw);
        }
        return csModelList;


    }


}
