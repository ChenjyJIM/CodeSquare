package com.example.a54545.phpconnectnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderShow extends AppCompatActivity {
    private TextView textView1,textView2,textView3,textView4,textView5,textView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist_page);
        ArrayList<TextView> textViews=new ArrayList<>();
        textView1=(TextView)findViewById(R.id.textView7);
        textView2=(TextView)findViewById(R.id.textView8);
        textView3=(TextView)findViewById(R.id.textView9);
        textView4=(TextView)findViewById(R.id.textView13);
        textView5=(TextView)findViewById(R.id.textView14);
        textView6=(TextView)findViewById(R.id.textView15);
        textViews.add(textView1);
        textViews.add(textView2);
        textViews.add(textView3);
        textViews.add(textView4);
        textViews.add(textView5);
        textViews.add(textView6);
        Bundle bundle2=this.getIntent().getExtras();
        Order order=new Order();
        int num=bundle2.getInt("num");
         String key[]=new String[num];
         String text[]=new String[textViews.size()];
         for(int i=0;i<text.length;i++)
         {
             text[i]="1";
         }
        final ArrayList<Order> orderList=new ArrayList<Order>(num);

        for(int i=0;i<num;i++)
        {
            key[i]="order"+String.valueOf(i+1);
            order=(Order) bundle2.getSerializable(key[i]);
            orderList.add(order);
        }

        for(int i=0;i<orderList.size();i++)
        {
           int id= orderList.get(i).getId();
           String name=orderList.get(i).getName();
           String phone=orderList.get(i).getPhone();
           Double totalprice=orderList.get(i).getTotalprice();
           String time=orderList.get(i).getTime();
           text[i]="              "+id+"           "+name+"      "+phone+"     "+totalprice+" "+time.substring(0,11);
        }
        for(int i=0;i<textViews.size();i++)
        {
            if(text[i].equals("1")) {

                textViews.get(i).setText("");
            }
            else
                textViews.get(i).setText(text[i]);
    }

        textView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(OrderShow.this,OrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("order",orderList.get(0));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    for(int i=0;i<textViews.size();i++)
    {
        final int count=i;
        textViews.get(i).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(OrderShow.this,OrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("order",orderList.get(count));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    }
}
