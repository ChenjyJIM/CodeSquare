package com.example.a54545.phpconnectnew;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    private Button btn1,btn2,btn3;
    private String account;
    private TextView tv1,tv2,tv3;
    private ArrayList<Product> productArrayList=new ArrayList<Product>();
    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_first,container,false);
        tv1=(TextView)view.findViewById(R.id.textViewnew);
        tv2=(TextView)view.findViewById(R.id.textView2new);
        tv3=(TextView)view.findViewById(R.id.textView5new);
        btn1=(Button)view.findViewById(R.id.button3new);
        btn2=(Button)view.findViewById(R.id.button4new);
        btn3=(Button)view.findViewById(R.id.button5new);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowProduct.class);
                Bundle bundle=new Bundle();
                bundle.putString("account",account);
                    bundle.putSerializable("product",productArrayList.get(0));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowProduct.class);
                Bundle bundle=new Bundle();
                bundle.putString("account",account);
                bundle.putSerializable("product",productArrayList.get(1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowProduct.class);
                Bundle bundle=new Bundle();
                bundle.putString("account",account);
                bundle.putSerializable("product",productArrayList.get(2));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tv1.setText(productArrayList.get(0).getName());
        tv2.setText(productArrayList.get(1).getName());
        tv3.setText(productArrayList.get(2).getName());
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       productArrayList=((transaction)context).getProductList();
       account=((transaction)context).getAccount();
    }
}
