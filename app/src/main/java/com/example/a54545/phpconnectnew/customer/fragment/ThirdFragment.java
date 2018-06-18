package com.example.a54545.phpconnectnew.customer.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a54545.phpconnectnew.R;
import com.example.a54545.phpconnectnew.entity.Customer;
import com.example.a54545.phpconnectnew.entity.Order;
import com.example.a54545.phpconnectnew.transaction.transaction;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * 用户入口：个人界面fragment实现体
 */
public class ThirdFragment extends Fragment {
    private TextView balance,address;
    private TextView tv,ordershow;
    private String name;
    private Customer customer;
    private ArrayList<Order> orderlist;
    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_third,container,false);
        balance=(TextView)view.findViewById(R.id.textView3new);

        tv=(TextView)view.findViewById(R.id.textView7new);
        ordershow=(TextView)view.findViewById(R.id.textView4new);
        ordershow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecondFragment.class);
                Bundle bundle=new Bundle();
                int num=orderlist.size();
                String key[]=new String[num];
                for(int i=0;i<num;i++)
                {
                    key[i]="order"+String.valueOf(i+1);
                    bundle.putSerializable(key[i],orderlist.get(i));
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        address=(TextView)view.findViewById(R.id.address);
        address.setText("住址："+customer.getAddress());
        tv.setText("欢迎您：  "+name);
       //  tv=(TextView)view.findViewById(R.id.textView7new);
        balance.setText("账户余额："+String.valueOf(customer.getCash()));

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         name=((transaction)context).getAccount();
         customer=((transaction)context).getCustomer();
         orderlist=((transaction)context).getOrderList();
    }
}
