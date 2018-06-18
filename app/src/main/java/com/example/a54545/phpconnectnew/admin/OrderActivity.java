package com.example.a54545.phpconnectnew.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.a54545.phpconnectnew.R;
import com.example.a54545.phpconnectnew.entity.Order;

/**
 * 商家入口：显示订单（详细情况）
 */
public class OrderActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);
        textView=(TextView)findViewById(R.id.textView11);
        Order order;
        Bundle bundle2=this.getIntent().getExtras();
        order=(Order) bundle2.getSerializable("order");
        textView.setText("订单编号:"+order.getId()+"\n"
        +"下单者："+order.getName()+"\n"
        +"联系方式："+order.getPhone()+"\n"
        +"地址："+order.getAddress()+"\n"
        +"产品："+order.getPname()+"\n"
        +"单价："+order.getPrice()+"\n"
        +"购买数量："+order.getName()+"\n"
        +"总价:"+order.getTotalprice()+"\n"
        +"下单时间:"+order.getTime());

    }
}
