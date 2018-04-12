package com.example.a54545.phpconnectnew;


import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    private ArrayList<Order> ordershow=new ArrayList<Order>();
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;
    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_second,container,false);
        tv1=(TextView)view.findViewById(R.id.textView11new);
        tv2=(TextView)view.findViewById(R.id.textView12new);
        tv3=(TextView)view.findViewById(R.id.textView13new);
        tv4=(TextView)view.findViewById(R.id.textView14new);
        tv5=(TextView)view.findViewById(R.id.textView15new);
        tv6=(TextView)view.findViewById(R.id.textView30);
        ArrayList<TextView> tv=new ArrayList<TextView>();
        tv.add(tv1);
        tv.add(tv2);
        tv.add(tv3);
        tv.add(tv4);
        tv.add(tv5);
        tv.add(tv6);
        int num=ordershow.size();
        for(int i=0;i<num;i++)
        {
            tv.get(i).setText("       "+ordershow.get(i).getPname()
                    +"     "+ordershow.get(i).getNumber()
                    +"       "+ordershow.get(i).getTotalprice()
            +"     "+ordershow.get(i).getTime().substring(0,10)
            +"        已支付");
        }

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ordershow=((transaction)context).getOrderList();
    }
}
