package com.example.a54545.phpconnectnew;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener ,transaction{

    private int num=0;
    private Customer customer;
    private TextView mTextMessage;
    private TextView name;
    private ImageView img;
    private final FragmentManager mFragmentManager = getSupportFragmentManager();
    private FrameLayout framelayout;
    private Fragment mFragmentOne;
    private Fragment mFragmentTwo, mFragmentThree,fragmentNow;
    private Bundle bundle2;
    private String username;
    private ArrayList<Order> ordershow=new ArrayList<Order>();
    private ArrayList<Product>productlist=new ArrayList<Product>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction fragmentTransaction2 = mFragmentManager.beginTransaction();
                    mTextMessage.setText("会员下单界面");
                    img.setImageResource(R.drawable.test);
                    if (mFragmentOne.isAdded()) {
                        //如果fragmentOne已经存在，则隐藏当前的fragment，
                        //然后显示fragmentOne（不会重新初始化，只是加载之前隐藏的fragment）
                        fragmentTransaction2.hide(fragmentNow).show(mFragmentOne);
                    } else {
                        //如果fragmentOne不存在，则隐藏当前的fragment，
                        //然后添加fragmentOne（此时是初始化）
                        fragmentTransaction2.hide(fragmentNow).add(R.id.framelayout, mFragmentOne);
                        fragmentTransaction2.addToBackStack(null);
                    }
                    fragmentNow = mFragmentOne;
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_dashboard:
                    FragmentTransaction fragmentTransaction3 = mFragmentManager.beginTransaction();
                    mTextMessage.setText("我的订单");
                    img.setImageResource(R.drawable.guess);
                    if (mFragmentTwo.isAdded()) {
                        fragmentTransaction3.hide(fragmentNow).show(mFragmentTwo);
                    } else {
                        fragmentTransaction3.hide(fragmentNow).add(R.id.framelayout, mFragmentTwo);
                        fragmentTransaction3.addToBackStack(null);
                    }
                    fragmentNow = mFragmentTwo;
                    fragmentTransaction3.commit();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("个人信息");
                    img.setImageResource(R.drawable.dec);
                    FragmentTransaction fragmentTransaction4=mFragmentManager.beginTransaction();
                    if (mFragmentThree.isAdded()) {
                        fragmentTransaction4.hide(fragmentNow).show(mFragmentThree);
                    } else {
                        fragmentTransaction4.hide(fragmentNow).add(R.id.framelayout, mFragmentThree);
                        fragmentTransaction4.addToBackStack(null);
                    }
                    fragmentNow = mFragmentThree;
                    fragmentTransaction4.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(" ");
        setContentView(R.layout.activity_user);
        mTextMessage = (TextView) findViewById(R.id.message);
        name=(TextView)findViewById(R.id.textView7new);
        img=(ImageView)findViewById(R.id.imageView4);
        customer=new Customer();

        initView();
        initDefaultFragment();
        Bundle bundle1=this.getIntent().getExtras();
         username=bundle1.getString("account");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        new Thread(new RequestThread_customer()).start();
        new Thread(new RequestThread_order()).start();
        new Thread(new RequestThread_product()).start();
        try {
            Thread.currentThread().sleep(500);//阻断0.1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





    }

    private void initDefaultFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //add：往碎片集合中添加一个碎片；
        //replace：移除之前所有的碎片，替换新的碎片（remove和add的集合体）(很少用，不推荐，因为是重新加载，所以消耗流量)
        //参数：1.公共父容器的的id  2.fragment的碎片
        fragmentTransaction.add(R.id.framelayout, mFragmentOne);
        fragmentTransaction.addToBackStack(null);

        //提交事务
        fragmentTransaction.commit();
        fragmentNow = mFragmentOne;
    }

    private void initView() {
        framelayout = (FrameLayout) findViewById(R.id.framelayout);
        //实例化FragmentOne
        mFragmentTwo = new SecondFragment();
        mFragmentOne = new FirstFragment();
        mFragmentThree =new ThirdFragment();
        //获取碎片管理者
        framelayout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {


    }

    @Override
    public String getAccount() {
        return username;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public ArrayList<Order> getOrderList() {
        return ordershow;
    }

    @Override
    public ArrayList<Product> getProductList() {
        return productlist;
    }


    private class RequestThread_customer implements Runnable {
        @SuppressWarnings("unchecked")
        public void run()  {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000); //设置读取超时
            String validateUrl = "http://139.224.135.139:81/user/getuserinform.php"; //这里是你与服务器交互的地址
            HttpPost httpRequst = new HttpPost(validateUrl);
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); //准备传输的数据
            params.add(new BasicNameValuePair("user", username));
            try
            {
                httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));//发送请求
                HttpResponse response = httpClient.execute(httpRequst);  //得到响应
                if(response.getStatusLine().getStatusCode() == 200)//返回值如果为200的话则证明成功的得到了数据
                {
                    StringBuilder builder = new StringBuilder();
                    //将得到的数据进行解析
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    for(String s =buffer.readLine(); s!= null; s = buffer.readLine())
                    {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());
                    //得到Json对象
                    JSONObject jsonObject   = new JSONObject(builder.toString());
                    customer.setId(jsonObject.getInt("id"));
                    customer.setName(jsonObject.getString("name"));
                    customer.setAddress(jsonObject.getString("address"));
                    customer.setPassword(jsonObject.getString("password"));
                    customer.setCash(new BigDecimal(jsonObject.getDouble("cash")));
                    customer.setPhone(jsonObject.getString("phone"));
                    customer.setSex(jsonObject.getString("sex"));
                    //在线程中判断是否得到成功从服务器得到数据
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private class RequestThread_order implements Runnable {
        @SuppressWarnings("unchecked")
        public void run()  {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000); //设置读取超时
            String validateUrl = "http://139.224.135.139:81/user/getuserorderinform.php"; //这里是你与服务器交互的地址
            HttpPost httpRequst = new HttpPost(validateUrl);
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); //准备传输的数据
            params.add(new BasicNameValuePair("user", username));
            try
            {
                httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));//发送请求
                HttpResponse response = httpClient.execute(httpRequst);  //得到响应
                if(response.getStatusLine().getStatusCode() == 200)//返回值如果为200的话则证明成功的得到了数据
                {
                    StringBuilder builder = new StringBuilder();
                    //将得到的数据进行解析
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    for(String s =buffer.readLine(); s!= null; s = buffer.readLine())
                    {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());
                    //得到Json对象
                    JSONArray jsonArray=new JSONArray(builder.toString());
                    // JSONObject jsonObject[]=new JSONObject[jsonArray.length()];

//                   //  JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);
                    num=jsonArray.length();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
//                             switch (i) {
//                                 case 0:
//                                     ordershow1.setId(jsonObject.getInt("id"));
//                                     ordershow1.setName(jsonObject.getString("name"));
//                                     ordershow1.setPhone(jsonObject.getString("phone"));
//                                     ordershow1.setAddress(jsonObject.getString("address"));
//                                     ordershow1.setPname(jsonObject.getString("pname"));
//                                     ordershow1.setPrice(jsonObject.getDouble("price"));
//                                     ordershow1.setNumber(jsonObject.getInt("number"));
//                                     ordershow1.setTotalprice(jsonObject.getDouble("totalprice"));
//                                     ordershow1.setTime(jsonObject.getString("time"));
//                                     break;
//                                 case 1:
//                                     ordershow2.setId(jsonObject.getInt("id"));
//                                     ordershow2.setName(jsonObject.getString("name"));
//                                     ordershow2.setPhone(jsonObject.getString("phone"));
//                                     ordershow2.setAddress(jsonObject.getString("address"));
//                                     ordershow2.setPname(jsonObject.getString("pname"));
//                                     ordershow2.setPrice(jsonObject.getDouble("price"));
//                                     ordershow2.setNumber(jsonObject.getInt("number"));
//                                     ordershow2.setTotalprice(jsonObject.getDouble("totalprice"));
//                                     ordershow2.setTime(jsonObject.getString("time"));
//                                     break;
//                                 case 2:
//                                     ordershow3.setId(jsonObject.getInt("id"));
//                                     ordershow3.setName(jsonObject.getString("name"));
//                                     ordershow3.setPhone(jsonObject.getString("phone"));
//                                     ordershow3.setAddress(jsonObject.getString("address"));
//                                     ordershow3.setPname(jsonObject.getString("pname"));
//                                     ordershow3.setPrice(jsonObject.getDouble("price"));
//                                     ordershow3.setNumber(jsonObject.getInt("number"));
//                                     ordershow3.setTotalprice(jsonObject.getDouble("totalprice"));
//                                     ordershow3.setTime(jsonObject.getString("time"));
//                                     break;
//
//                             }
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
                    //在线程中判断是否得到成功从服务器得到数据
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private class RequestThread_product implements Runnable {
        @SuppressWarnings("unchecked")
        public void run()  {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);//设置链接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000); //设置读取超时
            String validateUrl = "http://139.224.135.139:81/user/getproductinform.php"; //这里是你与服务器交互的地址
            HttpPost httpRequst = new HttpPost(validateUrl);
            List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); //准备传输的数据
            params.add(new BasicNameValuePair("user", username));
            try
            {
                httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));//发送请求
                HttpResponse response = httpClient.execute(httpRequst);  //得到响应
                if(response.getStatusLine().getStatusCode() == 200)//返回值如果为200的话则证明成功的得到了数据
                {
                    StringBuilder builder = new StringBuilder();
                    //将得到的数据进行解析
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    for(String s =buffer.readLine(); s!= null; s = buffer.readLine())
                    {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());
                    //得到Json对象
                    JSONArray jsonArray=new JSONArray(builder.toString());
                    // JSONObject jsonObject[]=new JSONObject[jsonArray.length()];

//                   //  JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);
                    num=jsonArray.length();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
//                             switch (i) {
//                                 case 0:
//                                     ordershow1.setId(jsonObject.getInt("id"));
//                                     ordershow1.setName(jsonObject.getString("name"));
//                                     ordershow1.setPhone(jsonObject.getString("phone"));
//                                     ordershow1.setAddress(jsonObject.getString("address"));
//                                     ordershow1.setPname(jsonObject.getString("pname"));
//                                     ordershow1.setPrice(jsonObject.getDouble("price"));
//                                     ordershow1.setNumber(jsonObject.getInt("number"));
//                                     ordershow1.setTotalprice(jsonObject.getDouble("totalprice"));
//                                     ordershow1.setTime(jsonObject.getString("time"));
//                                     break;
//                                 case 1:
//                                     ordershow2.setId(jsonObject.getInt("id"));
//                                     ordershow2.setName(jsonObject.getString("name"));
//                                     ordershow2.setPhone(jsonObject.getString("phone"));
//                                     ordershow2.setAddress(jsonObject.getString("address"));
//                                     ordershow2.setPname(jsonObject.getString("pname"));
//                                     ordershow2.setPrice(jsonObject.getDouble("price"));
//                                     ordershow2.setNumber(jsonObject.getInt("number"));
//                                     ordershow2.setTotalprice(jsonObject.getDouble("totalprice"));
//                                     ordershow2.setTime(jsonObject.getString("time"));
//                                     break;
//                                 case 2:
//                                     ordershow3.setId(jsonObject.getInt("id"));
//                                     ordershow3.setName(jsonObject.getString("name"));
//                                     ordershow3.setPhone(jsonObject.getString("phone"));
//                                     ordershow3.setAddress(jsonObject.getString("address"));
//                                     ordershow3.setPname(jsonObject.getString("pname"));
//                                     ordershow3.setPrice(jsonObject.getDouble("price"));
//                                     ordershow3.setNumber(jsonObject.getInt("number"));
//                                     ordershow3.setTotalprice(jsonObject.getDouble("totalprice"));
//                                     ordershow3.setTime(jsonObject.getString("time"));
//                                     break;
//
//                             }
                       Product product=new Product();
                        product.setId(jsonObject.getInt("id"));
                       product.setName(jsonObject.getString("name"));
                       product.setPrice(jsonObject.getDouble("price"));
                       product.setDescription(jsonObject.getString("description"));
                        productlist.add(product);
                    }
                    //在线程中判断是否得到成功从服务器得到数据
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


}
