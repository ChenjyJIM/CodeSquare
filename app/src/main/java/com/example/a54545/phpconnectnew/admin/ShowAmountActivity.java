package com.example.a54545.phpconnectnew.admin;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a54545.phpconnectnew.R;
import com.example.a54545.phpconnectnew.entity.Amount;
import com.example.a54545.phpconnectnew.entity.Order;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 商家入口：显示销售额页面
 */
public class ShowAmountActivity extends AppCompatActivity {
    private String signal="I am jim";
    int num=0;
    private ArrayList<Order> ordershow=new ArrayList<Order>();
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_show_orderlist:
                    new Thread(new RequestThread_orderlist()).start();
                    try {
                        Thread.currentThread().sleep(500);//阻断0.1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(ShowAmountActivity.this,OrderShow.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("num",num);

                    String key[]=new String[num];
                    for(int i=0;i<num;i++)
                    {
                        key[i]="order"+String.valueOf(i+1);
                        bundle.putSerializable(key[i],ordershow.get(i));
                    }


                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;

                case R.id.navigation_show_amount:
                    Intent intent1=new Intent(ShowAmountActivity.this,ShowAmountActivity.class);
                    startActivity(intent1);
                    return true;

                case R.id.navigation_show_customer:
                    Intent intent2=new Intent(ShowAmountActivity.this,CustomerActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.navigation_show_data:
                    Intent intent3=new Intent(ShowAmountActivity.this,DataActivity1.class);
                    startActivity(intent3);
                    break;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_amount);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation1);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        final List<Amount> amountlist=new ArrayList<>();
        final List<String> list=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://139.224.135.139:81/admin/getOrder.php");
                try {
                    HttpResponse response = httpClient.execute(httpGet);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(entity.getContent()));
                        String result = reader.readLine();
                        JSONArray order=new JSONArray(result);
                        for(int i=0;i<order.length();i++){
                            JSONObject temp=(JSONObject)order.get(i);
                            Amount amount=new Amount(temp.getString("P_name"),temp.getInt("P_price"),temp.getInt("O_number"),temp.getInt("O_tprice"));
                            amountlist.add(amount);
                            list.add(amount.toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.currentThread().sleep(500);//阻断0.1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListView amountlistview=(ListView)findViewById(R.id.amountlist);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        amountlistview.setAdapter(adapter);
    }
    private class RequestThread_orderlist implements Runnable {
        @SuppressWarnings("unchecked")
        public void run()  {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000); //设置读取超时
            String validateUrl = "http://139.224.135.139:81/admin/showorderlist.php"; //这里是你与服务器交互的地址
            HttpPost httpRequst = new HttpPost(validateUrl);
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); //准备传输的数据
            params.add(new BasicNameValuePair("signal", signal));

            try
            {

                httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));//发送请求
                HttpResponse response = httpClient.execute(httpRequst);  //得到响应
                if(response.getStatusLine().getStatusCode() == 200)//返回值如果为200的话则证明成功的得到了数据
                {
                    StringBuilder builder = new StringBuilder();
                    //将得到的数据进行解析
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent()));
                    for(String s =buffer.readLine(); s!= null; s = buffer.readLine())
                    {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());

                    JSONArray jsonArray=new JSONArray(builder.toString());
                    num=jsonArray.length();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Order order=new Order();
                        order.setId(jsonObject.getInt("id"));
                        order.setName(jsonObject.getString("name"));
                        order.setPhone(jsonObject.getString("phone"));
                        order.setAddress(jsonObject.getString("address"));
                        order.setPname(jsonObject.getString("pname"));
                        order.setPrice(jsonObject.getDouble("price"));
                        order.setNumber(jsonObject.getInt("number"));
                        order.setTotalprice(jsonObject.getDouble("totalprice"));
                        order.setTime(jsonObject.getString("time"));
                        ordershow.add(order);
                    }


                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
