package com.example.a54545.phpconnectnew;


        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;

public class ShowCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer);
        Customer customer=(Customer)getIntent().getSerializableExtra("customer");
        TextView customerview=(TextView)findViewById(R.id.customer);
        customerview.setText("姓名:"+customer.getName()+"\n"
                +"性别:"+customer.getSex()+"\n"
                +"手机号:"+customer.getPhone()+"\n"
                +"总消费金额:"+customer.getCash()+"\n"
                +"消费次数:"+customer.getTimes());
    }
}
