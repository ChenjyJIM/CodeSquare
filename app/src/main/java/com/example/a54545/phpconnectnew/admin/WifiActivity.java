package com.example.a54545.phpconnectnew.admin;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.a54545.phpconnectnew.Utils.DateUtils;

import com.example.a54545.phpconnectnew.R;

/**
 * 店铺WiFi探针数据返回结果显示
 */
public class WifiActivity extends AppCompatActivity {
    //选择时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private ImageView img;
    private String days;
    private TextView dateTv,show;//时间选择
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        img=(ImageView)findViewById(R.id.imageView5);
        dateTv = (TextView)findViewById(R.id.datePicker);
        show=(TextView)findViewById(R.id.textView5);
        //设置日期选择器初始日期
        mYear = Integer.parseInt(DateUtils.getCurYear("yyyy"));
        mMonth = Integer.parseInt(DateUtils.getCurMonth("MM"))-1;
        mDay = Integer.parseInt(DateUtils.getCurDay("dd"));
        //设置当前 日期
        days = DateUtils.getCurDateStr("yyyy-MM-dd");
        dateTv.setText(days);
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTimeSelector();

                if(dateTv.getText().toString().trim().equals("2018-04-09"))
                {
                    img.setImageResource(R.drawable.wifidata09);
                    show.setText("当天数据统计如下：");
                }
                else if(dateTv.getText().toString().trim().equals("2018-04-10"))
                {
                    img.setImageResource(R.drawable.wifidata10);
                    show.setText("当天数据统计如下：");
                }
                else
                {
                    img.setImageResource(R.drawable.write);
                    show.setText("不好意思~在当日您的设备还没有数据可以显示哦~~");
                }
            }
        });
    }
    /**
     * 显示日期选择器
     */
    public void showTimeSelector() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mYear = i;
                mMonth = i1;
                mDay = i2;
                if (mMonth + 1 < 10) {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("-").append("0").
                                append(mMonth + 1).append("-").append("0").append(mDay).toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("-").append("0").
                                append(mMonth + 1).append("-").append(mDay).toString();
                    }

                } else {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("-").
                                append(mMonth + 1).append("-").append("0").append(mDay).toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("-").
                                append(mMonth + 1).append("-").append(mDay).toString();
                    }

                }
                dateTv.setText(days);
            }
        }, mYear, mMonth, mDay).show();
    }
}
